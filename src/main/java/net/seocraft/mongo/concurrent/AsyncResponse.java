package net.seocraft.mongo.concurrent;

public interface AsyncResponse<T> {

    void callback(Callback<Response<T>> callback);

}