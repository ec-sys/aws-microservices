package demo.aws.backend.uaa.rpc;

import demo.aws.backend.uaa.service.TokenService;
import demo.aws.core.autogen.grpc.uaa.UAAPayloadFromTokenRequest;
import demo.aws.core.autogen.grpc.uaa.UAAPayloadFromTokenResponse;
import demo.aws.core.autogen.grpc.uaa.UaaGrpc;
import demo.aws.core.framework.grpc.GrpcHeaderServerInterceptor;
import io.grpc.stub.StreamObserver;
import io.micrometer.core.instrument.binder.grpc.ObservationGrpcServerInterceptor;
import net.devh.boot.grpc.server.service.GrpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@GrpcService(interceptors = {GrpcHeaderServerInterceptor.class, ObservationGrpcServerInterceptor.class})
public class UaaGrpcServer extends UaaGrpc.UaaImplBase {

    private static Logger logger = LoggerFactory.getLogger(UaaGrpcServer.class);

    private final TokenService tokenService;

    @Autowired
    public UaaGrpcServer(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    public void getPayloadFromToken(UAAPayloadFromTokenRequest request, StreamObserver<UAAPayloadFromTokenResponse> responseObserver) {
        responseObserver.onNext(tokenService.getPayloadFromToken(request));
        responseObserver.onCompleted();
    }
}
