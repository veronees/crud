package hello.mycrud.security.service;

import hello.mycrud.crud.util.JwtUtil;
import hello.mycrud.security.domain.dto.JwtDto;
import hello.mycrud.security.domain.entity.RefreshToken;
import hello.mycrud.security.repository.RefreshTokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReIssueService {

    private final RefreshTokenRepository refreshTokenRepository;

    @Value("${jwt.secret}")
    private String secretKey;

    @Transactional
    public void save(String refreshToken) {
        RefreshToken build = RefreshToken.builder()
                .refreshToken(refreshToken)
                .expired(false)
                .revoked(false)
                .username(JwtUtil.getUsername(refreshToken, secretKey))
                .build();
        refreshTokenRepository.save(build);
    }


    //refreshtoken으로 요청해서 accesstoken 재발급 받는 로직
    @Transactional
    public String reIssueAccessToken(HttpServletRequest request, HttpServletResponse response) {
        String authorization = request.getHeader("AUTHORIZATION");

        if (authorization == null || !authorization.startsWith("Bearer ")) {
                throw new RuntimeException("refresh token과 함께 요청하십시오.");
        }

        String refreshToken = authorization.split(" ")[1];
        String username1 = JwtUtil.getUsername(refreshToken, secretKey);
        RefreshToken findToken = refreshTokenRepository.find(username1); //여기서 찾은 결과는 엔티티임
        String findTokenToString = findToken.getRefreshToken(); //엔티티에서 최종 refreshToken 값을 꺼내줘야함

        if (findTokenToString.equals(refreshToken)) {
            boolean expired = JwtUtil.isExpired(refreshToken, secretKey); //true면 만료, false면 만료x
            //토큰이 실제로 기간 만료가 아니고, expire 디비 값이 false이고, revoke 디비 값이 false이면 -> 아직 만료 안된 토큰임
            if (expired == false && findToken.isExpired() == false && findToken.isRevoked() == false) {
                String username = JwtUtil.getUsername(refreshToken, secretKey);
                String reIssuedToken = JwtUtil.reIssueAccessToken(username, secretKey);
                return reIssuedToken;
            }
        }

        //refresh token도 만료된 경우
        return "다시 로그인하십시오.";
    }
}
