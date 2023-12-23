package demo.aws.backend.post.rpc;

import demo.aws.backend.post.service.PostService;
import demo.aws.core.autogen.grpc.post.PSTPostByIdRequest;
import demo.aws.core.autogen.grpc.post.PSTPostResponse;
import demo.aws.core.autogen.grpc.post.PostGrpc;
import demo.aws.core.framework.grpc.GrpcHeaderServerInterceptor;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@GrpcService(interceptors = GrpcHeaderServerInterceptor.class)
public class PostGrpcServer extends PostGrpc.PostImplBase {

    private static Logger logger = LoggerFactory.getLogger(PostGrpcServer.class);

    private final PostService postService;

    @Autowired
    public PostGrpcServer(PostService postService) {
        this.postService = postService;
    }

    @Override
    public void getPostById(PSTPostByIdRequest request, StreamObserver<PSTPostResponse> responseObserver) {
        responseObserver.onNext(postService.getPostById(request.getId()));
        responseObserver.onCompleted();
    }
}
