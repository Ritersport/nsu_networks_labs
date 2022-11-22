package ru.nsu.leorita.data.mappers;

import ru.nsu.leorita.data.services.opentripmap.models.OpenTripDescriptionResponse;
import ru.nsu.leorita.data.services.opentripmap.models.OpenTripPlace;
import ru.nsu.leorita.data.services.opentripmap.models.OpenTripPlacesResponse;
import ru.nsu.leorita.domain.models.InterestingPlace;

import java.util.ArrayList;
import java.util.List;

public class InterestingPlaceMapper {
    public static InterestingPlace createInterestingPlace(OpenTripDescriptionResponse openTripDescriptionResponse) {
        InterestingPlace interestingPlace = new InterestingPlace();
        if (openTripDescriptionResponse.info != null) {
            interestingPlace.setDescription(openTripDescriptionResponse.info.description);
        }
        interestingPlace.setName(openTripDescriptionResponse.placeName);

        return interestingPlace;
    }

    public static List<String> createPlacesList(OpenTripPlacesResponse openTripPlacesResponse) {
        List<String> placeIDS = new ArrayList<>();
        for (OpenTripPlace place : openTripPlacesResponse) {
            placeIDS.add(place.id);
        }
        return placeIDS;
    }
}
