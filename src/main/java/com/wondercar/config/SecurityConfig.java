package com.wondercar.config;

import com.wondercar.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

/**
 * SpringSecurity配置类
 *
 * @author: wondercar
 * @since: 2023-03-19
 */
@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true,
        prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private LoginService loginService;
    @Autowired
    private PersistentTokenRepository tokenRepository;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // 自定义403页面
                .exceptionHandling()
                .accessDeniedPage("/unauth")
                .and()
                // 表单登录
                .formLogin()
                .loginPage("/login")// 登录页面请求URL
                .loginProcessingUrl("/login")// 登录表单请求URL
                .defaultSuccessUrl("/main")// 登录成功跳转URL
                .failureUrl("/fail")// 登录失败跳转URL
                .and()
                .logout()
                .logoutUrl("/logout")// 注销登录请求URL
                .logoutSuccessUrl("/login")// 注销成功跳转URL
                .and()
                // 认证配置
                .authorizeRequests()
                // 放行指定请求路径
                .antMatchers("/layui/**","/login")
                .permitAll()
                // 权限验证
                //.antMatchers("/findAll").hasAuthority("admin")
                .antMatchers("/find").hasAnyAuthority("menu:system")// role
                .antMatchers("/findAll").hasRole("sysMag")// ROLE_xx
                // 任何请求都需要身份认证
                .anyRequest()
                .authenticated();
        // 开启记住我功能
        http.rememberMe()
                .tokenValiditySeconds(10)// 记住我token有效期 单位秒
                .tokenRepository(tokenRepository)
                .userDetailsService(loginService);
        // 关闭csrf
        http.csrf().disable();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
