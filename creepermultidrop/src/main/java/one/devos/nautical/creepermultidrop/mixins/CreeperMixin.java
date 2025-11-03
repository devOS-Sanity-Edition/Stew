package one.devos.nautical.creepermultidrop.mixins;


import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;


@Mixin(Creeper.class)
public abstract class CreeperMixin extends Entity {
    @Shadow
    public abstract boolean isPowered();

    @Shadow private boolean droppedSkulls;

    public CreeperMixin(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

//    @ModifyReturnValue(method = "canDropMobsSkull", at = @At("RETURN"))
//    public boolean canDropMobsSkull(boolean original) {
//        // this is weirdly written im aware but it wasnt dropping 100% of the time on charged creeper which wtf? so.. `this.isPowered()` had to be added back
//        return original || this.random.nextFloat() < 0.1 || this.isPowered();
//    }

    @WrapOperation(
            method = "killedEntity",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/monster/Creeper;isPowered()Z"
            )
    )
    public boolean checkRandomChance(Creeper instance, Operation<Boolean> original) {
        boolean isPowered = original.call(instance);

        if (isPowered) {
            this.droppedSkulls = false;
            return true;
        }

        boolean randomChance = this.random.nextFloat() < 0.1;

        if (!randomChance)
            this.droppedSkulls = true;

        return randomChance;
    }
}
