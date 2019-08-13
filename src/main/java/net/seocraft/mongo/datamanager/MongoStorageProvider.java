package net.seocraft.mongo.datamanager;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.mongodb.DB;
import net.seocraft.mongo.models.Model;
import org.jetbrains.annotations.NotNull;
import org.mongojack.JacksonDBCollection;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.Callable;

public class MongoStorageProvider<O extends Model> implements StorageProvider<O> {

    private ListeningExecutorService executorService;
    private JacksonDBCollection<O, String> mongoCollection;

    private String dataPrefix;
    private Class<? extends O> modelClass;

    public MongoStorageProvider(ListeningExecutorService executorService, DB database, String dataPrefix, Class<O> modelClass, ObjectMapper mapper) {
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
    public @NotNull ListenableFuture<Optional<O>> findOne(@NotNull String id) {
        return this.executorService.submit(() -> this.findOneSync(id));
    }

    @Override
    public @NotNull ListenableFuture<Set<O>> find(@NotNull Set<String> ids) {
        return this.executorService.submit(() -> this.findSync(ids));
    }

    @Override
    public @NotNull ListenableFuture<Set<O>> find(int limit) {
        return this.executorService.submit(() -> this.findSync(limit));
    }

    @Override
    public @NotNull ListenableFuture<Set<O>> find() {
        return this.executorService.submit((Callable<Set<O>>) this::findSync);
    }

    @Override
    public ListenableFuture<Void> save(@NotNull O object) {
        return this.executorService.submit(() -> {
            this.saveSync(object);
            return null;
        });
    }

    @Override
    public ListenableFuture<Void> save(@NotNull Set<O> objects) {
        return this.executorService.submit(() -> {
            this.saveSync(objects);
            return null;
        });
    }

    @Override
    public ListenableFuture<Void> delete(@NotNull String id) {
        return this.executorService.submit(() -> {
            this.deleteSync(id);
            return null;
        });
    }

    @Override
    public ListenableFuture<Void> delete(@NotNull O object) {
        return this.executorService.submit(() -> {
            this.deleteSync(object);
            return null;
        });
    }

    @Override
    public ListenableFuture<Void> delete(@NotNull Set<O> objects) {
        return this.executorService.submit(() -> {
            this.deleteSync(objects);
            return null;
        });
    }

    @Override
    public Optional<O> findOneSync(@NotNull String id) {
       return Optional.ofNullable(mongoCollection.findOneById(id));
    }

    @Override
    public @NotNull Set<O> findSync(@NotNull Set<String> ids) {
        Set<O> objects = new HashSet<>();
        ids.forEach(id -> findOneSync(id).ifPresent(objects::add));
        return objects;
    }

    @Override
    public @NotNull Set<O> findSync(int limit) {
        return new HashSet<>(this.mongoCollection.find().limit(limit).toArray());
    }

    @Override
    public @NotNull Set<O> findSync() {
        return new HashSet<>(this.mongoCollection.find().toArray());
    }

    @Override
    public void saveSync(@NotNull O object) {
        this.mongoCollection.save(object);
    }

    @Override
    public void saveSync(@NotNull Set<O> objects) {
        objects.forEach(this::saveSync);
    }

    @Override
    public void deleteSync(@NotNull String id) {
        this.mongoCollection.removeById(id);
    }

    @Override
    public void deleteSync(@NotNull O object) {
        this.deleteSync(object.id());
    }

    @Override
    public void deleteSync(@NotNull Set<O> objects) {
        objects.forEach(this::deleteSync);
    }
}