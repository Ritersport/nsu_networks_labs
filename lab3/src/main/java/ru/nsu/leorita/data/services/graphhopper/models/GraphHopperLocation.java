package ru.nsu.leorita.data.services.graphhopper.models;

import com.google.gson.annotations.SerializedName;


public class GraphHopperLocation {

    @SerializedName("point")
    public GraphHopperPoint point;

    @SerializedName("country")
    public String country;

    @SerializedName("city")
    public String city;

    @SerializedName("name")
    public String locationName;

    @SerializedName("street")
    public String street;

    @SerializedName("housenumber")
    public String houseNumber;
}
