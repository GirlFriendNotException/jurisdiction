package com.yuzb.filter;


import com.yuzb.utils.JWTUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


/*
 * @Author: yuzb
 * @Date: 2021/5/23
 * @Description: 整合jwt
 **/
public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    public JWTAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String tokenHeader = request.getHeader("authentica-token");
        if (StringUtils.isBlank(tokenHeader)) {
            throw new RuntimeException("没有携带token");
        }
        // 如果请求头中有token，则进行解析，并且设置认证信息
        SecurityContextHolder.getContext().setAuthentication(getAuthentication(tokenHeader));
        super.doFilterInternal(request, response, chain);
    }

    // 这里从token中获取用户信息并新建一个token
    private UsernamePasswordAuthenticationToken getAuthentication(String tokenHeader) {
        String username;
        try {
             username = JWTUtils.getUsername(tokenHeader);
        }catch (Exception e){
            throw  new RuntimeException("token无效");
        }
            List<SimpleGrantedAuthority> userRoles = JWTUtils.getUserRole(tokenHeader);
            return new UsernamePasswordAuthenticationToken(username, null, userRoles);


    }
}
