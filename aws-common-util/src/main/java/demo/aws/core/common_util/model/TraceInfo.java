package demo.aws.core.common_util.model;

import lombok.Data;

@Data
public class TraceInfo {
    private String requestId; // x-request-id
    private String traceId; // x-b3-traceid
    private String spanid; // x-b3-spanid
    private String parentSpanId; // x-b3-parentspanid
    private String sampled; // x-b3-sampled
    private String flags; // x-b3-flags
    private String grpcTraceBin;// grpc-trace-bin
    private String traceParent;// traceparent
}
