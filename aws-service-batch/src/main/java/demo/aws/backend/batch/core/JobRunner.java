package demo.aws.backend.batch.core;

public interface JobRunner {
    default void prepare() {
    }

    void execute() throws Exception;

    default void onComplete() {
    }

    default void onError(Throwable throwable) {
    }
}
