package hello.hellospring.config;

import hello.hellospring.jwt.JwtAccessDeniedHandler;
import hello.hellospring.jwt.JwtAuthenticationEntryPoint;
import hello.hellospring.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;


/**
 * Spring Security 설정을 구성하는 클래스.
 * JwtTokenProvider, JwtAccessDeniedHandler, JwtAuthenticationEntryPoint를 주입받음.
 */
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    // JwtTokenProvider, JwtAccessDeniedHandler, JwtAuthenticationEntryPoint를 주입받음
    private final JwtTokenProvider jwtTokenProvider;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;


    // AuthenticationManager를 빈으로 등록
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration
    ) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    // SecurityFilterChain를 빈으로 등록
    @Bean
    @Order(0)
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // CSRF 보안 설정을 해제하여 토큰 사용
        http
                .csrf().disable();

        // 세션 생성 및 사용하지 않도록 설정
        http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // JWT 예외 처리 핸들링 설정
        http
                .exceptionHandling()
                .accessDeniedHandler(jwtAccessDeniedHandler)
                .authenticationEntryPoint(jwtAuthenticationEntryPoint);

        // JwtSecurityConfig를 적용하여 JWT 필터 등록
        http.apply(new JwtSecurityConfig(jwtTokenProvider));

        return http.build();
    }
}
