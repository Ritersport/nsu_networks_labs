package ru.nsu.leorita.client.ui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.log4j.Logger;

public class App extends Application {
    Logger logger = Logger.getLogger(App.class);
    RootController controller;

    @Override
    public void start(Stage stage) throws Exception {
        logger.info("UI start");
        controller = new RootController();

        FXMLLoader loader = new FXMLLoader();
        loader.setController(controller);
        loader.setLocation(getClass().getClassLoader().getResource("layout.fxml"));
        Parent rootLayout = loader.load();
        Scene scene = new Scene(rootLayout);
        controller.onStart();

        stage.setScene(scene);
        stage.setTitle("Snakes");
        stage.show();
        stage.setOnCloseRequest((ignored) -> {
            stop();
            Platform.exit();
        });
    }

    @Override
    public void stop() {
        controller.onStop();
    }
}
