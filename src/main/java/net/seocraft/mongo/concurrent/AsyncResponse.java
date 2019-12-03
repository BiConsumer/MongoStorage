package net.seocraft.mongo.concurrent;

import org.jetbrains.annotations.NotNull;

public interface AsyncResponse<T> {

    void callback(@NotNull Callback<Response<T>> callback);

}