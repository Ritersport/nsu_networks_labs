package ru.nsu.leorita.data.services.opentripmap.models;

import com.google.gson.annotations.SerializedName;

public class OpenTripDescriptionResponse {
    @SerializedName("name")
    public String placeName;

    @SerializedName("wikipedia_extracts.text")
    public String description;
}
