package ru.nsu.leorita.data.mappers;

import ru.nsu.leorita.data.services.graphhopper.models.GraphHopperLocation;
import ru.nsu.leorita.data.services.graphhopper.models.GraphHopperResponse;
import ru.nsu.leorita.domain.models.Location;
import ru.nsu.leorita.domain.models.Point;

import java.util.ArrayList;
import java.util.List;

public class LocationsMapper {
    public static List<Location> createLocations(GraphHopperResponse graphHopperResponse) {
        List<Location> locations = new ArrayList<>();

        List<GraphHopperLocation> graphHopperLocations = graphHopperResponse.hits;
        for (GraphHopperLocation graphHopperLocation : graphHopperLocations) {

            Location location = new Location();
            location.setCity(graphHopperLocation.city);
            location.setCountry(graphHopperLocation.country);
            location.setName(graphHopperLocation.locationName);
            location.setHouseNumber(graphHopperLocation.houseNumber);
            location.setStreet(graphHopperLocation.street);
            location.setPoint(new Point(graphHopperLocation.point.longitude, graphHopperLocation.point.latitude));

            locations.add(location);
        }
        return locations;
    }
}
