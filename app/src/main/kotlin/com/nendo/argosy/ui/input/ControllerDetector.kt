package com.nendo.argosy.ui.input

import android.view.InputDevice

enum class DetectedIconLayout {
    XBOX,
    NINTENDO
}

object ControllerDetector {
    private val NINTENDO_VIDS = setOf(0x057e)
    private val XBOX_VIDS = setOf(0x045e)
    private val SONY_VIDS = setOf(0x054c)

    fun detectIconLayout(vendorId: Int): DetectedIconLayout? {
        return when (vendorId) {
            in NINTENDO_VIDS -> DetectedIconLayout.NINTENDO
            in XBOX_VIDS, in SONY_VIDS -> DetectedIconLayout.XBOX
            else -> null
        }
    }

    fun detectIconLayout(device: InputDevice): DetectedIconLayout? {
        return detectIconLayout(device.vendorId)
    }

    fun getActiveGamepadVendorId(): Int? {
        val deviceIds = InputDevice.getDeviceIds()
        for (deviceId in deviceIds) {
            val device = InputDevice.getDevice(deviceId) ?: continue
            val sources = device.sources
            if (sources and InputDevice.SOURCE_GAMEPAD == InputDevice.SOURCE_GAMEPAD ||
                sources and InputDevice.SOURCE_JOYSTICK == InputDevice.SOURCE_JOYSTICK
            ) {
                return device.vendorId
            }
        }
        return null
    }

    fun detectFromActiveGamepad(): DetectedIconLayout? {
        val vendorId = getActiveGamepadVendorId() ?: return null
        return detectIconLayout(vendorId)
    }
}
