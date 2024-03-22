package demo.aws.backend.faker.config.mongo;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface Mongo {

    /**
     * mongo.clients.name on application.yml
     *
     * @see MongodbProperteis
     */
    String value();
}
