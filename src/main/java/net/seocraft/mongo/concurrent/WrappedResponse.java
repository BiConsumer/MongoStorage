package net.seocraft.mongo.concurrent;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class WrappedResponse<T> implements Response<T> {

    @Nullable private Exception throwedException;
    @NotNull private Status status;
    @Nullable private T response;

    /**
     * Constructor of async response
     * @param throwedException shouldn't be null when an exception was thrown during the async block
     * @param status should be SUCCESS when throwedException is null or ERROR when response is null
     * @param response shouldn't be null when an exception was never thrown during the async block
     */
    public WrappedResponse(@Nullable Exception throwedException, @NotNull Status status, @Nullable T response) {
        this.throwedException = throwedException;
        this.status = status;
        this.response = response;
    }

    public @Nullable Exception getThrowedException() {
        return this.throwedException;
    }

    public @NotNull Status getStatus() {
        return this.status;
    }

    public @Nullable T getResponse() {
        return this.response;
    }

    public boolean isSuccessful() {
        return status == Status.SUCCESS;
    }

    public void ifSuccessful(Consumer<? super T> consumer) {
        if (this.status == Status.SUCCESS)
            consumer.accept(response);
    }
}