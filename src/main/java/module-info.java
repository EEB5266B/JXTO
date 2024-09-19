module jx3auto {
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

    exports com.f5fe18bc.jx3auto.config to org.yaml.snakeyaml;
    exports com.f5fe18bc.jx3auto;
}