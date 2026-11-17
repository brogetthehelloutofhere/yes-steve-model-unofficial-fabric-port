package com.elfmcys.ysm.mixin.client;

import com.elfmcys.ysm.client.event.RegisterEntityRenderersEvent;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.FirstPersonHandsAndItemsRenderer;
import net.minecraft.client.renderer.state.level.PlayerRenderState;
import net.minecraft.client.renderer.state.level.FirstPersonHandsAndItemsRenderState;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.world.entity.HumanoidArm;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * 第一人称手臂接管。
 *
 * <p>在 {@code renderHandsWithItems} 开头渲染自定义手臂模型。挂钩点选这里而不是
 * {@code renderArmWithItem}，因为后者每帧会被两只手各调用一次，而手臂模型
 * 本身就包含左右两条手臂，渲染两次会重叠。
 *
 * <p>渲染成功后把原版手臂 {@code renderPlayerArm} 拦掉；只拦手臂而不拦
 * {@code renderArmWithItem}，是为了保留原版对物品的变换与绘制。
 */
@Mixin(FirstPersonHandsAndItemsRenderer.class)
public abstract class ItemInHandRendererMixin {
    @Inject(method = "submitHandsWithItems", at = @At("HEAD"))
    private void ysm$renderCustomArm(float partialTick, PoseStack poseStack,
                                     SubmitNodeCollector collector, PlayerRenderState playerState,
                                     FirstPersonHandsAndItemsRenderState handsState, CallbackInfo ci) {
        LocalPlayer player = net.minecraft.client.Minecraft.getInstance().player;
        if (player != null) {
            RegisterEntityRenderersEvent.getFirstPersonArmRenderer()
                    .renderArm(player, partialTick, poseStack, collector, playerState.avatarRenderState.lightCoords);
        }
    }

    @Inject(method = "renderPlayerArm", at = @At("HEAD"), cancellable = true)
    private void ysm$hideVanillaArm(PoseStack poseStack, SubmitNodeCollector collector, int packedLight,
                                    float equippedProgress, float swingProgress, HumanoidArm arm,
                                    PlayerRenderState playerState, CallbackInfo ci) {
        if (RegisterEntityRenderersEvent.getFirstPersonArmRenderer().shouldHideVanillaArm()) {
            ci.cancel();
        }
    }
}
