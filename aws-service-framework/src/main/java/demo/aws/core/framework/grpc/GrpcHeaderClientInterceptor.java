package demo.aws.core.framework.grpc;

import demo.aws.core.common_util.model.AuthInfo;
import demo.aws.core.common_util.model.TraceInfo;
import io.grpc.CallOptions;
import io.grpc.Channel;
import io.grpc.ClientCall;
import io.grpc.ClientInterceptor;
import io.grpc.Context;
import io.grpc.ForwardingClientCall;
import io.grpc.ForwardingClientCallListener;
import io.grpc.Metadata;
import io.grpc.MethodDescriptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;

public class GrpcHeaderClientInterceptor implements ClientInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(GrpcHeaderClientInterceptor.class);

    @Override
    public <ReqT, RespT> ClientCall<ReqT, RespT> interceptCall(MethodDescriptor<ReqT, RespT> method, CallOptions call, Channel channel) {

        final RequestInfo outerContext = GrpcRequestInfoHeaderUtil.createRequestInfo();

        return new ForwardingClientCall.SimpleForwardingClientCall<ReqT, RespT>(channel.newCall(method, call)) {
            @Override
            public void start(ClientCall.Listener<RespT> responseListener, Metadata headers) {
                GRPCAuthInfo grpcAuthInfo = GrpcGlobals.LOGIN_INFO.get(Context.current());

                if (grpcAuthInfo == null) {
                    try {
                        AuthInfo authInfo =  (AuthInfo)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                        grpcAuthInfo = new GRPCAuthInfo();
                        grpcAuthInfo.setAuthInfo(authInfo);
                        grpcAuthInfo.setRequestInfo(outerContext);
                    } catch (Exception e) {
                        // 認証エラーをチェックする必要はないので無視する
                    }
                }

                if (grpcAuthInfo != null) {
                    // custom header
                    headers.put(GrpcGlobals.LOGIN_INFO_METADATA, grpcAuthInfo);

                    // trace header
                    TraceInfo traceInfo = grpcAuthInfo.getAuthInfo().getTraceInfo();
                    headers.put(GrpcGlobals.METADATA_TRACE_X_REQUEST_ID, traceInfo.getRequestId());
                    headers.put(GrpcGlobals.METADATA_TRACE_X_B3_TRACE_ID, traceInfo.getTraceId());
                    headers.put(GrpcGlobals.METADATA_TRACE_X_B3_SPAN_ID, traceInfo.getSpanid());
                    headers.put(GrpcGlobals.METADATA_TRACE_X_B3_PARENT_SPAN_ID, traceInfo.getParentSpanId());
                    headers.put(GrpcGlobals.METADATA_TRACE_X_B3_FLAGS, traceInfo.getFlags());
                    headers.put(GrpcGlobals.METADATA_TRACE_X_B3_SAMPLED, traceInfo.getSampled());
                    // headers.put(GrpcGlobals.METADATA_TRACE_GRPC_TRACE_BIN, traceInfo.getGrpcTraceBin());
                    headers.put(GrpcGlobals.METADATA_TRACE_TRACE_PARENT, traceInfo.getTraceParent());
                }

                final RequestInfo requestInfo = GrpcRequestInfoHeaderUtil.setRequestInfoHeader(headers, outerContext);

                super.start(new ForwardingClientCallListener.SimpleForwardingClientCallListener<RespT>(responseListener) {

                    @Override
                    public void onMessage(RespT message) {
                        if (logger.isInfoEnabled()) {
                            logger.info("RPC request. method: {} [{}]", method.getFullMethodName(), requestInfo.getRequestId());
                        }
                        super.onMessage(message);
                    }

                    @Override
                    public void onHeaders(Metadata headers) {
                        super.onHeaders(headers);
                    }

                }, headers);
            }
        };
    }
}

