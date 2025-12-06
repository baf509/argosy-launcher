package com.nendo.argosy.domain.usecase.download

import com.nendo.argosy.data.download.DownloadManager
import com.nendo.argosy.data.local.dao.GameDao
import com.nendo.argosy.data.remote.romm.RomMRepository
import com.nendo.argosy.data.remote.romm.RomMResult
import javax.inject.Inject

sealed class DownloadResult {
    data object Queued : DownloadResult()
    data class Error(val message: String) : DownloadResult()
}

class DownloadGameUseCase @Inject constructor(
    private val gameDao: GameDao,
    private val romMRepository: RomMRepository,
    private val downloadManager: DownloadManager
) {
    suspend operator fun invoke(gameId: Long): DownloadResult {
        val game = gameDao.getById(gameId)
            ?: return DownloadResult.Error("Game not found")

        val rommId = game.rommId
            ?: return DownloadResult.Error("Game not synced from RomM")

        return when (val result = romMRepository.getRom(rommId)) {
            is RomMResult.Success -> {
                val rom = result.data
                val fileName = rom.fileName ?: "${game.title}.rom"

                val ext = fileName.substringAfterLast('.', "").lowercase()
                if (ext in INVALID_ROM_EXTENSIONS) {
                    return DownloadResult.Error("Invalid ROM file type: .$ext")
                }

                downloadManager.enqueueDownload(
                    gameId = gameId,
                    rommId = rommId,
                    fileName = fileName,
                    gameTitle = game.title,
                    platformSlug = rom.platformSlug,
                    coverPath = game.coverPath,
                    expectedSizeBytes = rom.fileSize
                )
                DownloadResult.Queued
            }
            is RomMResult.Error -> {
                DownloadResult.Error("Failed to get ROM info: ${result.message}")
            }
        }
    }

    companion object {
        private val INVALID_ROM_EXTENSIONS = setOf(
            "png", "jpg", "jpeg", "gif", "webp", "bmp",
            "html", "htm", "txt", "md", "pdf"
        )
    }
}
