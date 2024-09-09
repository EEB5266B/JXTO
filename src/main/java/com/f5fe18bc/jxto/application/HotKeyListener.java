package com.f5fe18bc.jxto.application;

import com.f5fe18bc.jxto.config.Settings;
import com.f5fe18bc.jxto.enums.HotKeyIdentifier;
import com.melloware.jintellitype.JIntellitype;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * 热键
 */
public class HotKeyListener {

    public static final List<HotKey> hotKeyList = new ArrayList<>();
    private static final Logger logger = LoggerFactory.getLogger(HotKeyListener.class);

    public static void registerHotKey(Settings.HotKey hotKey) {

        if (hotKey != null) {

            if (StringUtils.isNotBlank(hotKey.getStart())) {
                hotKeyList.add(new HotKey(HotKeyIdentifier.START, hotKey.getStart()));
            }

            hotKeyList.forEach(k -> {

                if (logger.isDebugEnabled()) {
                    logger.debug("registerHotKey: key {}, value {}", k.identifier.getKey(), k.modifierAndKeyCode);
                }
                JIntellitype.getInstance().registerHotKey(k.identifier.getKey(), k.modifierAndKeyCode);
            });
        } else {
            logger.warn("hotkey settings is empty");
        }
    }

    public static void unregisterHotKey() {
        hotKeyList.forEach(k -> {

            if (logger.isDebugEnabled()) {
                logger.debug("unregisterHotKey: key {}", k.identifier.getKey());
            }
            JIntellitype.getInstance().unregisterHotKey(k.identifier.getKey());
        });
    }

    public static void startHotKeyListener() {

        try {
            Robot robot = new Robot();

            JIntellitype.getInstance().addHotKeyListener(markCode -> {

                if (logger.isDebugEnabled()) {
                    logger.debug("HotKeyListener: key {}", markCode);
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

            logger.error("Unexpected error", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 创建热键对象
     *
     * @param identifier         热键标识符
     * @param modifierAndKeyCode 热键组合（e.g. CTRL+SHIFT+A）
     * @return 热键对象
     */
    public static HotKey hotKey(HotKeyIdentifier identifier, String... modifierAndKeyCode) {
        return new HotKey(identifier, String.join("+", modifierAndKeyCode).toUpperCase());
    }

    /**
     * 创建热键对象
     *
     * @param identifier         热键标识符
     * @param modifierAndKeyCode 热键
     * @return 热键对象
     */
    public static HotKey hotKey(HotKeyIdentifier identifier, String modifierAndKeyCode) {
        return new HotKey(identifier, modifierAndKeyCode);
    }

    public record HotKey(HotKeyIdentifier identifier, String modifierAndKeyCode) {
    }
}
