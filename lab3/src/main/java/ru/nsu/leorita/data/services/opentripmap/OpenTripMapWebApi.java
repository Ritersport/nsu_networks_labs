package ru.nsu.leorita.data.services.opentripmap;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import ru.nsu.leorita.data.services.opentripmap.models.OpenTripDescriptionResponse;
import ru.nsu.leorita.data.services.opentripmap.models.OpenTripPlacesResponse;

public interface OpenTripMapWebApi {
    @GET("{lang}/places/radius")
    Single<OpenTripPlacesResponse> getPlaces(
            @Path("lang") String language,
            @Query("radius") Integer radius,
            @Query("lon") Double longitude,
            @Query("lat") Double latitude,
            @Query("apikey") String apiKey,
            @Query("format") String format
    );

    @GET("{lang}/places/xid/{xid}")
    Single<OpenTripDescriptionResponse> getDescription(
            @Path("lang") String language,
            @Path("xid") String placeId,
            @Query("apikey") String apiKey
    );
}
