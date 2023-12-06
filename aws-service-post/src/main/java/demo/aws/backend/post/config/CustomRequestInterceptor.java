package demo.aws.backend.post.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;

@Component
public class CustomRequestInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate template) {
        // do something
        template.header("auth-info", "test");
    }
}