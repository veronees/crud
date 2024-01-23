package hello.mycrud.security.service;

import hello.mycrud.crud.util.JwtUtil;
import hello.mycrud.security.domain.entity.RefreshToken;
import hello.mycrud.security.repository.RefreshTokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {
    private final RefreshTokenRepository refreshTokenRepository;

    @Value("${jwt.secret}")
    private String secretKey;

    @Transactional
    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String authorization = request.getHeader("AUTHORIZATION");

        if (authorization == null || !authorization.startsWith("Bearer ")) {
            throw new RuntimeException("access token과 함께 요청하십시오.");
        }

        String accessToken = authorization.split(" ")[1];
        String username = JwtUtil.getUsername(accessToken, secretKey);
        RefreshToken refreshToken = refreshTokenRepository.find(username);

        //로그아웃이기 때문에 토큰을 만료시키고, 폐지시킨다.
        refreshToken.setExpired(true);
        refreshToken.setRevoked(true);
    }
}
