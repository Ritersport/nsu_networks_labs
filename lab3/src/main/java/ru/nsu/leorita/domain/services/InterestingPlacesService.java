package ru.nsu.leorita.domain.services;

import io.reactivex.rxjava3.core.Single;
import ru.nsu.leorita.domain.models.InterestingPlace;

import java.util.List;

public interface InterestingPlacesService {
    Single<List<String>> getPlaces(Double lon, Double lat, Integer radius);

    Single<InterestingPlace> getDescriptions(String placeId);
}
