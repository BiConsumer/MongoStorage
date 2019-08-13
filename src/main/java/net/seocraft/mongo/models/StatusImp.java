package net.seocraft.mongo.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.beans.ConstructorProperties;

@Getter
@Setter
public class StatusImp implements Status {

    @JsonProperty("current_mode")
    @NotNull private String currentMode;

    @JsonProperty("last_mode")
    @NotNull private String lastMode;

    @JsonProperty("last_updated")
    private long lastUpdated;

    @ConstructorProperties({"current_mode", "last_mode", "last_updated"})
    public StatusImp(@NotNull String currentMode, @NotNull String lastMode, long lastUpdated) {
        this.currentMode = currentMode;
        this.lastMode = lastMode;
        this.lastUpdated = lastUpdated;
    }
}