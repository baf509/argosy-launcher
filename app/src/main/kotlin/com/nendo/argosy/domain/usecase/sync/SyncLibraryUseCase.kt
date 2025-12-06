package com.nendo.argosy.domain.usecase.sync

import android.util.Log
import com.nendo.argosy.data.remote.romm.RomMRepository
import com.nendo.argosy.data.remote.romm.RomMResult
import com.nendo.argosy.data.remote.romm.SyncResult
import com.nendo.argosy.ui.notification.NotificationManager
import com.nendo.argosy.ui.notification.NotificationProgress
import com.nendo.argosy.ui.notification.NotificationType
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.withContext
import javax.inject.Inject

private const val TAG = "SyncLibraryUseCase"
private const val NOTIFICATION_KEY = "romm-sync"

sealed class SyncLibraryResult {
    data class Success(val result: SyncResult) : SyncLibraryResult()
    data class Error(val message: String) : SyncLibraryResult()
}

class SyncLibraryUseCase @Inject constructor(
    private val romMRepository: RomMRepository,
    private val notificationManager: NotificationManager
) {
    suspend operator fun invoke(
        initializeFirst: Boolean = false,
        onProgress: ((current: Int, total: Int, platform: String) -> Unit)? = null
    ): SyncLibraryResult {
        Log.d(TAG, "invoke: starting, initializeFirst=$initializeFirst")

        if (initializeFirst) {
            romMRepository.initialize()
        }

        if (!romMRepository.isConnected()) {
            Log.d(TAG, "invoke: not connected")
            return SyncLibraryResult.Error("RomM not connected")
        }

        Log.d(TAG, "invoke: fetching summary")
        return when (val summary = romMRepository.getLibrarySummary()) {
            is RomMResult.Error -> {
                Log.e(TAG, "invoke: summary error: ${summary.message}")
                SyncLibraryResult.Error(summary.message)
            }
            is RomMResult.Success -> {
                val (platformCount, _) = summary.data
                Log.d(TAG, "invoke: got $platformCount platforms, showing persistent")

                notificationManager.showPersistent(
                    title = "Syncing Library",
                    subtitle = "Starting...",
                    key = NOTIFICATION_KEY,
                    progress = NotificationProgress(0, platformCount)
                )

                try {
                    withContext(NonCancellable) {
                        Log.d(TAG, "invoke: calling syncLibrary")
                        val result = romMRepository.syncLibrary { current, total, platform ->
                            Log.d(TAG, "invoke: progress $current/$total - $platform")
                            notificationManager.updatePersistent(
                                key = NOTIFICATION_KEY,
                                subtitle = platform,
                                progress = NotificationProgress(current, total)
                            )
                            onProgress?.invoke(current, total, platform)
                        }

                        Log.d(TAG, "invoke: syncLibrary returned - added=${result.gamesAdded}, updated=${result.gamesUpdated}, deleted=${result.gamesDeleted}, errors=${result.errors}")

                        val subtitle = buildString {
                            append("${result.gamesAdded} added, ${result.gamesUpdated} updated")
                            if (result.gamesDeleted > 0) {
                                append(", ${result.gamesDeleted} removed")
                            }
                        }

                        if (result.errors.isEmpty()) {
                            Log.d(TAG, "invoke: completing with success")
                            notificationManager.completePersistent(
                                key = NOTIFICATION_KEY,
                                title = "Sync complete",
                                subtitle = subtitle,
                                type = NotificationType.SUCCESS
                            )
                        } else {
                            Log.d(TAG, "invoke: completing with errors")
                            notificationManager.completePersistent(
                                key = NOTIFICATION_KEY,
                                title = "Sync completed with errors",
                                subtitle = "${result.errors.size} platform(s) failed",
                                type = NotificationType.ERROR
                            )
                        }

                        SyncLibraryResult.Success(result)
                    }
                } catch (e: Exception) {
                    Log.e(TAG, "invoke: exception", e)
                    withContext(NonCancellable) {
                        notificationManager.completePersistent(
                            key = NOTIFICATION_KEY,
                            title = "Sync failed",
                            subtitle = e.message,
                            type = NotificationType.ERROR
                        )
                    }
                    SyncLibraryResult.Error(e.message ?: "Sync failed")
                }
            }
        }
    }
}
