package ru.nsu.leorita;

import org.apache.log4j.Logger;
import ru.nsu.leorita.data.services.Config;
import ru.nsu.leorita.ui.App;


public class Main {
    public static Logger logger = Logger.getLogger(Main.class);

    public static void main(String[] args) {
        Config.loadConfig();
        App.launch(App.class, args);

    }
}