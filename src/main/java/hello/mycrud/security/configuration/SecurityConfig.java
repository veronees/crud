package hello.mycrud.security.configuration;

//import hello.mycrud.security.custom.CustomUserDetailsService;
import hello.mycrud.crud.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Value("${jwt.secret}")
    private String secretKey;

//    private final CustomUserDetailsService customUserDetailsService;
    private final JwtUtil jwtUtil;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .httpBasic((auth) -> auth //rest api이므로 기본설정 미사용
                        .disable());
        http
                .csrf((auth) -> auth //
                        .disable());
        http
                .formLogin((auth) -> auth
                        .disable());
        http
                .sessionManagement((auth) -> auth
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)); //jwt로 인증하므로 세션 미사용
        http
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers(HttpMethod.GET, "/users").hasRole("ADMIN")
                        .requestMatchers("/join", "/login").permitAll()
                        .anyRequest().authenticated()
                );
        http
                .addFilterBefore(new JwtFilter(secretKey, jwtUtil), UsernamePasswordAuthenticationFilter.class); //jwt 필터
//        http
//                .userDetailsService(customUserDetailsService);

        return http.build();
    }
}
