package ru.nsu.leorita.ui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ru.nsu.leorita.data.services.graphhopper.GraphHopperServiceImpl;
import ru.nsu.leorita.data.services.opentripmap.OpenTripMapServiceImpl;
import ru.nsu.leorita.data.services.openweather.OpenWeatherServiceImpl;
import ru.nsu.leorita.domain.services.InterestingPlacesService;
import ru.nsu.leorita.domain.services.LocationService;
import ru.nsu.leorita.domain.services.WeatherService;

public class App extends Application {

    RootController controller;

    @Override
    public void start(Stage stage) throws Exception {
        LocationService locationService = new GraphHopperServiceImpl();
        InterestingPlacesService interestingPlacesService = new OpenTripMapServiceImpl();
        WeatherService weatherService = new OpenWeatherServiceImpl();
        controller = new RootController(locationService, interestingPlacesService, weatherService);

        FXMLLoader loader = new FXMLLoader();
        loader.setController(controller);
        loader.setLocation(getClass().getClassLoader().getResource("layout.fxml"));
        Parent rootLayout = loader.load();
        controller.onStart();

        Scene scene = new Scene(rootLayout);
        stage.setScene(scene);
        stage.setTitle("Places");
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
