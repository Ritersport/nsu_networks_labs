package ru.nsu.leorita.data.services.graphhopper;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.nsu.leorita.Config;
import ru.nsu.leorita.data.mappers.LocationsMapper;
import ru.nsu.leorita.domain.models.Location;
import ru.nsu.leorita.domain.services.LocationService;

import java.util.List;

public class GraphHopperServiceImpl implements LocationService {
    private final Retrofit retrofit;
    private GraphHopperWebAPI graphHopperWebAPI;
    private static final String API_KEY = Config.GRAPH_HOPPER_API_KEY;
    private static final String BASE_URL = Config.GRAPH_HOPPER_BASE_URL;

    public GraphHopperServiceImpl() {
        this.retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();
        this.graphHopperWebAPI = retrofit.create(GraphHopperWebAPI.class);
    }

    @Override
    public Single<List<Location>> getLocations(String name) {
        return graphHopperWebAPI.getLocations(name, API_KEY).map(LocationsMapper::createLocations);
    }
}
