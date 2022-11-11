package ru.nsu.leorita.data.services.opentripmap;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.nsu.leorita.Config;
import ru.nsu.leorita.data.mappers.InterestingPlaceMapper;
import ru.nsu.leorita.domain.models.InterestingPlace;
import ru.nsu.leorita.domain.services.InterestingPlacesService;

import java.util.List;

public class OpenTripMapServiceImpl implements InterestingPlacesService {
    private Retrofit retrofit;
    private OpenTripMapWebApi openTripMapWebApi;
    private String BASE_URL = Config.OPEN_TRIP_MAP_BASE_URL;
    private String API_KEY = Config.OPEN_TRIP_MAP_API_KEY;
    private String LANG = Config.LANGUAGE;


    public OpenTripMapServiceImpl() {
        this.retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build();
        openTripMapWebApi = retrofit.create(OpenTripMapWebApi.class);
    }

    @Override
    public Single<InterestingPlace> getDescriptions(String placeId) {
        return openTripMapWebApi.getDescription(LANG, placeId, API_KEY).map(InterestingPlaceMapper::createInterestingPlace);
    }

    @Override
    public Single<List<String>> getPlaces(Double lon, Double lat, Integer radius) {

        return openTripMapWebApi.getPlaces(LANG, radius, lon, lat, API_KEY, "json").map(InterestingPlaceMapper::createPlacesList);
    }
}
