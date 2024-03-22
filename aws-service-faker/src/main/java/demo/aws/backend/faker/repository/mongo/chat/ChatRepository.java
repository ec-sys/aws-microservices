package demo.aws.backend.faker.repository.mongo.chat;

import demo.aws.backend.faker.config.mongo.Mongo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ChatRepository {

    @Mongo(value = "chat")
    private MongoTemplate chatMongoTemplate;
    @Mongo(value = "rtm")
    private MongoTemplate rtmMongoTemplate;

    public void save(Object document) {
        chatMongoTemplate.save(document);
    }
    public void removeAll(List<? extends Object> objects) {
        objects.forEach(object -> chatMongoTemplate.remove(object));
    }
    public <T>  void insertAllNoReturn(List<T> documents) {
        chatMongoTemplate.insertAll(documents);
    }

    public <T> List<T> insertAllWithReturn(List<T> documents) {
        return new ArrayList<>(chatMongoTemplate.insertAll(documents));
    }
}

