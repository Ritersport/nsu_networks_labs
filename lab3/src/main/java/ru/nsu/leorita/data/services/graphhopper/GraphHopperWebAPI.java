package ru.nsu.leorita.data.services.graphhopper;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;
import ru.nsu.leorita.data.services.graphhopper.models.GraphHopperResponse;


public interface GraphHopperWebAPI {

    @GET("geocode")
    Single<GraphHopperResponse> getLocations(
            @Query("q") String locationName,
            @Query("key") String apiKey
    );
}
