package com.f5fe18bc.jx3.auto;

import com.f5fe18bc.jx3.auto.application.HotKeyApp;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * APP 类
 */
@Slf4j
public class Jx3AutoAPP extends Application {

    private TextField xField;
    private TextField yField;
    private TextArea colorTextArea;

    @Override
    public void start(Stage primaryStage) {

        log.info("start APP");

        Label xLabel = new Label("X Coordinate:");
        xField = new TextField();
        Label yLabel = new Label("Y Coordinate:");
        yField = new TextField();

        Button captureButton = new Button("Capture Screen");
        captureButton.setOnAction(event -> captureScreen());

        Button clickButton = new Button("Click at Coordinates");
        clickButton.setOnAction(event -> clickAtCoordinates());

        Button getColorButton = new Button("Get Color at Coordinates");
        getColorButton.setOnAction(event -> getColorAtCoordinates());

        colorTextArea = new TextArea();
        colorTextArea.setEditable(false);

        VBox vbox = new VBox(10);
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(xLabel, xField, yLabel, yField, captureButton, clickButton, getColorButton, colorTextArea);

        Scene scene = new Scene(vbox, 300, 300);

        primaryStage.setTitle("JXTO");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void captureScreen() {
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        GraphicsConfiguration gc = gd.getDefaultConfiguration();
        Rectangle rect = gc.getBounds();
        Robot robot;
        try {
            robot = new Robot();
            BufferedImage image = robot.createScreenCapture(rect);
            //保存路径
            File screenFile = new File("img/");
            if (!screenFile.exists()) {
                screenFile.mkdir();
            }
            File f = new File(screenFile, System.currentTimeMillis() + ".png");

            ImageIO.write(image, "png", f);

        } catch (AWTException | IOException e) {
            log.error("captureScreen", e);
        }
    }

    private void clickAtCoordinates() {
        int x = Integer.parseInt(xField.getText());
        int y = Integer.parseInt(yField.getText());

        try {
            Robot robot = new Robot();
            robot.mouseMove(x, y);
            robot.delay(1000); // 延迟1秒
            robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
            robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        } catch (AWTException e) {
            log.error("clickAtCoordinates", e);
        }
    }

    private void getColorAtCoordinates() {
        int x = Integer.parseInt(xField.getText());
        int y = Integer.parseInt(yField.getText());

        try {
            Robot robot = new Robot();
            Color color = robot.getPixelColor(x, y);
            colorTextArea.setText("Color at (" + x + ", " + y + "): " + color);
        } catch (AWTException e) {
            log.error("getColorAtCoordinates", e);
        }
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        log.info("stop app");
        HotKeyApp.unregisterHotKey();
        System.exit(0);
    }
}