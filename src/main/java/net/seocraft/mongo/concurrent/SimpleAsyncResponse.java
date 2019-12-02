package net.seocraft.mongo.concurrent;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("UnstableApiUsage")
public class SimpleAsyncResponse<T> implements AsyncResponse<T> {

    private ListenableFuture<Response<T>> responseFuture;

    public SimpleAsyncResponse(@NotNull ListenableFuture<Response<T>> responseFuture) {
        this.responseFuture = responseFuture;
    }

    public void callback(Callback<Response<T>> callback) {
        Futures.addCallback(this.responseFuture, wrapCallback(callback));
    }

    private FutureCallback<Response<T>> wrapCallback(Callback<Response<T>> callback) {
        return new FutureCallback<Response<T>>() {

            @Override
            public void onSuccess(Response<T> response) {
                callback.call(response);
            }

            @Override
            public void onFailure(Throwable throwable) {
                callback.handleException(throwable);
            }
        };
    }
}