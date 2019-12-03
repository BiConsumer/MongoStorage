package net.seocraft.mongo.concurrent;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.function.Consumer;

/**
 * This class will wrap an async response with a success status or an exception
 * @param <T> Interface/Class that will be wrapped
 */
public interface Response<T> {

    /**
     * @return Async block status
     */
    @NotNull Status getStatus();

    /**
     * @return Response block status
     */
    @NotNull Optional<T> getResponse();

    /**
     * @return thrown exception when the async block fails
     */
    @NotNull Optional<Exception> getThrowedException();

    /**
     * @return {@code true} if status is SUCCESS, otherwise {@code false}
     */
    boolean isSuccessful();

    /**
     * If response was successful, invoke the specified consumer with the value,
     * otherwise do nothing.
     * @param consumer block to be executed if a value is present
     * @throws NullPointerException if value is present and {@code consumer} is null
     */
    void ifSuccessful(@NotNull Consumer<? super T> consumer);

    /**
     * Enum of the reponse status
     */
    enum Status {
        SUCCESS, ERROR
    }

}