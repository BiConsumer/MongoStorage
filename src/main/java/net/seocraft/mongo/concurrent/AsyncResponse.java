package net.seocraft.mongo.concurrent;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("UnstableApiUsage")
public class AsyncResponse<T> {

    private ListenableFuture<WrappedResponse<T>> wrappedResponseListenableFuture;

    public AsyncResponse(@NotNull ListenableFuture<WrappedResponse<T>> wrappedResponseListenableFuture) {
        this.wrappedResponseListenableFuture = wrappedResponseListenableFuture;
    }

    public void callback(Callback<WrappedResponse<T>> callback) {
        Futures.addCallback(wrappedResponseListenableFuture, wrapCallback(callback));
    }

    private FutureCallback<WrappedResponse<T>> wrapCallback(Callback<WrappedResponse<T>> callback) {
        return new FutureCallback<WrappedResponse<T>>() {

            @Override
            public void onSuccess(WrappedResponse<T> wrappedResponse) {
                callback.call(wrappedResponse);
            }

            @Override
            public void onFailure(Throwable throwable) {
                callback.handleException(throwable);
            }
        };
    }
}