package net.seocraft.mongo.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;

public interface Status {

    @JsonProperty("current_mode")
    @NotNull String getCurrentMode();

    @JsonProperty("last_mode")
    @NotNull String getLastMode();

    @JsonProperty("last_updated")
    long getLastUpdated();

    void setCurrentMode(@NotNull String mode);

    void setLastMode(@NotNull String mode);

    void setLastUpdated(long lastUpdated);

}