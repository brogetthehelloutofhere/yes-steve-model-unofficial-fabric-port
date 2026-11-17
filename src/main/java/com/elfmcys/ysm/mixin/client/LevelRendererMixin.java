package com.elfmcys.ysm.mixin.client;

import com.elfmcys.ysm.YesSteveModel;
import com.elfmcys.ysm.client.animation.AnimationParallelTicker;
import com.elfmcys.ysm.util.RenderUtil;
import com.mojang.blaze3d.resource.GraphicsResourceAllocator;
import com.mojang.renderpearl.api.buffers.GpuBufferSlice;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import org.joml.Vector4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LevelRenderer.class)
public class LevelRendererMixin {
    @Inject(method = "render", at = @At("HEAD"))
    private void beforeRenderLevel(GraphicsResourceAllocator resourceAllocator,
                                   boolean renderOutline,
                                   CameraRenderState cameraState,
                                   GpuBufferSlice terrainFog,
                                   Vector4f fogColor,
                                   boolean shouldRenderSky,
                                   boolean consistentDepthRequired,
                                   CallbackInfo ci) {
        if (YesSteveModel.isAvailable()) {
            RenderUtil.setRenderingLevel(true);
            AnimationParallelTicker.scheduleAll(
                    Minecraft.getInstance().getDeltaTracker().getGameTimeDeltaPartialTick(true));
        }
    }

    @Inject(method = "render", at = @At("RETURN"))
    private void afterRenderEntities(GraphicsResourceAllocator resourceAllocator,
                                     boolean renderOutline,
                                     CameraRenderState cameraState,
                                     GpuBufferSlice terrainFog,
                                     Vector4f fogColor,
                                     boolean shouldRenderSky,
                                     boolean consistentDepthRequired,
                                     CallbackInfo ci) {
        if (YesSteveModel.isAvailable()) {
            AnimationParallelTicker.waitAll();
            RenderUtil.setRenderingLevel(false);
        }
    }
}
