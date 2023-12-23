package demo.aws.core.framework.grpc;

import io.grpc.Context;
import io.grpc.Metadata;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.UUID;

@Slf4j
public class GrpcRequestInfoHeaderUtil {
    protected static ThreadLocal<RequestInfo> origin = new ThreadLocal<>();

    public static void setChainedContext(RequestInfo source) {
        origin.set(source);
    }

    public static RequestInfo setRequestInfoHeader(Metadata headers, RequestInfo requestInfo) {
        if (requestInfo == null) {
            log.warn("requestInfo is not passed from outer context");
            requestInfo = createRequestInfo();
        }
        headers.put(GrpcGlobals.REQUEST_INFO_METADATA, requestInfo);
        return requestInfo;
    }
    public static RequestInfo createRequestInfo() {
        RequestInfo requestInfo = GrpcGlobals.REQUEST_INFO.get(Context.current());
        String podName = System.getenv("POD_NAME");
        if (requestInfo == null) {
            if (origin.get() != null) {
                requestInfo = origin.get();
            } else {
                UUID uuid = UUID.randomUUID();
                requestInfo = new RequestInfo(uuid.toString(), "dangling", podName);
            }
        }
        if (StringUtils.isEmpty(requestInfo.getRequestId())) {
            UUID uuid = UUID.randomUUID();
            requestInfo.setRequestId(uuid.toString());
        }
        if (StringUtils.isEmpty(requestInfo.getReferer())) {
            requestInfo.setReferer(podName);
        } else {
            String attach = " chained:" + podName;
            if (!requestInfo.getReferer().endsWith(podName)) {
                requestInfo.setReferer(requestInfo.getReferer() + attach);
            }
        }
        return requestInfo;
    }
}
