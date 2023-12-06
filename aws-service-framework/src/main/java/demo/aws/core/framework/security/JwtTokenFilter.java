package demo.aws.core.framework.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import demo.aws.core.framework.constant.CommonConstant;
import demo.aws.core.framework.security.model.LoginInfo;
import demo.aws.core.framework.utils.CommonUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if(!CommonUtil.getPublicUrlPaths().contains(request.getRequestURI())) {
            String loginInfoStr = request.getHeader(CommonConstant.HEADER_AUTH_INFO);
            if(StringUtils.isNotEmpty(loginInfoStr)) {
                ObjectMapper objectMapper = new ObjectMapper();
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        objectMapper.readValue(loginInfoStr, LoginInfo.class), null, null);
                authentication.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(request, response);
    }
}
