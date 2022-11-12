package ru.nsu.leorita.ui;

import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import ru.nsu.leorita.domain.models.InterestingPlace;
import ru.nsu.leorita.domain.models.Location;
import ru.nsu.leorita.domain.services.InterestingPlacesService;
import ru.nsu.leorita.domain.services.LocationService;
import ru.nsu.leorita.domain.services.WeatherService;
import ru.nsu.leorita.ui.fxext.FxSchedulers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static ru.nsu.leorita.Main.logger;


public class RootController implements ControllerLifecycle {
    private final LocationService locationService;
    private final WeatherService weatherService;
    private final InterestingPlacesService interestingPlacesService;
    private List<Location> locations;
    private List<String> placeIDs;
    private final List<InterestingPlace> places = Collections.synchronizedList(new ArrayList<InterestingPlace>(0));
    private int selectedItemIndex;
    @FXML
    public Button searchButton;
    @FXML
    public TextField searchField;
    @FXML
    public ListView<String> locationsList;

    @FXML
    public TextArea weatherText;
    @FXML
    public Slider radiusSlider;
    @FXML
    public Label radiusLabel;
    @FXML
    public ListView<String> placesList;

    Disposable placesSubscription = null;
    Disposable locationSubscription = null;
    Disposable weatherSubscription = null;

    public RootController(LocationService locationService, InterestingPlacesService interestingPlacesService, WeatherService weatherService) {
        this.locationService = locationService;
        this.interestingPlacesService = interestingPlacesService;
        this.weatherService = weatherService;
    }

    public void onButtonClick() {
        String searchText = searchField.getText();
        searchButton.setDisable(true);
        locationsList.setDisable(true);
        radiusSlider.setDisable(true);
        radiusSlider.setOpacity(0.53);
        if (locationSubscription != null) {
            locationSubscription.dispose();
        }
        locationSubscription = locationService.getLocations(searchText)
                .subscribeOn(Schedulers.io())
                .observeOn(FxSchedulers.get())
                .subscribe(locations -> {
                            this.locations = locations;
                            locationsList.getItems().clear();
                            weatherText.clear();
                            for (Location location : locations) {
                                locationsList.getItems().add(location.toString());
                            }
                            searchButton.setDisable(false);
                            locationsList.setDisable(false);
                        },
                        err -> {
                            logger.error(err);
                            locationsList.getItems().clear();
                            weatherText.clear();
                            locationsList.getItems().add("Nothing is found");
                            searchButton.setDisable(false);
                            locationsList.setDisable(false);
                        });
    }

    public void onListItemSelection() {
        this.selectedItemIndex = locationsList.getSelectionModel().getSelectedIndex();
        radiusSlider.setDisable(false);
        radiusSlider.setOpacity(1);
        Location selectedLocation = locations.get(selectedItemIndex);
        weatherText.setText("Loading...");
        if (weatherSubscription != null) {
            weatherSubscription.dispose();
        }
        weatherSubscription = weatherService.getWeather(selectedLocation.getPoint().getLat(), selectedLocation.getPoint().getLon())
                .subscribeOn(Schedulers.io())
                .observeOn(FxSchedulers.get())
                .subscribe((weather -> {
                    weatherText.setText(weather.toString());
                }),
                err -> logger.error(err)
                );
        findPlaces();
    }

    public void onDragDetected() {
        radiusLabel.setText(String.valueOf((int) radiusSlider.getValue()));
    }

    public void onDragEnded() {
        findPlaces();
    }

    public void findPlaces() {
        placesList.getItems().add("Loading...");
        Integer radius = (int) radiusSlider.getValue();

        if (placesSubscription != null) {
            placesSubscription.dispose();
        }
        placesSubscription = interestingPlacesService.getPlaces(
                        locations.get(selectedItemIndex).getPoint().getLon(),
                        locations.get(selectedItemIndex).getPoint().getLat(),
                        radius
                )
                .toObservable()
                .delay(500, TimeUnit.MILLISECONDS)
                .flatMapIterable((ids) -> ids)
                .flatMap((placeID) -> interestingPlacesService.getDescriptions((placeID)).toObservable())
                .observeOn(FxSchedulers.get())
                .doOnSubscribe((ignored) -> {
                    placesList.getItems().clear();
                    places.clear();
                })
                .doOnComplete(() -> {
                    if (places.isEmpty()) {
                        placesList.getItems().add("Nothing found");
                    }
                })
                .subscribe(
                        interestingPlace -> {
                            if (!"".equals(interestingPlace.getName())) {
                                places.add(interestingPlace);
                                placesList.getItems().add(interestingPlace.toString());
                            }
                        },
                        err -> {
                            logger.error(err);
                        }
                );
    }


    @Override
    public void onStart() {
    }

    @Override
    public void onStop() {
        if (weatherSubscription != null) {
            weatherSubscription.dispose();
        }
        if (locationSubscription != null) {
            locationSubscription.dispose();
        }
        if (placesSubscription != null) {
            placesSubscription.dispose();
        }
    }
}
