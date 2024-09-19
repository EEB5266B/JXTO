package com.eeb5266b.saltedfish.app;

import com.eeb5266b.saltedfish.app.service.LogApp;
import com.eeb5266b.saltedfish.app.config.Settings;
import com.eeb5266b.saltedfish.app.service.HotKeyApp;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.slf4j.LoggerFactory;

/**
 * APP 类
 */
public class SaltedFishAPP extends Application {

    public static final org.slf4j.Logger log = LoggerFactory.getLogger(SaltedFishAPP.class);

    @Override
    public void start(Stage primaryStage) {

        log.info("start APP");

        // 设置模糊效果
        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(10);
        dropShadow.setSpread(0.2);
        dropShadow.setColor(Color.web("#f0f0f0"));

        VBox vbox = new VBox();
        vbox.setPadding(new Insets(20, 0, 0, 0));
        vbox.setSpacing(10);
        vbox.getChildren().addAll();
        // 应用模糊效果
        vbox.setEffect(dropShadow);

        // 创建场景
        Scene scene = new Scene(vbox, 400, 200);

        primaryStage.setTitle("JX3 Auto");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        log.info("stop app");
        HotKeyApp.unregisterHotKey();
        System.exit(0);
    }

    @Override
    public void init() throws Exception {
        super.init();
        log.info("init app");
        Settings settings = Settings.getSettings();

        LogApp.changeRootLevel(settings.getSystem().getLogLevel());
        HotKeyApp.registerHotKey(settings.getHotKey());
        HotKeyApp.startHotKeyListener();
    }
}