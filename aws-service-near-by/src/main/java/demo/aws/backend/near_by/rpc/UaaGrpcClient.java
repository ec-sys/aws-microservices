package demo.aws.backend.near_by.rpc;

import demo.aws.core.autogen.grpc.uaa.UAAPayloadFromTokenRequest;
import demo.aws.core.autogen.grpc.uaa.UAAPayloadFromTokenResponse;
import demo.aws.core.autogen.grpc.uaa.UaaGrpc;
import demo.aws.core.framework.grpc.GrpcHeaderClientInterceptor;
import io.grpc.Channel;
import io.micrometer.core.instrument.binder.grpc.ObservationGrpcClientInterceptor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UaaGrpcClient {
    @GrpcClient(value = "uaa", interceptors = {GrpcHeaderClientInterceptor.class, ObservationGrpcClientInterceptor.class})
    private Channel channel;

    public UAAPayloadFromTokenResponse getayloadFromToken(String tokenId, String token) {
        UAAPayloadFromTokenRequest request = UAAPayloadFromTokenRequest.newBuilder()
                .setTokenId(tokenId)
                .setToken(token)
                .build();
        UaaGrpc.UaaBlockingStub stub = UaaGrpc.newBlockingStub(channel);
        return stub.getPayloadFromToken(request);
    }
}
