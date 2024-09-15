package com.f5fe18bc.jx3.auto.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 设置类，用于读取和管理应用程序的设置
 */
@Slf4j
@Data
public class Settings {

    private static final String SETTINGS_FILE = "settings.yaml";
    private static Settings settings;

    private System system;
    private HotKey hotKey;

    public static Settings getSettings() {

        if (settings == null) {
            settings = readSetting();
        }

        return settings;
    }

    /**
     * 从项目目录获取 settings.yaml 文件
     * 如果不存在则从 resources 下的 settings.yaml 复制到项目目录
     *
     * @return Settings 对象，包含从 settings.yaml 文件读取的设置
     * @throws RuntimeException 如果文件不存在或读取时发生错误
     */
    private static Settings readSetting() {
        // 项目根目录下的 settings.yaml 文件路径
        Path projectSettingsPath = Paths.get(SETTINGS_FILE);

        // 检查项目根目录下的 settings.yaml 是否存在
        if (!Files.exists(projectSettingsPath)) {
            log.info("The system starts initializing the default configuration");

            // resources 目录下的 settings.yaml 文件路径
            URL resourceUrl = Settings.class.getClassLoader().getResource(SETTINGS_FILE);
            if (resourceUrl != null) {
                try (InputStream in = resourceUrl.openStream(); OutputStream out = new FileOutputStream(SETTINGS_FILE)) {
                    // 从 resources 下的 settings.yaml 复制内容到项目根目录
                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = in.read(buffer)) > 0) {
                        out.write(buffer, 0, length);
                    }
                } catch (IOException e) {
                    // 复制失败时输出错误信息
                    log.error("copy settings.yaml error: ", e);
                    throw new RuntimeException(e);
                }
            } else {
                // 如果 resources 下没有找到 settings.yaml 文件，则输出错误信息
                log.error("{} file does not exist", SETTINGS_FILE);
                throw new RuntimeException("file does not exist");
            }
        } else {
            log.info("The system uses the configuration file in the project directory");
        }

        try (InputStream inputStream = new FileInputStream(projectSettingsPath.toFile())) {
            return new Yaml().loadAs(inputStream, Settings.class);
        } catch (FileNotFoundException e) {
            // 文件不存在时输出错误信息
            log.error("{} file does not exist", SETTINGS_FILE, e);
            throw new RuntimeException(e);
        } catch (IOException e) {
            // 其他读取文件时的错误信息
            log.error("{} file error", SETTINGS_FILE, e);
            throw new RuntimeException(e);
        }
    }

    @Data
    public static class System {

        private String logLevel;
    }

    @Data
    public static class HotKey {

        private String start;
    }
}