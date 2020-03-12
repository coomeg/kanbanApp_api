package com.jp.kanbannapp;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class KanbanSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	// ログイン処理の認証ルールを設定
        http.authorizeRequests()
                .antMatchers("/public/**").permitAll() // 認証なしでアクセス可能なパス
                .anyRequest().authenticated() // それ以外は認証が必要
                .and()
            .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/public/login") // ログインフォームのアクションに指定したURL[action="@{/login}"]を設定
                .usernameParameter("userId") // ログインフォームのユーザー欄のname属性を設定
                .passwordParameter("password") // ログインフォームのパスワード欄のname属性を設定
                .successForwardUrl("/") // ログイン成功時に遷移するURL
                .failureUrl("/login") // ログイン失敗時に遷移するURL
                .permitAll()
                .and()
            .logout()
                .logoutUrl("/logout")
                .permitAll()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"));
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/favicon.ico", "/css/**", "/js/**", "/images/**", "/fonts/**");
    }
}
