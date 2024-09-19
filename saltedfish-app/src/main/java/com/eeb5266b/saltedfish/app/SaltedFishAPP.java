package com.eeb5266b.saltedfish.app;

import com.eeb5266b.saltedfish.app.config.Settings;
import com.eeb5266b.saltedfish.app.service.HotKeyApp;
import com.eeb5266b.saltedfish.app.service.LogApp;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.slf4j.LoggerFactory;

/**
 * APP 类
 */
public class SaltedFishAPP extends Application {

    public static final org.slf4j.Logger log = LoggerFactory.getLogger(SaltedFishAPP.class);


    @Override
    public void start(Stage stage) {

        log.info("start APP");
        VBox root = new VBox();
        Scene scene = new Scene(root);

        stage.setTitle("Salted Fish");
        stage.getIcons().add(new Image("icon.png"));
        stage.setHeight(400);
        stage.setWidth(400);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(scene);
        stage.show();
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