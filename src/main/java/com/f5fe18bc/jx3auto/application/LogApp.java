package com.f5fe18bc.jx3auto.application;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import org.slf4j.LoggerFactory;

/**
 * 日志
 */
public class LogApp {

    public static final org.slf4j.Logger log = LoggerFactory.getLogger(LogApp.class);

    /**
     * 修改指定名称的logger对象的日志级别
     *
     * @param loggerName logger对象的名称
     * @param level      新的日志级别
     */
    public static void changeLevel(String loggerName, Level level) {
        // 输出警告日志，记录即将修改的日志级别
        log.warn("修改 {} Log 级别为 {}", loggerName, level);

        // 获取LoggerFactory的上下文对象
        LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
        // 根据logger名称获取对应的logger对象
        Logger logger = context.getLogger(loggerName);
        // 设置新的日志级别
        logger.setLevel(level);
    }

    /**
     * 更改根记录器的日志级别
     *
     * @param level 要更改成的日志级别，不能为null
     */
    public static void changeRootLevel(Level level) {
        // 调用changeLevel方法，将根记录器的日志级别设为指定的级别
        changeLevel("root", level);
    }

    /**
     * 更改根记录器的日志级别
     *
     * @param level 要更改成的日志级别，不能为null
     */
    public static void changeRootLevel(String level) {
        // 调用changeLevel方法，将根记录器的日志级别设为指定的级别
        changeLevel("root", Level.valueOf(level));
    }
}