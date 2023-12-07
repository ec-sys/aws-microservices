package demo.aws.core.framework.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import demo.aws.core.framework.constant.CommonConstant;
import demo.aws.core.framework.security.model.AuthInfo;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private static final String[] WHITE_LIST_URL = {
            "/auth/login"
    };
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        String path = request.getServletPath();
        if (request.getServletPath().contains("/auth/login")) {
            filterChain.doFilter(request, response);
            return;
        }

        String authInfo = request.getHeader(CommonConstant.HEADER_AUTH_INFO);
        if(Objects.isNull(authInfo) || authInfo.trim().isEmpty()) {
            filterChain.doFilter(request, response);
            return;
        }

        ObjectMapper mapper = new ObjectMapper();
        AuthInfo authObj = mapper.readValue(authInfo, AuthInfo.class);
        if(Objects.isNull(authObj)) {
            filterChain.doFilter(request, response);
            return;
        }

        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();
        authObj.getRoleName().forEach(roleName -> authorityList.add(new SimpleGrantedAuthority(roleName)));
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(authObj, null, authorityList);
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authToken);
        filterChain.doFilter(request, response);
    }
}