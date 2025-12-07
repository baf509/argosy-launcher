package com.nendo.argosy.domain.usecase.game

import com.nendo.argosy.data.local.dao.GameDao
import com.nendo.argosy.data.repository.GameRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

class DeleteGameUseCase @Inject constructor(
    private val gameDao: GameDao,
    private val gameRepository: GameRepository
) {
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    suspend operator fun invoke(gameId: Long): Boolean {
        val game = gameDao.getById(gameId) ?: return false
        val path = game.localPath ?: return false

        gameRepository.clearLocalPath(gameId)

        scope.launch {
            try {
                val file = File(path)
                if (file.exists()) {
                    file.delete()
                }
            } catch (_: Exception) {
                // DB already updated, orphaned file is acceptable
            }
        }

        return true
    }
}
