module com.f5fe18bc.jxto {
    requires java.desktop;
    requires java.naming;
    requires javafx.graphics;
    requires javafx.controls;
    requires ch.qos.logback.classic;
    requires ch.qos.logback.core;
    requires com.melloware.jintellitype;
    requires org.slf4j;
    requires org.yaml.snakeyaml;
    requires org.apache.commons.lang3;

    exports com.f5fe18bc.jxto.config to org.yaml.snakeyaml;

    exports com.f5fe18bc.jxto;
}