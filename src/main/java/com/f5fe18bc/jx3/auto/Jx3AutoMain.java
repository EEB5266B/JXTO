package com.f5fe18bc.jx3.auto;

import com.f5fe18bc.jx3.auto.application.WindowAPP;
import com.f5fe18bc.jx3.auto.application.HotKeyApp;
import com.f5fe18bc.jx3.auto.application.LogApp;
import com.f5fe18bc.jx3.auto.config.Settings;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * 启动类
 */
@SpringBootApplication
public class Jx3AutoMain {

    public static void main(String[] args) {
        SpringApplicationBuilder builder = new SpringApplicationBuilder(Jx3AutoMain.class);
        builder.headless(false).run(args);
        initConfig();
        Jx3AutoAPP.launch(Jx3AutoAPP.class, args);
    }

    public static void initConfig() {
        Settings settings = Settings.getSettings();

        LogApp.changeRootLevel(settings.getSystem().getLogLevel());
        HotKeyApp.registerHotKey(settings.getHotKey());
        HotKeyApp.startHotKeyListener();
        WindowAPP.getAll();
    }
}
