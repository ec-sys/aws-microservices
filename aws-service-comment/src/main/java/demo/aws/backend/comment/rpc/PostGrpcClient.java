package demo.aws.backend.comment.rpc;

import demo.aws.core.autogen.grpc.post.PSTPostByIdRequest;
import demo.aws.core.autogen.grpc.post.PSTPostResponse;
import demo.aws.core.autogen.grpc.post.PostGrpc;
import demo.aws.core.framework.grpc.GrpcHeaderClientInterceptor;
import io.grpc.Channel;
import io.micrometer.core.instrument.binder.grpc.ObservationGrpcClientInterceptor;
import net.devh.boot.grpc.client.inject.GrpcClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PostGrpcClient {
    @GrpcClient(value = "post", interceptors = {GrpcHeaderClientInterceptor.class, ObservationGrpcClientInterceptor.class})
    private Channel postChannel;

    public PSTPostResponse getPostById(long postId) {
        PSTPostByIdRequest request = PSTPostByIdRequest.newBuilder()
                .setId(postId)
                .build();
        PostGrpc.PostBlockingStub stub = PostGrpc.newBlockingStub(postChannel);
        return stub.getPostById(request);
    }
}
