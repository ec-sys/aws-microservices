package demo.aws.core.framework.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import demo.aws.core.common_util.constant.CommonConstant;
import demo.aws.core.common_util.constant.TraceConstant;
import demo.aws.core.common_util.model.AuthInfo;
import demo.aws.core.common_util.model.TraceInfo;
import demo.aws.core.common_util.utils.CommonUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
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
        authObj.setTraceInfo(getTraceInfoFromHeader(request));

        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();
        authObj.getRoleName().forEach(roleName -> authorityList.add(new SimpleGrantedAuthority(roleName)));
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(authObj, null, authorityList);
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authToken);
        filterChain.doFilter(request, response);
    }

    private TraceInfo getTraceInfoFromHeader(HttpServletRequest request) {
        TraceInfo traceInfo = new TraceInfo();
        traceInfo.setRequestId(CommonUtil.toStr(request.getHeader(TraceConstant.TRACE_X_REQUEST_ID)));
        traceInfo.setTraceId(CommonUtil.toStr(request.getHeader(TraceConstant.TRACE_X_B3_TRACE_ID)));
        traceInfo.setSpanid(CommonUtil.toStr(request.getHeader(TraceConstant.TRACE_X_B3_SPAN_ID)));
        traceInfo.setParentSpanId(CommonUtil.toStr(request.getHeader(TraceConstant.TRACE_X_B3_PARENT_SPAN_ID)));
        traceInfo.setSampled(CommonUtil.toStr(request.getHeader(TraceConstant.TRACE_X_B3_SAMPLED)));
        traceInfo.setFlags(CommonUtil.toStr(request.getHeader(TraceConstant.TRACE_X_B3_FLAGS)));
        traceInfo.setGrpcTraceBin(CommonUtil.toStr(request.getHeader(TraceConstant.TRACE_GRPC_TRACE_BIN)));
        traceInfo.setTraceParent(CommonUtil.toStr(request.getHeader(TraceConstant.TRACE_TRACE_PARENT)));
        logger.info("TraceInfo - {}", traceInfo);
        return traceInfo;
    }
}
