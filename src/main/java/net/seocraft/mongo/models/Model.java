package net.seocraft.mongo.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public interface Model {

    @JsonProperty("_id")
    String getId();

}