package com.yuzb.filter;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.yuzb.model.User;
import com.yuzb.utils.CommonResult;
import com.yuzb.utils.JWTUtils;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


/*
 * @Author: yuzb
 * @Date: 2021/5/23
 * @Description: 登录拦截器
 **/
public class LoginFilter extends UsernamePasswordAuthenticationFilter {
    private AuthenticationManager authenticationManager;


    public LoginFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        super.setFilterProcessesUrl("/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (!request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException(
                    "Authentication method not supported: " + request.getMethod());
        }
        String verify_code = (String) request.getSession().getAttribute("verify_code");
        checkCode(request, request.getParameter("code"), verify_code);
        return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getParameter("username"), request.getParameter("password")));
    }

    public void checkCode(HttpServletRequest request, String code, String verify_code) {
        //无论验证码是否正确都清除,客户端需重新刷新获取验证码
//        request.getSession().removeAttribute("verify_code");
        if (code == null || verify_code == null || "".equals(code) || !verify_code.toLowerCase().equals(code.toLowerCase())) {
            //验证码不正确
            throw new AuthenticationServiceException("验证码不正确");
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication auth) throws IOException {
        User user = (User) auth.getPrincipal();
        String token = JWTUtils.generateJsonWebToken(user);
        res.addHeader("authentica-token", token);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        PrintWriter out = response.getWriter();
        if (failed instanceof LockedException) {
            out.write(new ObjectMapper().writeValueAsString(CommonResult.failed("账户被锁定，请联系管理员!")));
        } else if (failed instanceof DisabledException) {
            out.write(new ObjectMapper().writeValueAsString(CommonResult.failed("账户被禁用，请联系管理员!")));
        } else if (failed instanceof BadCredentialsException) {
            out.write(new ObjectMapper().writeValueAsString(CommonResult.failed("用户名或者密码输入错误，请重新输入!")));
        } else {
            out.write(new ObjectMapper().writeValueAsString(CommonResult.failed(failed.getMessage())));
        }
        out.flush();
        out.close();
    }
}
