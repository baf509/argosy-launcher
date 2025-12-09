package com.nendo.argosy.data.launcher

import android.content.ComponentName
import android.content.Intent

object GameNativeLauncher : SteamLauncher {
    override val packageName = "app.gamenative"
    override val displayName = "GameNative"
    override val supportsScanning = true

    override fun createLaunchIntent(steamAppId: Long): Intent = Intent().apply {
        component = ComponentName(packageName, ".MainActivity")
        action = "app.gamenative.LAUNCH_GAME"
        putExtra("app_id", steamAppId.toInt())
    }
}
