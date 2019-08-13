package net.seocraft.mongo.models;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.module.SimpleAbstractTypeResolver;
import com.fasterxml.jackson.databind.module.SimpleModule;

public class InterfaceDeserializer {

    public static SimpleModule getAbstractTypes() {
        SimpleModule module = new SimpleModule("InterfaceDeserializerModule", Version.unknownVersion());
        SimpleAbstractTypeResolver resolver = new SimpleAbstractTypeResolver();
        resolver.addMapping(Status.class, StatusImp.class)
                .addMapping(User.class, UserImp.class);
        module.setAbstractTypes(resolver);
        return module;
    }
}