package one.devos.nautical.clientier.mixins;

import net.minecraft.CrashReport;
import net.minecraft.ReportType;
import one.devos.nautical.clientier.crash.RoombaCrash;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.nio.file.Path;
import java.util.List;

@Mixin(CrashReport.class)
public class CrashReportRoombaMixin {
    @Inject(method = "saveToFile(Ljava/nio/file/Path;Lnet/minecraft/ReportType;Ljava/util/List;)Z", at = @At("HEAD"))
    private void crashed(Path path, ReportType type, List<String> links, CallbackInfoReturnable<Boolean> cir) {
        RoombaCrash.INSTANCE.playRoombaFallingDownTheStairs(); // is this a very stupid place to do it? yes. is it at least a guarentee? yep.
    }
}
