package com.f5fe18bc.jx3auto.application;

import com.f5fe18bc.jx3auto.config.Settings;
import com.f5fe18bc.jx3auto.enums.HotKeyIdentifier;
import com.melloware.jintellitype.JIntellitype;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * 热键应用程序类，负责注册、注销和监听热键事件。
 */
public class HotKeyApp {

    // 日志记录器实例。
    public static final Logger log = LoggerFactory.getLogger(HotKeyApp.class);

    // 保存已注册热键的列表。
    public static final List<HotKey> hotKeyList = new ArrayList<>();

    /**
     * 注册给定配置的热键。
     *
     * @param hotKey 热键配置对象，包含热键的启动等信息。
     */
    public static void registerHotKey(Settings.HotKey hotKey) {
        if (hotKey != null) {

            // 检查并添加启动热键。
            if (StringUtils.isNotBlank(hotKey.getStart())) {
                hotKeyList.add(new HotKey(HotKeyIdentifier.START, hotKey.getStart()));
            }

            // 遍历热键列表，注册每个热键。
            hotKeyList.forEach(k -> {
                // 调试模式下记录日志。
                if (log.isDebugEnabled()) {
                    log.debug("registerHotKey: key {}, value {}", k.identifier.getKey(), k.modifierAndKeyCode);
                }
                JIntellitype.getInstance().registerHotKey(k.identifier.getKey(), k.modifierAndKeyCode);
            });
        } else {
            // 配置为空时记录警告日志。
            log.warn("hotkey settings is empty");
        }
    }

    /**
     * 注销所有已注册的热键。
     */
    public static void unregisterHotKey() {
        // 遍历热键列表，注销每个热键。
        hotKeyList.forEach(k -> {
            // 调试模式下记录日志。
            if (log.isDebugEnabled()) {
                log.debug("unregisterHotKey: key {}", k.identifier.getKey());
            }
            JIntellitype.getInstance().unregisterHotKey(k.identifier.getKey());
        });
    }

    /**
     * 启动热键监听器，监听热键事件。
     */
    public static void startHotKeyListener() {
        try {
            // 创建Robot对象用于模拟键盘事件。
            Robot robot = new Robot();

            // 添加热键监听器，处理不同的热键事件。
            JIntellitype.getInstance().addHotKeyListener(markCode -> {
                // 调试模式下记录日志。
                if (log.isDebugEnabled()) {
                    log.debug("HotKeyListener: key {}", markCode);
                }

                // 根据热键标识符执行相应的操作。
                if (HotKeyIdentifier.START.getKey() == markCode) {
                    char myChar = '1';
                    int keyCode = KeyEvent.getExtendedKeyCodeForChar(myChar);
                    robot.keyPress(keyCode);
                    robot.keyRelease(keyCode);
                } else if (HotKeyIdentifier.TEST.getKey() == markCode) {
                    // 可以添加测试热键的处理逻辑。
                }
            });
        } catch (AWTException e) {
            // 异常处理：记录错误并抛出运行时异常。
            log.error("Unexpected error", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 热键对象的记录类。
     *
     * @param identifier         热键标识符，表示热键的功能。
     * @param modifierAndKeyCode 热键的修饰符和键码，如"CTRL+SHIFT+A"。
     */
    public record HotKey(HotKeyIdentifier identifier, String modifierAndKeyCode) {

        /**
         * 创建热键对象。
         *
         * @param identifier         热键标识符
         * @param modifierAndKeyCode 热键组合（e.g. CTRL+SHIFT+A）
         */
        public HotKey(HotKeyIdentifier identifier, String... modifierAndKeyCode) {
            this(identifier, String.join("+", modifierAndKeyCode).toUpperCase());
        }
    }
}
