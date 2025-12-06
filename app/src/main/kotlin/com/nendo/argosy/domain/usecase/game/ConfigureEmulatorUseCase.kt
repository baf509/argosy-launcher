package com.nendo.argosy.domain.usecase.game

import com.nendo.argosy.data.emulator.EmulatorRegistry
import com.nendo.argosy.data.emulator.InstalledEmulator
import com.nendo.argosy.data.local.dao.EmulatorConfigDao
import com.nendo.argosy.data.local.entity.EmulatorConfigEntity
import javax.inject.Inject

class ConfigureEmulatorUseCase @Inject constructor(
    private val emulatorConfigDao: EmulatorConfigDao
) {
    suspend fun setForGame(gameId: Long, platformId: String, emulator: InstalledEmulator?) {
        emulatorConfigDao.deleteGameOverride(gameId)

        if (emulator != null) {
            val config = EmulatorConfigEntity(
                platformId = platformId,
                gameId = gameId,
                packageName = emulator.def.packageName,
                displayName = emulator.def.displayName,
                coreName = EmulatorRegistry.getRetroArchCores()[platformId],
                isDefault = false
            )
            emulatorConfigDao.insert(config)
        }
    }

    suspend fun setForPlatform(platformId: String, emulator: InstalledEmulator?) {
        emulatorConfigDao.clearPlatformDefaults(platformId)

        if (emulator != null) {
            val config = EmulatorConfigEntity(
                platformId = platformId,
                gameId = null,
                packageName = emulator.def.packageName,
                displayName = emulator.def.displayName,
                coreName = EmulatorRegistry.getRetroArchCores()[platformId],
                isDefault = true
            )
            emulatorConfigDao.insert(config)
        }
    }

    suspend fun clearForGame(gameId: Long) {
        emulatorConfigDao.deleteGameOverride(gameId)
    }

    suspend fun clearForPlatform(platformId: String) {
        emulatorConfigDao.clearPlatformDefaults(platformId)
    }
}
