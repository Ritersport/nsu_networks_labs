package ru.nsu.leorita.data.services;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static ru.nsu.leorita.Main.logger;

public class Config {
    public static String GRAPH_HOPPER_API_KEY = null;
    public static String GRAPH_HOPPER_BASE_URL = null;
    public static String OPEN_WEATHER_API_KEY = null;
    public static String OPEN_WEATHER_BASE_URL = null;
    public static String OPEN_TRIP_MAP_BASE_URL = null;
    public static String OPEN_TRIP_MAP_API_KEY = null;
    public static String LANGUAGE = null;

    public static void loadConfig() {

        Properties properties = new Properties();
        try {
            InputStream ins = Config.class.getResourceAsStream("/config.properties");
            properties.load(ins);
            GRAPH_HOPPER_API_KEY = properties.getProperty("graph_hopper_api_key");
            GRAPH_HOPPER_BASE_URL = properties.getProperty("graph_hopper_base_url");
            OPEN_WEATHER_API_KEY = properties.getProperty("open_weather_api_key");
            OPEN_WEATHER_BASE_URL = properties.getProperty("open_weather_base_url");
            OPEN_TRIP_MAP_BASE_URL = properties.getProperty("open_trip_map_base_url");
            OPEN_TRIP_MAP_API_KEY = properties.getProperty("open_trip_map_api_key");
            LANGUAGE = properties.getProperty("language");
        } catch (IOException e) {
            logger.error("Error while loading config: " + e);
            System.exit(1);
        }
    }
}
