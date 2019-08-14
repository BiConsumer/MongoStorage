package net.seocraft.mongo.datamanager;

import net.seocraft.mongo.concurrent.AsyncResponse;
import net.seocraft.mongo.models.Model;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.Set;

public interface StorageProvider<T extends Model> {

    @NotNull AsyncResponse<T> findOne(@NotNull String id);

    @NotNull AsyncResponse<Set<T>> find(@NotNull Set<String> ids);

    @NotNull AsyncResponse<Set<T>> find(int limit);

    @NotNull AsyncResponse<Set<T>> find();

    AsyncResponse<Void> save(@NotNull T object);

    AsyncResponse<Void> save(@NotNull Set<T> objects);

    AsyncResponse<Void> delete(@NotNull String id);

    AsyncResponse<Void> delete(@NotNull T object);

    AsyncResponse<Void> delete(@NotNull Set<T> objects);

    Optional<T> findOneSync(@NotNull String id);

    @NotNull Set<T> findSync(@NotNull Set<String> idSet);

    @NotNull Set<T> findSync(int limit);

    @NotNull Set<T> findSync();

    void saveSync(@NotNull T object);

    void saveSync(@NotNull Set<T> objectSet);

    void deleteSync(@NotNull String id);

    void deleteSync(@NotNull T object);

    void deleteSync(@NotNull Set<T> objectSet);

}