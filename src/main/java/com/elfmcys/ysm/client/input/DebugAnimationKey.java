package com.elfmcys.ysm.client.input;

import com.elfmcys.ysm.YesSteveModel;
import com.elfmcys.ysm.client.gui.overlay.DebugAnimationScreen;
import com.elfmcys.ysm.util.InputCheckUtil;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;

public class DebugAnimationKey {
    public static final KeyMapping DEBUG_ANIMATION_KEY = new KeyMapping("key.yes_steve_model.debug_animation.desc",
            InputConstants.Type.KEYBOARD, InputConstants.KEY_B,
            YsmKeyCategories.YSM);

    public static void register() {
        YsmInputDispatcher.registerKeyHandler(DebugAnimationKey::onKeyboardInput);
    }

    public static void onKeyboardInput(YsmKeyEvent event) {
        if (!YesSteveModel.isAvailable()) {
            return;
        }
        if (!InputCheckUtil.isInGame()) {
            return;
        }
        if (event.getAction() == InputConstants.PRESS && InputCheckUtil.keyIsMatch(event, DEBUG_ANIMATION_KEY, true)) {
            if (!DebugAnimationScreen.isEnabled()) {
                DebugAnimationScreen.enable();
            } else {
                DebugAnimationScreen.disable();
            }
        }
    }
}
