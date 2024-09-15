package com.f5fe18bc.jx3.auto.application;

import com.f5fe18bc.jx3.auto.enums.HotKeyIdentifier;
import com.f5fe18bc.jx3.auto.config.Settings;
import com.melloware.jintellitype.JIntellitype;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * 热键
 */
@Slf4j
public class HotKeyApp {

    public static final List<HotKey> hotKeyList = new ArrayList<>();
    public static void registerHotKey(Settings.HotKey hotKey) {

        if (hotKey != null) {

            if (StringUtils.isNotBlank(hotKey.getStart())) {
                hotKeyList.add(new HotKey(HotKeyIdentifier.START, hotKey.getStart()));
            }

            hotKeyList.forEach(k -> {

                if (log.isDebugEnabled()) {
                    log.debug("registerHotKey: key {}, value {}", k.identifier.getKey(), k.modifierAndKeyCode);
                }
                JIntellitype.getInstance().registerHotKey(k.identifier.getKey(), k.modifierAndKeyCode);
            });
        } else {
            log.warn("hotkey settings is empty");
        }
    }

    public static void unregisterHotKey() {
        hotKeyList.forEach(k -> {

            if (log.isDebugEnabled()) {
                log.debug("unregisterHotKey: key {}", k.identifier.getKey());
            }
            JIntellitype.getInstance().unregisterHotKey(k.identifier.getKey());
        });
    }

    public static void startHotKeyListener() {

        try {
            Robot robot = new Robot();

            JIntellitype.getInstance().addHotKeyListener(markCode -> {

                if (log.isDebugEnabled()) {
                    log.debug("HotKeyListener: key {}", markCode);
                }

                if (HotKeyIdentifier.START.getKey() == markCode) {
                    char myChar = '1';
                    int keyCode = KeyEvent.getExtendedKeyCodeForChar(myChar);
                    robot.keyPress(keyCode);
                    robot.keyRelease(keyCode);
                } else if (HotKeyIdentifier.TEST.getKey() == markCode) {

                }
            });
        } catch (AWTException e) {

            log.error("Unexpected error", e);
            throw new RuntimeException(e);
        }
    }

    public record HotKey(HotKeyIdentifier identifier, String modifierAndKeyCode) {

        /**
         * 创建热键对象
         *
         * @param identifier         热键标识符
         * @param modifierAndKeyCode 热键组合（e.g. CTRL+SHIFT+A）
         */
        public HotKey(HotKeyIdentifier identifier, String... modifierAndKeyCode) {
            this(identifier, String.join("+", modifierAndKeyCode).toUpperCase());
        }
    }
}
