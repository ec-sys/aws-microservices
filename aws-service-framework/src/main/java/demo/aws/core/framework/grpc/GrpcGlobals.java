package demo.aws.core.framework.grpc;

import demo.aws.core.common_util.constant.CommonConstant;
import demo.aws.core.common_util.constant.TraceConstant;
import demo.aws.core.common_util.utils.CurrentThreadExecutor;
import io.grpc.Context;
import io.grpc.Metadata;
import lombok.SneakyThrows;

import java.util.concurrent.Executor;

public class GrpcGlobals {
    public static final Context.Key<GRPCAuthInfo> LOGIN_INFO = Context.key("zenblog.authorize");
    public static final Metadata.Key<GRPCAuthInfo> LOGIN_INFO_METADATA = Metadata.Key.of("zenblog.authorize-bin", new LoginInfoMarshaller());
    public static final Metadata.Key<String> AUTH_TOKEN_METADATA = Metadata.Key.of("zenblog.auth.token", Metadata.ASCII_STRING_MARSHALLER);
    // リクエスト情報
    public static final Context.Key<RequestInfo> REQUEST_INFO = Context.key("zenblog.request");
    // リクエスト情報キー
    public static final Metadata.Key<RequestInfo> REQUEST_INFO_METADATA = Metadata.Key.of("zenblog.request-bin", new RequestInfoMarshaller());

    // tracing
    public static final Metadata.Key<String> METADATA_TRACE_X_REQUEST_ID = Metadata.Key.of(TraceConstant.TRACE_X_REQUEST_ID, Metadata.ASCII_STRING_MARSHALLER);
    public static final Metadata.Key<String> METADATA_TRACE_X_B3_SPAN_ID = Metadata.Key.of(TraceConstant.TRACE_X_B3_SPAN_ID, Metadata.ASCII_STRING_MARSHALLER);
    public static final Metadata.Key<String> METADATA_TRACE_X_B3_TRACE_ID = Metadata.Key.of(TraceConstant.TRACE_X_B3_TRACE_ID, Metadata.ASCII_STRING_MARSHALLER);
    public static final Metadata.Key<String> METADATA_TRACE_X_B3_PARENT_SPAN_ID = Metadata.Key.of(TraceConstant.TRACE_X_B3_PARENT_SPAN_ID, Metadata.ASCII_STRING_MARSHALLER);
    public static final Metadata.Key<String> METADATA_TRACE_X_B3_SAMPLED = Metadata.Key.of(TraceConstant.TRACE_X_B3_SAMPLED, Metadata.ASCII_STRING_MARSHALLER);
    public static final Metadata.Key<String> METADATA_TRACE_X_B3_FLAGS = Metadata.Key.of(TraceConstant.TRACE_X_B3_FLAGS, Metadata.ASCII_STRING_MARSHALLER);
    // public static final Metadata.Key<String> METADATA_TRACE_GRPC_TRACE_BIN = Metadata.Key.of(TraceConstant.TRACE_GRPC_TRACE_BIN, Metadata.ASCII_STRING_MARSHALLER);
    public static final Metadata.Key<String> METADATA_TRACE_TRACE_PARENT = Metadata.Key.of(TraceConstant.TRACE_TRACE_PARENT, Metadata.ASCII_STRING_MARSHALLER);

    // Constructor
    // ------------------------------------------------------------------------
    private GrpcGlobals() {}

    // LoginInfoMarshaller class
    // ------------------------------------------------------------------------
    private static class LoginInfoMarshaller implements Metadata.BinaryMarshaller<GRPCAuthInfo> {
        @Override public byte[] toBytes(GRPCAuthInfo value) {
            return value.toBytes();
        }

        @Override public GRPCAuthInfo parseBytes(byte[] serialized) {
            return GRPCAuthInfo.newLoginInfo(serialized);
        }
    }

    private static class RequestInfoMarshaller implements Metadata.BinaryMarshaller<RequestInfo> {
        @Override public byte[] toBytes(RequestInfo value) {
            return value.toBytes();
        }

        @SneakyThrows
        @Override public RequestInfo parseBytes(byte[] serialized) {
            return RequestInfo.newRequestInfo(serialized);
        }
    }

    // Executor
    // ------------------------------------------------------------------------
    public static Executor currentThreadContextExecutor() {
        return Context.currentContextExecutor(new CurrentThreadExecutor());
    }
}

