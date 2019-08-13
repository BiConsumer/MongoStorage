package net.seocraft.mongo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import net.seocraft.mongo.datamanager.MongoStorageProvider;
import net.seocraft.mongo.datamanager.StorageProvider;
import net.seocraft.mongo.models.StatusImp;
import net.seocraft.mongo.models.Status;
import net.seocraft.mongo.models.User;
import net.seocraft.mongo.models.UserImp;

public class MongoManager {

    @Inject private ObjectMapper mapper;

    @Inject private ListeningExecutorService executorService;

    public void testStorage() {
        MongoModule module = new MongoModule();
        Injector injector = module.createInjector();
        injector.injectMembers(this);

        MongoClient client = new MongoClient(new ServerAddress());

        DB database = client.getDB("test");

        StorageProvider<User> userStorageProvider = new MongoStorageProvider<>(executorService, database, "user", User.class, mapper);

        String id ="653b24ae-7d0e-4f36-9bca-6944a6b1e51e";

        Status status = new StatusImp("offline", "somewhere", System.currentTimeMillis());

        userStorageProvider.saveSync(new UserImp(id, "lel", status));

        userStorageProvider.findOneSync(id).ifPresent(user -> {
            try {
                System.out.println(mapper.writeValueAsString(user));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }
}