package com.elfmcys.ysm.client.event;

import com.elfmcys.ysm.YesSteveModel;
import com.elfmcys.ysm.client.input.YsmInputDispatcher;
import com.elfmcys.ysm.client.input.YsmKeyEvent;
import com.elfmcys.ysm.client.input.YsmMouseEvent;
import com.elfmcys.ysm.util.InputCheckUtil;
import com.mojang.blaze3d.platform.InputConstants;

public class ModInputEvent {
    // Minecraft 26.3 uses SDL scancodes. The key space fits in 512 entries.
    public static volatile boolean[] KEY_STATES = new boolean[512];
    // SDL mouse button identifiers are 1-based (left=1 through button 8=8).
    public static volatile boolean[] MOUSE_STATES = new boolean[InputConstants.MOUSE_BUTTON_8 + 1];

    public static void register() {
        YsmInputDispatcher.registerKeyHandler(ModInputEvent::onKeyInput);
        YsmInputDispatcher.registerMouseHandler(ModInputEvent::onMouseInput);
    }

    public static void onKeyInput(YsmKeyEvent event) {
        if (!YesSteveModel.isAvailable() || !InputCheckUtil.isInGame()) {
            return;
        }
        if (event.key() >= 0 && event.key() < KEY_STATES.length) {
            if (event.getAction() == InputConstants.PRESS) {
                KEY_STATES[event.key()] = true;
            } else if (event.getAction() == InputConstants.RELEASE) {
                KEY_STATES[event.key()] = false;
            }
        }
    }

    public static void onMouseInput(YsmMouseEvent event) {
        if (!YesSteveModel.isAvailable() || !InputCheckUtil.isInGame()) {
            return;
        }
        int button = event.button();
        if (button >= InputConstants.MOUSE_BUTTON_LEFT && button <= InputConstants.MOUSE_BUTTON_8) {
            if (event.getAction() == InputConstants.PRESS) {
                MOUSE_STATES[button] = true;
            } else if (event.getAction() == InputConstants.RELEASE) {
                MOUSE_STATES[button] = false;
            }
        }
    }
}
