package one.devos.nautical.clientier.utils

import com.sun.jna.platform.win32.Kernel32
import com.sun.jna.platform.win32.WinNT
import one.devos.nautical.clientier.Clientier

fun isWindows11(): Boolean {
    val osVersionInfo = WinNT.OSVERSIONINFO()
    Kernel32.INSTANCE.GetVersionEx(osVersionInfo)

    if (osVersionInfo.dwBuildNumber.toLong() < 22000L) {
        Clientier.LOGGER.info("[Clientier] Not running Windows 11, will not attempt to apply dark mode titlebar.")
        return false
    }

    Clientier.LOGGER.info("[Clientier] Running Windows 11, will attempt to apply dark mode titlebar.")
    return true
}