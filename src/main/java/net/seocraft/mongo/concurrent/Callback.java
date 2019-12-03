package net.seocraft.mongo.concurrent;

import org.jetbrains.annotations.NotNull;

import java.util.logging.Level;
import java.util.logging.Logger;

public interface Callback<T> {

    void call(T object);

    default void handleException(@NotNull Throwable throwable){
        Logger.getGlobal().log(Level.SEVERE, "Error executing callback.", throwable);
    }

}