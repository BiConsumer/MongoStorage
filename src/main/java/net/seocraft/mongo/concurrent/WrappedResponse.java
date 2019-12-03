package net.seocraft.mongo.concurrent;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.function.Consumer;

public class WrappedResponse<T> implements Response<T> {

    @NotNull private Status status;
    @Nullable private T response;
    @Nullable private Exception throwedException;

    /**
     * Constructor of async response
     * @param throwedException shouldn't be null when an exception was thrown during the async block
     * @param status should be SUCCESS when throwedException is null or ERROR when response is null
     * @param response shouldn't be null when an exception was never thrown during the async block
     */
    public WrappedResponse(@NotNull Status status, @Nullable T response, @Nullable Exception throwedException) {
        this.status = status;
        this.throwedException = throwedException;
        this.response = response;
    }

    @Override
    public @NotNull Status getStatus() {
        return this.status;
    }

    @Override
    public @NotNull Optional<T> getResponse() {
        return Optional.ofNullable(this.response);
    }

    @Override
    public @NotNull Optional<Exception> getThrowedException() {
        return Optional.ofNullable(this.throwedException);
    }

    @Override
    public boolean isSuccessful() {
        return status == Status.SUCCESS;
    }

    @Override
    public void ifSuccessful(@NotNull Consumer<? super T> consumer) {
        if (this.status == Status.SUCCESS)
            consumer.accept(this.response);
    }
}