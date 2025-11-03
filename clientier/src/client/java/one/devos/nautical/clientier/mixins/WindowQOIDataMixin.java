package one.devos.nautical.clientier.mixins;

import net.minecraft.client.Minecraft;
import one.devos.nautical.clientier.titlebar.QOIWindowIcon;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class WindowQOIDataMixin {
    @Inject(method = "onResourceLoadFinished", at = @At("HEAD"))
    private void onResourceLoadFinished(CallbackInfo ci) {
        QOIWindowIcon.INSTANCE.okayLetsDoThisShit();
    }
}

