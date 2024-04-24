package demo.aws.backend.batch.config.mongo;

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
