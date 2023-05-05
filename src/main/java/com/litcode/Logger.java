package com.litcode;

public class Logger {
    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(Logger.class);

    public static void logInfo(String message) {
        LOGGER.info(message);
    }
}
