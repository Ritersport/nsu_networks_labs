package ru.nsu.leorita.domain.services;

import io.reactivex.rxjava3.core.Single;
import ru.nsu.leorita.domain.models.Location;

import java.util.List;

public interface LocationService {
    Single<List<Location>> getLocations(String name);
}
