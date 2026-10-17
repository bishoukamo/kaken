package com.kitakokaken.CMS.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import com.kitakokaken.CMS.service.UserDetailsServiceImpl;

@Configuration
public class SecurityConfig {

    private final UserDetailsServiceImpl userDetailsService;

    public SecurityConfig(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {
                System.out.println("★★★ SecurityFilterChain が作成されました");

        http
            .authorizeHttpRequests(auth -> auth
            .requestMatchers("/login").permitAll()
            .requestMatchers("/user/new").permitAll()
            .requestMatchers("/admin/**").hasRole("teach")
            .requestMatchers("/siyoulog").hasRole("teach")
            .requestMatchers("/nendo_update").hasRole("teach")
            .requestMatchers("/user").hasRole("teach")
            .requestMatchers("/c_delete").hasRole("teach")
            .requestMatchers("/ptr_itiran").hasRole("teach")
            .requestMatchers("/capa").hasRole("teach")
            .requestMatchers("/reissue").hasRole("teach")
            .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .loginProcessingUrl("/login/process")
                .usernameParameter("email")
                .passwordParameter("password")
                .defaultSuccessUrl("/citiran", true)
                .failureHandler((request, response, exception) -> {
                    System.out.println("★★★★★★★★★★★★");
                    System.out.println("ログイン失敗");
                    System.out.println("メール = " + request.getParameter("email"));
                    System.out.println("失敗理由 = " + exception.getClass().getName());
                    System.out.println("メッセージ = " + exception.getMessage());
                    System.out.println("★★★★★★★★★★★★");

                    response.sendRedirect("/login?error");
                })
                .successHandler((request, response, authentication) -> {
                    System.out.println("★★★★★★★★★★★★");
                    System.out.println("ログイン成功！");
                    System.out.println("ユーザー = " + authentication.getName());
                    System.out.println("★★★★★★★★★★★★");

                    response.sendRedirect("/home");
                })
                .permitAll()
            )
            .logout(logout -> logout
                .logoutSuccessUrl("/login")
                .permitAll()
            )
            .userDetailsService(userDetailsService);

        return http.build();
    }
}