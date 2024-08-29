package demo.aws.backend.near_by.api;

import demo.aws.backend.near_by.config.ws.UserWebSocket;
import demo.aws.core.common_util.model.AuthInfo;
import demo.aws.core.framework.grpc.GRPCAuthInfo;
import demo.aws.core.framework.grpc.GrpcGlobals;
import io.grpc.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

public final class Controllers {

    private static final Logger logger = LoggerFactory.getLogger(Controllers.class);

    public static void setAuthentication(Principal principal) {
        if (principal instanceof UserWebSocket) {
            UserWebSocket user = (UserWebSocket)(principal);
            AuthInfo authInfo = user.getAuthInfo();

            List<SimpleGrantedAuthority> authorityList = new ArrayList<>();
            authInfo.getRoleName().forEach(roleName -> authorityList.add(new SimpleGrantedAuthority(roleName)));
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(authInfo, null, authorityList);
            SecurityContextHolder.getContext().setAuthentication(authToken);

            logger.info("Attach login info into the context: {}-{}", authInfo.getUserId(), authInfo.getLoginId());

            GRPCAuthInfo grpcAuthInfo = new GRPCAuthInfo();
            grpcAuthInfo.setAuthInfo(authInfo);
            Context.current().withValue(GrpcGlobals.LOGIN_INFO, grpcAuthInfo).attach();
            return;
        }
        logger.warn("Could not set authentication. principal: {}", principal);
    }
}