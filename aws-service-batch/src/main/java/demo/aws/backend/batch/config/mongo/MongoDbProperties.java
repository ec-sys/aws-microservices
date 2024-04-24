package demo.aws.backend.batch.config.mongo;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

@Data
@ConfigurationProperties("mongo")
public class MongoDbProperties {

    /**
     * MongoDB client all service.
     */
    private List<Client> clients = new ArrayList<>();

    /**
     * MongoDB client per sevice.
     */
    @Data
    public static class Client {
        private String name;
        private String uri = "mongodb://localhost:27017/test";
    }
}