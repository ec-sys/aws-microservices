package demo.aws.backend.near_by.service;

public interface MessagePublisher {

    void publish(long userId, String message);
}