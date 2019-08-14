package net.seocraft.mongo.concurrent;

import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

/**
 * This class will wrap an async response with a success status or an exception
 * @param <T> Interface/Class that will be wrapped
 */
public class WrappedResponse<T> {

    private Exception throwedException;
    @NotNull private Status status;
    private T response;

    /**
     * Constructor of async resposne
     * @param throwedException shouldn't be null when an exception was thrown during the async block
     * @param status should be SUCCESS when throwedException is null or ERROR when response is null
     * @param response shouldn't be null when an exception was never whrown during the async block
     */
    public WrappedResponse(Exception throwedException, @NotNull Status status, T response) {
        this.throwedException = throwedException;
        this.status = status;
        this.response = response;
    }

    /**
     * @return thrown exception when the async block fails
     */
    public Exception getThrowedException() {
        return throwedException;
    }

    /**
     * @return Async block status
     */
    public @NotNull Status getStatus() {
        return status;
    }

    /**
     * @return Response block status
     */
    public T getResponse() {
        return response;
    }

    /**
     * @return {@code true} if status is SUCCESS, otherwise {@code false}
     */
    public boolean isSuccessful() {
        return status == Status.SUCCESS;
    }

    /**
     * If response was successful, invoke the specified consumer with the value,
     * otherwise do nothing.
     * @param consumer block to be executed if a value is present
     * @throws NullPointerException if value is present and {@code consumer} is null
     */
    public void ifSuccessful(Consumer<? super T> consumer) {
        if (status == Status.SUCCESS)
            consumer.accept(response);
    }

    /**
     * Enum of the reponse status
     */
    public enum Status {
        SUCCESS, ERROR
    }
}