package com.elfmcys.ysm.mixin.client;

import com.mojang.blaze3d.platform.InputConstants;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Minecraft 26.3 uses an SDL-backed keyboard-state buffer. Unbound key mappings
 * use InputConstants.UNKNOWN (value -1); some SDL-backed runtimes attempt to
 * index that value directly and throw IndexOutOfBoundsException while
 * KeyMapping.setAll() restores key state after closing a screen.
 */
@Mixin(InputConstants.class)
public abstract class InputConstantsMixin {
    @Inject(method = "isKeyDown(I)Z", at = @At("HEAD"), cancellable = true)
    private static void ysm$ignoreUnknownKeyCode(int key, CallbackInfoReturnable<Boolean> cir) {
        if (key < 0) {
            cir.setReturnValue(false);
        }
    }
}
