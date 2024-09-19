package com.f5fe18bc.jx3auto.application;

import com.sun.jna.Native;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinDef.HWND;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * 窗口应用程序类，提供获取所有窗口信息的方法
 */
public class WindowAPP {

    // 日志记录器实例
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(WindowAPP.class);

    // Windows 用户API实例
    private static final User32 user32 = User32.INSTANCE;
    // 无界窗口标题常量
    private static final String WUJIE_TITLE = "剑网3无界";

    /**
     * 获取所有窗口的标题和句柄映射
     *
     * @return 包含所有窗口标题和句柄的Map
     */
    private static Map<String, HWND> getAllWindow() {
        Map<String, HWND> hwndMap = new HashMap<>();

        // 枚举所有顶级窗口，获取其标题并存储到Map中
        user32.EnumWindows((hWnd, arg1) -> {
            hwndMap.put(getWindowTitle(hWnd), hWnd);
            return true;
        }, null);

        return hwndMap;
    }

    /**
     * 根据标题查找窗口的标题和句柄映射
     *
     * @param title 要查找的窗口标题（支持部分匹配）
     * @return 匹配给定标题的窗口的Map
     */
    private static Map<String, HWND> findWindow(String title) {
        Map<String, HWND> hwndMap = new HashMap<>();

        // 枚举所有顶级窗口，查找标题中包含给定字符串的窗口
        user32.EnumWindows((hWnd, arg1) -> {
            String windowTitle = getWindowTitle(hWnd);
            if (windowTitle.contains(title)) {
                hwndMap.put(windowTitle, hWnd);
            }

            return true;
        }, null);

        return hwndMap;
    }

    /**
     * 获取指定窗口句柄的窗口标题
     *
     * @param hWnd 窗口句柄
     * @return 窗口标题字符串
     */
    private static String getWindowTitle(HWND hWnd) {
        char[] windowText = new char[512];
        user32.GetWindowText(hWnd, windowText, 512);
        return Native.toString(windowText);
    }

    /**
     * 获取指定窗口句柄的窗口大小
     *
     * @param hWnd 窗口句柄
     * @return 窗口大小的RECT结构
     */
    private static WinDef.RECT getWindowSize(HWND hWnd) {
        WinDef.RECT rectangle = new WinDef.RECT();
        user32.GetWindowRect(hWnd, rectangle);

        return rectangle;
    }

    /**
     * 查找剑网3无界窗口
     *
     * @return 无界窗口的标题和句柄映射
     */
    public static Map<String, HWND> getWuJieWindow() {
        return findWindow(WUJIE_TITLE);
    }
}
