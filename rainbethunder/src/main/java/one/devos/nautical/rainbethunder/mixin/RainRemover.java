package one.devos.nautical.rainbethunder.mixin;

import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.storage.ServerLevelData;
import net.minecraft.world.level.storage.WritableLevelData;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Supplier;

@Mixin(ServerLevel.class)
@Debug(export = true)
public abstract class RainRemover extends Level {
    @Shadow @Final private ServerLevelData serverLevelData;

	protected RainRemover(WritableLevelData pLevel0, ResourceKey<Level> pResourceKey1,
            RegistryAccess pRegistryAccess2, Holder<DimensionType> pHolder3, Supplier<ProfilerFiller> pSupplier4,
            boolean pBoolean5, boolean pBoolean6, long pLong7, int pInt8) {
        super(pLevel0, pResourceKey1, pRegistryAccess2, pHolder3, pBoolean5, pBoolean6, pLong7, pInt8);
    }

    @Redirect(method = "advanceWeatherCycle", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/storage/WritableLevelData;isThundering()Z"))
    private boolean redirect(WritableLevelData instance) {

        return instance.isRaining();
    }

    @Redirect(method = "advanceWeatherCycle", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/storage/ServerLevelData;getThunderTime()I"))
    private int redirect2(ServerLevelData instance) {

        return instance.getRainTime();
    }
    @Redirect(method = "advanceWeatherCycle", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/storage/ServerLevelData;setThundering(Z)V"))
    private void ignoreThisShit(ServerLevelData instance, boolean b) {

    }

    @Redirect(method = "advanceWeatherCycle", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/storage/ServerLevelData;setRaining(Z)V"))
    private void thisGoodShit(ServerLevelData instance, boolean b) {
        instance.setRaining(b);
        instance.setThundering(b);
    }

    @Inject(method = "advanceWeatherCycle", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/storage/ServerLevelData;setRaining(Z)V"))
    private void whyyyyyyyyyy(CallbackInfo ci) {
        this.thunderLevel = rainLevel;
        this.oThunderLevel = oRainLevel;
        this.serverLevelData.setThunderTime(this.serverLevelData.getRainTime());
    }
}
