package com.nendo.argosy.data.launcher

import android.content.Context

object SteamLaunchers {
    val all: List<SteamLauncher> = listOf(
        GameHubLauncher,
        GameHubLiteLauncher,
        GameHubLiteAntutuLauncher,
        GameHubLiteLudashiLauncher,
        GameHubLitePubgLauncher,
        GameNativeLauncher
    )

    fun getInstalled(context: Context): List<SteamLauncher> =
        all.filter { it.isInstalled(context) }

    fun getByPackage(packageName: String): SteamLauncher? =
        all.find { it.packageName == packageName }

    fun getPreferred(context: Context): SteamLauncher? =
        getInstalled(context).firstOrNull()
}
