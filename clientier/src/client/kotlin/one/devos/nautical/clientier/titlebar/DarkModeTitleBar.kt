package one.devos.nautical.winterisms.client.titlebar

import com.sun.jna.Platform
import com.sun.jna.platform.win32.WinDef
import one.devos.nautical.clientier.config.Config
import one.devos.nautical.clientier.utils.isWindows11

object DarkModeTitleBar {
    const val DWMA_USE_IMMERSIVE_DARK_MODE = 20

    fun darkModeTitleBarForWindows11(hwndLong: Long) {
        if (Config.windows11DarkModeTitlebar) {
            if (Platform.isWindows() && isWindows11()) {
                val enabled = Config.windows11DarkModeTitlebar
                val useImmersiveDarkMode = if (enabled) 1 else 0
                val hwnd = WinDef.HWND(com.sun.jna.Pointer.createConstant(hwndLong.toInt()))
                DwmApi.INSTANCE.DwmSetWindowAttribute(hwnd, DWMA_USE_IMMERSIVE_DARK_MODE, intArrayOf(useImmersiveDarkMode), Integer.BYTES)
            }
        }
    }
}