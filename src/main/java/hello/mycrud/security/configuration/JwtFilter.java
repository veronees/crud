package hello.mycrud.security.configuration;

import hello.mycrud.crud.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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
    private final JwtUtil jwtUtil;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        /**
         * UserDetailsService, UserDetails를 사용한 인증 로직
         */
        //헤더에서 토큰 꺼내기
        String token = request.getHeader("Authorization");
        System.out.println("token = " + token);

        if (token != null){
            String jwt = token.split(" ")[1];
            String tokenType = JwtUtil.getTokenType(jwt, secretKey);
            if (request.getRequestURI().equals("/reissue")) {
                if (!JwtUtil.isExpired(jwt, secretKey) && tokenType.equals("refreshToken")) {
                    Authentication authentication = jwtUtil.getAuthentication(jwt);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
            else {
                if (!JwtUtil.isExpired(jwt, secretKey) && tokenType.equals("accessToken")) {
                    Authentication authentication = jwtUtil.getAuthentication(jwt);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
            filterChain.doFilter(request, response);
        }
        filterChain.doFilter(request, response);
    }
}
//        //헤더에서 토큰 꺼내기
//        String token = request.getHeader("Authorization");
//        String requestURI = request.getRequestURI();
//        System.out.println("requestURI = " + requestURI);
//
//        if (token != null) {
//            String jwt = token.split(" ")[1];
//            String tokenType = JwtUtil.getTokenType(jwt, secretKey);
//
//            //"/reissue"를 제외한 모든 URI에서
//            if (requestURI != "/reissue") {
//                //tokenType이 리프레시토큰이면
//                if (tokenType == "refreshToken") {
//                    filterChain.doFilter(request, response);
//                    return;
//                }
//                System.out.println("token = " + token);
//                //에세스 토큰이면
//                if (tokenType == "accessToken") {
//                    if (!JwtUtil.isExpired(jwt, secretKey)) {
//                        Authentication authentication = jwtUtil.getAuthentication(jwt);
//                        SecurityContextHolder.getContext().setAuthentication(authentication);
//                    }
//                }
//
//                filterChain.doFilter(request, response);
//            }
//
//            if (requestURI == "/reissue") {
//                if (tokenType == "refreshToken") {
//                    if (!JwtUtil.isExpired(jwt, secretKey)) {
//                        Authentication authentication = jwtUtil.getAuthentication(jwt);
//                        SecurityContextHolder.getContext().setAuthentication(authentication);
//                    }
//                }
//                if (tokenType == "accessToken") {
//                    filterChain.doFilter(request, response);
//                    return;
//                }
//            }
//        }
//
//        filterChain.doFilter(request, response);




        /**
         * JWT에서 username이랑 role 꺼내서 안증하는 로직(UserDetailsService사용 x)
         */
//        //요청 헤더에서 Authorization꺼내기. 여기에 token값이 넘어옴
//        String authorization = request.getHeader("Authorization");
//
//
//        // token 없는 경우
//        if (authorization == null) {
//            filterChain.doFilter(request, response);
//            return;
//        }
//
//        //jwt 꺼내기
//        //Bearer asdjfakehfl.wehfuiasdf12.faewfa << 이런식의 토큰값이라 앞에 "Bearer "를 짤라냄
//        String token = authorization.split(" ")[1];
//
//
//        //token 만료 되었는지 check
//        if(JwtUtil.isExpired(token, secretKey)) {
//            filterChain.doFilter(request, response);
//            return;
//        }
//
//        //claims에 넣어둔 username값 꺼내기
//        String username = JwtUtil.getUsername(token, secretKey);
//
//        //claims에 넣어둔 role이 저장되어있는 List꺼내기
//        List<String> role = JwtUtil.getRole(token, secretKey);
//
//
//        //권한 부여
//        UsernamePasswordAuthenticationToken authenticationToken =
//                new UsernamePasswordAuthenticationToken(username, null,
//                        role.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
//
//
//        //Detail을 넣어준다.
//        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
//        filterChain.doFilter(request, response);
