package com.f5fe18bc.jxto.application;

import org.slf4j.LoggerFactory;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.platform.win32.WinDef.RECT;
import com.sun.jna.platform.win32.WinUser.WNDENUMPROC;


public class WindowAPP {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(WindowAPP.class);

    public static void getAll() {
        final User32 user32 = User32.INSTANCE;
        user32.EnumWindows(new WNDENUMPROC() {
            int count = 0;
            public boolean callback(HWND hWnd, Pointer arg1) {
                char[] windowText = new char[512];
                user32.GetWindowText(hWnd, windowText, 512);
                String wText = Native.toString(windowText);
                RECT rectangle = new RECT();
                user32.GetWindowRect(hWnd, rectangle);
                // get rid of this if block if you want all windows regardless
                // of whether
                // or not they have text
                // second condition is for visible and non minimised windows
                if (wText.isEmpty() || !(User32.INSTANCE.IsWindowVisible(hWnd)
                        && rectangle.left > -32000)) {
                    return true;
                }
                System.out.println("Found window with text " + hWnd
                        + ", total " + ++count + " Text: " + wText);
                return true;
            }
        }, null);
    }


}
