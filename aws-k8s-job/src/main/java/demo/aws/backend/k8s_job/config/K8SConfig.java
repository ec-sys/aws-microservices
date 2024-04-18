package demo.aws.backend.k8s_job.config;

import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.Configuration;
import io.kubernetes.client.util.ClientBuilder;
import io.kubernetes.client.util.KubeConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;

@Component
public class K8SConfig {
    private static boolean isInit = false;

    @Value("${k8s.config.path}")
    private static String kubeConfigPath; // file path to your KubeConfig

    @Value("${k8s.config.path}")
    public void setPrivateName(String privateName) {
        K8SConfig.kubeConfigPath = privateName;
    }

    public static ApiClient getApiClient() {
        return getApiClient(kubeConfigPath);
    }

    public static ApiClient getApiClient(String configPath) {
        if(K8SConfig.isInit) {
            return Configuration.getDefaultApiClient();
        } else {
            try {
                var client = ClientBuilder.kubeconfig(KubeConfig.loadKubeConfig(new FileReader(configPath))).build();
                // set the global default api-client to the in-cluster one from above
                Configuration.setDefaultApiClient(client);
                K8SConfig.isInit = true;

                return client;
            } catch (IOException exception) {
                return null;
            }
        }
    }
}
