package net.seocraft.mongo.datamanager;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.mongodb.DB;
import net.seocraft.mongo.concurrent.AsyncResponse;
import net.seocraft.mongo.concurrent.WrappedResponse;
import net.seocraft.mongo.models.Model;
import org.jetbrains.annotations.NotNull;
import org.mongojack.JacksonDBCollection;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class MongoStorageProvider<T extends Model> implements StorageProvider<T> {

    private ListeningExecutorService executorService;
    private JacksonDBCollection<T, String> mongoCollection;

    private String dataPrefix;
    private Class<? extends T> modelClass;

    public MongoStorageProvider(ListeningExecutorService executorService, DB database, String dataPrefix, Class<T> modelClass, ObjectMapper mapper) {
        this.executorService = executorService;
        this.dataPrefix = dataPrefix;
        this.modelClass = modelClass;
        this.mongoCollection = JacksonDBCollection.wrap(database.getCollection(dataPrefix),
                modelClass,
                String.class,
                mapper
        );
    }

    @Override
    public @NotNull AsyncResponse<T> findOne(@NotNull String id) {
        return new AsyncResponse<>(this.executorService.submit(() -> {
            Optional<T> response = this.findOneSync(id);
            return response.map(t -> new WrappedResponse<>(null, WrappedResponse.Status.SUCCESS, t))
                    .orElseGet(() -> new WrappedResponse<>(null, WrappedResponse.Status.ERROR, null));
        }));
    }

    @Override
    public @NotNull AsyncResponse<Set<T>> find(@NotNull Set<String> ids) {
        return new AsyncResponse<>(this.executorService.submit(() -> new WrappedResponse<>(null, WrappedResponse.Status.SUCCESS, this.findSync(ids))));
    }

    @Override
    public @NotNull AsyncResponse<Set<T>> find(int limit) {
        return new AsyncResponse<>(this.executorService.submit(() -> new WrappedResponse<>(null, WrappedResponse.Status.SUCCESS, this.findSync(limit))));
    }

    @Override
    public @NotNull AsyncResponse<Set<T>> find() {
        return new AsyncResponse<>(this.executorService.submit(() -> new WrappedResponse<>(null, WrappedResponse.Status.SUCCESS, this.findSync())));
    }

    @Override
    public AsyncResponse<Void> save(@NotNull T object) {
        return new AsyncResponse<>(this.executorService.submit(() -> {
            this.saveSync(object);
            return null;
        }));
    }

    @Override
    public AsyncResponse<Void> save(@NotNull Set<T> objects) {
        return new AsyncResponse<>(this.executorService.submit(() -> {
            this.saveSync(objects);
            return null;
        }));
    }

    @Override
    public AsyncResponse<Void> delete(@NotNull String id) {
        return new AsyncResponse<>(this.executorService.submit(() -> {
            this.deleteSync(id);
            return null;
        }));
    }

    @Override
    public AsyncResponse<Void> delete(@NotNull T object) {
        return new AsyncResponse<>(this.executorService.submit(() -> {
            this.deleteSync(object);
            return null;
        }));
    }

    @Override
    public AsyncResponse<Void> delete(@NotNull Set<T> objects) {
        return new AsyncResponse<>(this.executorService.submit(() -> {
            this.deleteSync(objects);
            return null;
        }));
    }

    @Override
    public Optional<T> findOneSync(@NotNull String id) {
        return Optional.ofNullable(mongoCollection.findOneById(id));
    }

    @Override
    public @NotNull Set<T> findSync(@NotNull Set<String> ids) {
        Set<T> objects = new HashSet<>();
        ids.forEach(id -> this.findOneSync(id).ifPresent(objects::add));
        return objects;
    }

    @Override
    public @NotNull Set<T> findSync(int limit) {
        return new HashSet<>(this.mongoCollection.find().limit(limit).toArray());
    }

    @Override
    public @NotNull Set<T> findSync() {
        return new HashSet<>(this.mongoCollection.find().toArray());
    }

    @Override
    public void saveSync(@NotNull T object) {
        this.mongoCollection.save(object);
    }

    @Override
    public void saveSync(@NotNull Set<T> objects) {
        objects.forEach(this::saveSync);
    }

    @Override
    public void deleteSync(@NotNull String id) {
        this.mongoCollection.removeById(id);
    }

    @Override
    public void deleteSync(@NotNull T object) {
        this.deleteSync(object.id());
    }

    @Override
    public void deleteSync(@NotNull Set<T> objects) {
        objects.forEach(this::deleteSync);
    }
}