package net.seocraft.mongo.models;

import org.jetbrains.annotations.NotNull;

public interface User extends Model {

    @NotNull String getName();

    @NotNull Status getStatus();

}