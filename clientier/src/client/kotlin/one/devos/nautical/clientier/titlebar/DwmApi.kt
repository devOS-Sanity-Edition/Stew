package one.devos.nautical.winterisms.client.titlebar

import com.sun.jna.Library
import com.sun.jna.Native
import com.sun.jna.platform.win32.WinDef.HWND
import com.sun.jna.win32.W32APIOptions


interface DwmApi : Library {
    fun DwmSetWindowAttribute(hwnd: HWND, attr: Int, attrValue: IntArray, attrSize: Int): Int

    companion object {
        val INSTANCE: DwmApi = Native.load<DwmApi>("dwmapi", DwmApi::class.java, W32APIOptions.DEFAULT_OPTIONS)
    }
}