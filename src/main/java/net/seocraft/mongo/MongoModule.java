package net.seocraft.mongo;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Scopes;
import net.seocraft.mongo.models.InterfaceDeserializer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MongoModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(ExecutorService.class).to(ListeningExecutorService.class);
        bind(ListeningExecutorService.class).toInstance(MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(8)));
        bind(ObjectMapper.class).toProvider(() -> {
            ObjectMapper mapper = new ObjectMapper().registerModule(InterfaceDeserializer.getAbstractTypes());
            mapper.setVisibility(mapper.getSerializationConfig()
                    .getDefaultVisibilityChecker()
                    .withFieldVisibility(JsonAutoDetect.Visibility.ANY)
                    .withGetterVisibility(JsonAutoDetect.Visibility.ANY)
                    .withIsGetterVisibility(JsonAutoDetect.Visibility.ANY)
                    .withSetterVisibility(JsonAutoDetect.Visibility.ANY)
                    .withCreatorVisibility(JsonAutoDetect.Visibility.PROTECTED_AND_PUBLIC));
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            return mapper;
        }).in(Scopes.SINGLETON);
    }

    public Injector createInjector() {
        return Guice.createInjector(this);
    }
}