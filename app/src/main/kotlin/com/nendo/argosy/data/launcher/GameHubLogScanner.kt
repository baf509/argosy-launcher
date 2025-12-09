package com.nendo.argosy.data.launcher

import android.os.Environment
import android.util.Log
import java.io.File
import java.util.regex.Pattern

private const val TAG = "GameHubLogScanner"

data class ScannedSteamGame(
    val appId: Long,
    val name: String
)

object GameHubLogScanner {

    private val appIdPattern = Pattern.compile("\"appId\":(\\d+)")
    private val namePattern = Pattern.compile("\"name\":\"([^\"]+)\"")
    private val jsonPattern = Pattern.compile(
        "ACFWriter extend.*->\\s*(\\{.*\"subTask\":\\[\\{.*?\"appId\":(\\d+).*?\"name\":\"([^\"]+)\".*?\\}\\].*\\})"
    )

    private fun getLogDir(packageName: String): File = File(
        Environment.getExternalStorageDirectory(),
        "Android/data/$packageName/files/Documents/XiaoKunLogcat"
    )

    fun scan(packageName: String): List<ScannedSteamGame> {
        val logDir = getLogDir(packageName)
        if (!logDir.exists() || !logDir.canRead()) {
            Log.w(TAG, "Log directory not accessible: ${logDir.absolutePath}")
            return emptyList()
        }

        val games = mutableMapOf<Long, ScannedSteamGame>()

        logDir.listFiles()?.filter { it.name.startsWith("XiaoKunLogInfo") }?.forEach { file ->
            try {
                scanFile(file, games)
            } catch (e: Exception) {
                Log.e(TAG, "Error scanning ${file.name}", e)
            }
        }

        Log.d(TAG, "Found ${games.size} unique Steam games")
        return games.values.toList()
    }

    private fun scanFile(file: File, games: MutableMap<Long, ScannedSteamGame>) {
        file.useLines { lines ->
            lines.filter { it.contains("ACFWriter extend") && it.contains("subTask") }
                .forEach { line ->
                    extractGameFromLine(line)?.let { game ->
                        games[game.appId] = game
                    }
                }
        }
    }

    private fun extractGameFromLine(line: String): ScannedSteamGame? {
        return try {
            val jsonStart = line.indexOf("{")
            if (jsonStart == -1) return null

            val json = line.substring(jsonStart)

            val appIdMatcher = appIdPattern.matcher(json)
            val nameMatcher = namePattern.matcher(json)

            if (appIdMatcher.find() && nameMatcher.find()) {
                val appId = appIdMatcher.group(1)?.toLongOrNull() ?: return null
                val name = nameMatcher.group(1) ?: return null

                ScannedSteamGame(appId = appId, name = name)
            } else {
                null
            }
        } catch (e: Exception) {
            Log.e(TAG, "Failed to parse line", e)
            null
        }
    }
}
