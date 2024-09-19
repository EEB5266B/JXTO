module saltedfish.app {
    requires ch.qos.logback.core;
    requires com.sun.jna;
    requires com.sun.jna.platform;
    requires java.datatransfer;
    requires java.desktop;
    requires javafx.controls;
    requires javafx.graphics;
    requires jintellitype;
    requires org.apache.commons.lang3;
    requires org.slf4j;
    requires org.yaml.snakeyaml;
    requires ch.qos.logback.classic;

    exports com.eeb5266b.saltedfish.app.config to org.yaml.snakeyaml;
    exports com.eeb5266b.saltedfish.app;
}