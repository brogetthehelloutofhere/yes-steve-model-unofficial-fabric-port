package com.elfmcys.ysm.client.animation.molang.functions;

import com.elfmcys.ysm.client.event.ModInputEvent;
import com.elfmcys.ysm.molang.runtime.ExecutionContext;
import com.elfmcys.ysm.molang.runtime.Function;
import com.elfmcys.ysm.util.InputCheckUtil;
import com.mojang.blaze3d.platform.InputConstants;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class InputCheck {
    public static class Keyboard implements Function {
        @Override
        public @Nullable Object evaluate(@NotNull ExecutionContext<?> context, @NotNull ArgumentCollection arguments) {
            if (!InputCheckUtil.isInGame()) {
                return false;
            }
            for (var i = 0; i < arguments.size(); i++) {
                int keyCode = arguments.getAsInt(context, i);
                if (keyCode >= 0 && keyCode < ModInputEvent.KEY_STATES.length && ModInputEvent.KEY_STATES[keyCode]) {
                    return true;
                }
            }
            return false;
        }

        @Override
        public boolean validateArgumentSize(int size) {
            return size >= 1;
        }
    }

    public static class Mouse implements Function {
        @Override
        public @Nullable Object evaluate(@NotNull ExecutionContext<?> context, @NotNull ArgumentCollection arguments) {
            if (!InputCheckUtil.isInGame()) {
                return false;
            }
            int legacyButton = arguments.getAsInt(context, 0);
            int sdlButton = switch (legacyButton) {
                // YSM model inputs historically use GLFW numbering (left=0, right=1, middle=2).
                case 0 -> InputConstants.MOUSE_BUTTON_LEFT;
                case 1 -> InputConstants.MOUSE_BUTTON_RIGHT;
                case 2 -> InputConstants.MOUSE_BUTTON_MIDDLE;
                case 3 -> InputConstants.MOUSE_BUTTON_4;
                case 4 -> InputConstants.MOUSE_BUTTON_5;
                case 5 -> InputConstants.MOUSE_BUTTON_6;
                case 6 -> InputConstants.MOUSE_BUTTON_7;
                case 7 -> InputConstants.MOUSE_BUTTON_8;
                default -> -1;
            };
            if (sdlButton >= 0 && sdlButton < ModInputEvent.MOUSE_STATES.length) {
                return ModInputEvent.MOUSE_STATES[sdlButton];
            }
            return false;
        }

        @Override
        public boolean validateArgumentSize(int size) {
            return size == 1;
        }
    }
}
