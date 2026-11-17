package com.elfmcys.ysm.util;

import com.elfmcys.ysm.client.input.YsmKeyEvent;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.Minecraft;

public class InputCheckUtil {
    /**
     * 仅匹配键位，不检查修饰键。
     */
    public static boolean keyIsMatch(YsmKeyEvent event, KeyMapping keyMapping) {
        return keyMapping.matches(new KeyEvent(event.key(), event.scanCode(), event.modifiers()));
    }

    /**
     * 匹配键位，且要求 Alt 修饰键状态与期望一致。
     */
    public static boolean keyIsMatch(YsmKeyEvent event, KeyMapping keyMapping, boolean needAlt) {
        if (!keyMapping.matches(new KeyEvent(event.key(), event.scanCode(), event.modifiers()))) {
            return false;
        }
        KeyEvent keyEvent = new KeyEvent(event.key(), event.scanCode(), event.modifiers());
        return keyEvent.hasAltDown() == needAlt;
    }

    public static boolean isInGame() {
        Minecraft mc = Minecraft.getInstance();
        // Do not process YSM shortcuts while a loading overlay is visible.
        if (mc.gui.overlay() != null) {
            return false;
        }
        // Do not process YSM shortcuts while another screen is open.
        if (mc.gui.screen() != null) {
            return false;
        }
        if (!mc.mouseHandler.isMouseGrabbed()) {
            return false;
        }
        return mc.isWindowActive();
    }
}
