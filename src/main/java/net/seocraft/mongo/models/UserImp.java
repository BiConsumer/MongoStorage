package net.seocraft.mongo.models;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.beans.ConstructorProperties;

@Getter
public class UserImp implements User {

    @JsonProperty("_id")
    @Getter(AccessLevel.NONE)
    @NotNull private String id;

    @NotNull private String name;

    @NotNull private Status status;

    @ConstructorProperties({"id", "name", "status"})
    public UserImp(@NotNull String id, @NotNull String name, @NotNull Status status) {
        this.id = id;
        this.name = name;
        this.status = status;
    }

    @Override
    public String id() {
        return id;
    }
}