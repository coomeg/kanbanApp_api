package com.jp.kanbannapp;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.jp.kanbannapp.sequrity.KanbanUserDetailsService;
import com.jp.kanbannapp.sequrity.KanbanUsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class KanbanSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private KanbanUserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/public/**").permitAll()
                .antMatchers("/login.html").permitAll()
                .anyRequest().authenticated()
                .and()
//            .formLogin()
//                .loginPage("/login")
//                .loginProcessingUrl("/public/login")
//                .usernameParameter("userId")
//                .passwordParameter("password")
//                .successForwardUrl("/")
//                .failureUrl("/login.html")
//                .permitAll()
//                .and()
            .logout()
                .logoutUrl("/logout")
                .permitAll()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"));

        KanbanUsernamePasswordAuthenticationFilter jsonUsernamePasswordAuthenticationFilter =
                new KanbanUsernamePasswordAuthenticationFilter(authenticationManager());
        jsonUsernamePasswordAuthenticationFilter.setUsernameParameter("userId");
        jsonUsernamePasswordAuthenticationFilter.setPasswordParameter("password");
        // ログイン後にリダイレクトのリダイレクトを抑制
        jsonUsernamePasswordAuthenticationFilter
            .setAuthenticationSuccessHandler((req, res, auth) -> res.setStatus(HttpServletResponse.SC_OK));
        // ログイン失敗時のリダイレクト抑制
        jsonUsernamePasswordAuthenticationFilter
            .setAuthenticationFailureHandler((req, res, ex) -> res.setStatus(HttpServletResponse.SC_UNAUTHORIZED));
        http.addFilterAt(jsonUsernamePasswordAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/favicon.ico", "/css/**", "/js/**", "/images/**", "/fonts/**");
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder;
    }

    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
//        auth.userDetailsService(userDetailsService);
    }
}
