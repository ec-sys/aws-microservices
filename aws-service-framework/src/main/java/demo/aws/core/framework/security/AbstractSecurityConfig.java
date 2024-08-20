package demo.aws.core.framework.security;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

public class AbstractSecurityConfig {
    private String[] WHITE_LIST_URL = {
            "/auth/login", "/actuator/**"
    };
    protected void addWhiteListUrl(List<String> whiteListUrls) {
        if(CollectionUtils.isNotEmpty(whiteListUrls)) {
            List<String> newUrls = new ArrayList<>();
            newUrls.addAll(whiteListUrls);
            for (String url : WHITE_LIST_URL) {
                newUrls.add(url);
            }
            WHITE_LIST_URL = newUrls.toArray(String[]::new);
        }
    }

    @Autowired
    private JwtAuthFilter jwtAuthFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req ->
                        req.requestMatchers(WHITE_LIST_URL)
                                .permitAll()
                                .anyRequest()
                                .authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
        ;

        return http.build();
    }
}
