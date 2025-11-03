package one.devos.nautical.clientier.mixins;

import com.sun.jna.Platform;
import net.minecraft.client.Minecraft;
import one.devos.nautical.winterisms.client.titlebar.DarkModeTitleBar;
import org.lwjgl.glfw.GLFWNativeWin32;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class WindowTitlebarMixin {
    @Inject(method = "setWindowActive", at = @At("HEAD"))
    private void titlebar(boolean windowActive, CallbackInfo ci) {
        if (Platform.isWindows()) {
            long window = Minecraft.getInstance().getWindow().handle();
            int windowId = (int) GLFWNativeWin32.glfwGetWin32Window(window);

            DarkModeTitleBar.INSTANCE.darkModeTitleBarForWindows11(windowId);
        }
    }
}
