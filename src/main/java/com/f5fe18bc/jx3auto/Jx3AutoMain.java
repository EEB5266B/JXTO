package com.f5fe18bc.jx3auto;

import com.f5fe18bc.jx3auto.application.HotKeyApp;
import com.f5fe18bc.jx3auto.application.LogApp;
import com.f5fe18bc.jx3auto.application.WindowAPP;
import com.f5fe18bc.jx3auto.config.Settings;

/**
 * 启动类
 */
public class Jx3AutoMain {

    public static void main(String[] args) {
        initConfig();
        Jx3AutoAPP.launch(Jx3AutoAPP.class, args);
    }

    public static void initConfig() {
        Settings settings = Settings.getSettings();

//        LogApp.changeRootLevel(settings.getSystem().getLogLevel());
        HotKeyApp.registerHotKey(settings.getHotKey());
        HotKeyApp.startHotKeyListener();
        WindowAPP.getAll();
    }
}
