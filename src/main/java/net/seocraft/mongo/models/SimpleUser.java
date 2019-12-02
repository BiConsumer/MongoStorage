package net.seocraft.mongo.models;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.beans.ConstructorProperties;

@Getter
public class SimpleUser implements User {

    @JsonProperty("_id")
    @NotNull private String id;

    @NotNull private String name;

    @NotNull private Status status;

    @ConstructorProperties({"id", "name", "status"})
    public SimpleUser(@NotNull String id, @NotNull String name, @NotNull Status status) {
        this.id = id;
        this.name = name;
        this.status = status;
    }

}