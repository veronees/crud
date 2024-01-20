package hello.mycrud.security.configuration;

import hello.mycrud.crud.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final String secretKey;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        //요청 헤더에서 Authorization꺼내기. 여기에 token값이 넘어옴
        String authorization = request.getHeader("Authorization");


        // token 없는 경우
        if (authorization == null) {
            filterChain.doFilter(request, response);
            return;
        }

        //jwt 꺼내기
        //Bearer asdjfakehfl.wehfuiasdf12.faewfa << 이런식의 토큰값이라 앞에 "Bearer "를 짤라냄
        String token = authorization.split(" ")[1];


        //token 만료 되었는지 check
        if(JwtUtil.isExpired(token, secretKey)) {
            filterChain.doFilter(request, response);
            return;
        }

        //claims에 넣어둔 username값 꺼내기
        String username = JwtUtil.getUsername(token, secretKey);

        //claims에 넣어둔 role이 저장되어있는 List꺼내기
        List<String> role = JwtUtil.getRole(token, secretKey);



//        //권한 부여
//        UsernamePasswordAuthenticationToken authenticationToken =
//                new UsernamePasswordAuthenticationToken(username, null, List.of(new SimpleGrantedAuthority("ADMIN")));

        //권한 부여
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(username, null,
                        role.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));


        //Detail을 넣어준다.
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request, response);
    }
}
