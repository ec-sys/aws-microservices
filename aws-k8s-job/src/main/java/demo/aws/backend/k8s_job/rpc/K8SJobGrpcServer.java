package demo.aws.backend.k8s_job.rpc;

import demo.aws.core.autogen.grpc.k8s_job.K8SJobGrpc;
import net.devh.boot.grpc.server.service.GrpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@GrpcService
public class K8SJobGrpcServer extends K8SJobGrpc.K8SJobImplBase {
    private static Logger logger = LoggerFactory.getLogger(K8SJobGrpcServer.class);
}
