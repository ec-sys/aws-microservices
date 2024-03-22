package demo.aws.backend.faker.config.mongo;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface MongoClientOptionsMeta {

    int[] maxWaitTime() default {};

    int[] maxConnectionIdleTime() default {};

    int[] maxConnectionLifeTime() default {};

    int[] connectTimeout() default {};

    int[] socketTimeout() default {};
}

