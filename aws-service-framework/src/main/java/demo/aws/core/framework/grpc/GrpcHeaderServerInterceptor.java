package demo.aws.core.framework.grpc;
import demo.aws.core.common_util.model.AuthInfo;
import io.grpc.Context;
import io.grpc.Contexts;
import io.grpc.Metadata;
import io.grpc.ServerCall;
import io.grpc.ServerCallHandler;
import io.grpc.ServerInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import java.util.ArrayList;
import java.util.List;

public class GrpcHeaderServerInterceptor implements ServerInterceptor {

    // Logger
    @SuppressWarnings("unused")
    private static final Logger logger = LoggerFactory.getLogger(GrpcHeaderServerInterceptor.class);

    // Override methods
    // ------------------------------------------------------------------------
    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> call, Metadata headers, ServerCallHandler<ReqT, RespT> next) {
        Context context = Context.current();

        // Login info
        GRPCAuthInfo authInfo = headers.get(GrpcGlobals.LOGIN_INFO_METADATA);

        if (authInfo != null) {
            context = context.withValue(GrpcGlobals.LOGIN_INFO, authInfo);

            AuthInfo authObj = authInfo.getAuthInfo();
            List<SimpleGrantedAuthority> authorityList = new ArrayList<>();
            authObj.getRoleName().forEach(roleName -> authorityList.add(new SimpleGrantedAuthority(roleName)));
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(authObj, null, authorityList);
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }

        if (authInfo != null) {
            return Contexts.interceptCall(context, call, headers, next);
        }

        return next.startCall(call, headers);
    }
}
