package ru.nsu.leorita.data.services.graphhopper.models;

import com.google.gson.annotations.SerializedName;

public class GraphHopperPoint {

    @SerializedName("lng")
    public double longitude;

    @SerializedName("lat")
    public double latitude;
}
