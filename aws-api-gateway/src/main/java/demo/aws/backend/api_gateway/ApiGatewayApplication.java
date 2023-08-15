package demo.aws.backend.api_gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(
        basePackages = {
                "demo.aws.backend.api_gateway"
        },
        basePackageClasses = {
                demo.aws.core.framework.utils.JwtTokenUtil.class
})
public class ApiGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }

}
