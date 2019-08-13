package net.seocraft.mongo.datamanager;

import com.google.common.util.concurrent.ListenableFuture;
import net.seocraft.mongo.models.Model;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.Set;

public interface StorageProvider<O extends Model> {

    @NotNull ListenableFuture<Optional<O>> findOne(@NotNull String id);

    @NotNull ListenableFuture<Set<O>> find(@NotNull Set<String> ids);

    @NotNull ListenableFuture<Set<O>> find(int limit);

    @NotNull ListenableFuture<Set<O>> find();

    ListenableFuture<Void> save(@NotNull O object);

    ListenableFuture<Void> save(@NotNull Set<O> objects);

    ListenableFuture<Void> delete(@NotNull String id);

    ListenableFuture<Void> delete(@NotNull O object);

    ListenableFuture<Void> delete(@NotNull Set<O> objects);

    Optional<O> findOneSync(@NotNull String id);

    @NotNull Set<O> findSync(@NotNull Set<String> idSet);

    @NotNull Set<O> findSync(int limit);

    @NotNull Set<O> findSync();

    void saveSync(@NotNull O object);

    void saveSync(@NotNull Set<O> objectSet);

    void deleteSync(@NotNull String id);

    void deleteSync(@NotNull O object);

    void deleteSync(@NotNull Set<O> objectSet);

}