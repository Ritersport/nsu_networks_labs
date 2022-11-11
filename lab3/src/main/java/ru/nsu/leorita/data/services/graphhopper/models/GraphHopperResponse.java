package ru.nsu.leorita.data.services.graphhopper.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GraphHopperResponse {

    @SerializedName("hits")
    public List<GraphHopperLocation> hits;
}
