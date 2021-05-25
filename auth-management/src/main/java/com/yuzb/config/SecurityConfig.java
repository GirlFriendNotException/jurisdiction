package com.yuzb.config;


import com.yuzb.filter.JWTAuthorizationFilter;
import com.yuzb.filter.LoginFilter;
import com.yuzb.model.Authority;
import com.yuzb.service.AuthorityService;
import com.yuzb.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


import java.util.List;
import java.util.function.Consumer;

/*
 * @Author: yuzb
 * @Date: 2021/5/22
 * @Description:
 **/
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    AuthorityService authorityService;

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    UserServiceImpl userService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(HttpMethod.GET, "/vc.jpg");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry
                expressionInterceptUrlRegistry = http.authorizeRequests();
        List<Authority> authorityList = authorityService.findAllAuthority();
        authorityList.forEach(new Consumer<Authority>() {
            @Override
            public void accept(Authority authority) {
                expressionInterceptUrlRegistry.antMatchers(authority.getUrl()).
                        hasAnyAuthority(authority.getPermTag());
            }
        });

        expressionInterceptUrlRegistry
                .and()
                .addFilter(new JWTAuthorizationFilter(authenticationManager()))
                .addFilter(new LoginFilter(authenticationManager()))
                .csrf().disable() // 不需要session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
}
