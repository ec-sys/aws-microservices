package demo.aws.backend.batch.config.mongo;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import demo.aws.backend.batch.config.mongo.MongoDbProperties.Client;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.core.convert.DbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;


/**
 * MongodbConfiguration.
 */
@Configuration
@EnableConfigurationProperties(MongoDbProperties.class)
@Slf4j
public class MongoDbConfiguration implements BeanPostProcessor {

    private final MongoDbProperties properties;

    private final Map<String, Client> clients = new WeakHashMap<>();

    private final Map<String, MongoTemplate> caches = new WeakHashMap<>();

    @Autowired
    public MongoDbConfiguration(
            MongoDbProperties properties
    ) {
        this.properties = properties;
    }

    @PostConstruct
    public void postConstruct() {
        for (Client client : properties.getClients()) {
            clients.put(client.getName(), client);
        }
    }

    @Override
    @SuppressWarnings("NullableProblems")
    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        Class<?> clazz = bean.getClass();

        while (clazz != null) {
            for (Field field : clazz.getDeclaredFields()) {
                if (field.isAnnotationPresent(Mongo.class)) {
                    registerMongoTemplate(bean, field);
                }
            }
            clazz = clazz.getSuperclass();
        }

        return bean;
    }

    private void registerMongoTemplate(Object bean, Field field) {
        Mongo target = field.getAnnotation(Mongo.class);
        MongoClientOptionsMeta targetOptionMeta = field.getAnnotation(MongoClientOptionsMeta.class);
        ReadPreferenceMeta readPreferenceMeta = field.getAnnotation(ReadPreferenceMeta.class);

        String name = target.value();

        if (StringUtils.hasText(name)) {
            if (!caches.containsKey(name) && clients.containsKey(name)) {
                Client config = clients.get(name);
                MongoClient client = createMongoClient(config, targetOptionMeta, readPreferenceMeta);
                MongoDatabaseFactory factory = createDbFactory(
                        client,
                        getDatabase(config.getUri())
                );
                caches.put(name, new MongoTemplate(factory, createConverter(factory)));

                if (log.isInfoEnabled()) {
                    log.info("*** register MongoTemplate: cls={}, svc={}", bean.getClass().getName(), name);
                }
            }
            ReflectionUtils.makeAccessible(field);
            ReflectionUtils.setField(field, bean, caches.get(name));
        }
    }

    private MongoClient createMongoClient(Client client, MongoClientOptionsMeta meta, ReadPreferenceMeta readPreference) {
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyToClusterSettings(
                        builder -> builder.applyConnectionString(new ConnectionString(client.getUri()))
                ).build();
        MongoClient mongoClient = MongoClients.create(settings);
        return mongoClient;
    }

    private MongoProperties convertProperties(Client client) {
        MongoProperties props = new MongoProperties();
        props.setUri(client.getUri());
        return props;
    }

    private MongoDatabaseFactory createDbFactory(MongoClient client, String databaseName) {
        return new SimpleMongoClientDatabaseFactory(client, databaseName);
    }

    private String getDatabase(String uri) {
        String[] values = uri.split("/");
        return values[values.length - 1];
    }

    private MappingMongoConverter createConverter(MongoDatabaseFactory factory) {
        DbRefResolver resolver = new DefaultDbRefResolver(factory);
        MongoMappingContext context = new MongoMappingContext();
        MappingMongoConverter converter = new MappingMongoConverter(resolver, context);
        converter.setCustomConversions(customConversions());
        converter.afterPropertiesSet();
        return converter;
    }

    private MongoCustomConversions customConversions() {
        List<Converter<?, ?>> converters = new ArrayList<>();
//        converters.add(new EnumStringConverter());
//        converters.add(new StringEnumConverter());
        return new MongoCustomConversions(converters);
    }
}
