package demo.aws.backend.near_by.service;

import demo.aws.backend.near_by.rpc.UaaGrpcClient;
import demo.aws.core.autogen.grpc.uaa.UAAPayloadFromTokenResponse;
import demo.aws.core.common_util.dto.JWTPayloadDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TokenService {

    @Autowired
    UaaGrpcClient uaaGrpcClient;

    public JWTPayloadDto getPayloadFromToken(String tokenId, String token) {
        UAAPayloadFromTokenResponse rpcResponse = uaaGrpcClient.getayloadFromToken(tokenId, token);
        JWTPayloadDto payload = new JWTPayloadDto();
        payload.setLoginId(rpcResponse.getLoginId());
        payload.setUserId(rpcResponse.getUserId());
        payload.setRoleNames(rpcResponse.getRoleNamesList());
        return payload;
    }
}
