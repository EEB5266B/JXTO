package com.f5fe18bc.jx3auto.application;

import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.platform.win32.WinDef.RECT;
import com.sun.jna.platform.win32.WinUser.WNDENUMPROC;
import org.slf4j.LoggerFactory;

/**
 * 窗口应用程序类，提供获取所有窗口信息的方法
 */
public class WindowAPP {

    public static final org.slf4j.Logger log = LoggerFactory.getLogger(WindowAPP.class);

    /**
     * 获取并打印所有可见窗口的信息
     * <p>
     * 遍历所有窗口并获取窗口文本和位置，过滤出可见窗口，打印其句柄和文本
     */
    public static void getAll() {
        final User32 user32 = User32.INSTANCE;
        user32.EnumWindows(new WNDENUMPROC() {
            public boolean callback(HWND hWnd, Pointer arg1) {
                // 获取窗口文本
                char[] windowText = new char[512];
                user32.GetWindowText(hWnd, windowText, 512);
                String wText = Native.toString(windowText);
                // 获取窗口矩形区域
                RECT rectangle = new RECT();
                user32.GetWindowRect(hWnd, rectangle);
                // 过滤空窗口文本或不可见窗口
                if (wText.isEmpty() || !(User32.INSTANCE.IsWindowVisible(hWnd) && rectangle.left > -32000)) {
                    return true;
                }
                // 打印窗口信息
                System.out.println("Found window with text " + hWnd + ", Size " + rectangle + " Text: " + wText);
                return true;
            }
        }, null);
    }
}