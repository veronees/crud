package hello.mycrud.crud.util;

import hello.mycrud.security.domain.dto.JwtDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtUtil {

    private final UserDetailsService customUserDetailsService;

    @Value("${jwt.secret}")
    private String secretKey;


//    public static String createToken(String username, String role, String secretKey, Long expireTimeMs) {
//        Claims claims = Jwts.claims();
//        claims.put("username", username);
//        claims.put("role", List.of(role)); //List.of는 변경 불가능한 리스트를 만드는 메서드. List에 role집어넣음
//        return Jwts.builder()
//                .setClaims(claims)
//                .setIssuedAt(new Date(System.currentTimeMillis()))
//                .setExpiration(new Date(System.currentTimeMillis() + expireTimeMs))
//                .signWith(SignatureAlgorithm.HS256, secretKey)
//                .compact();
//    }

    public static JwtDto createToken(String username, String secretKey) {
        final Long accessTokenExpired = 1000 * 60 * 30L; //30분
        final Long refreshTokenExpired = 1000 * 60 * 60 * 24L; //1일
        final String[] tokenType = {"accessToken", "refreshToken"};



        Claims accessClaims = Jwts.claims();
        accessClaims.put("username", username);
        accessClaims.put("tokenType", tokenType[0]);

        String accessToken = Jwts.builder()
                .setClaims(accessClaims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + accessTokenExpired))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
        Claims refreshClaims = Jwts.claims();
        refreshClaims.put("username", username);
        refreshClaims.put("tokenType", tokenType[1]);

        String refreshToken = Jwts.builder()
                .setClaims(refreshClaims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + refreshTokenExpired))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();

        return JwtDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public static String reIssueAccessToken(String username, String secretKey) {
        final Long accessTokenExpired = 1000 * 60 * 30L; //30분
        final String tokenType = "accessToken";
        Claims claims = Jwts.claims();
        claims.put("username", username);
        claims.put("tokenType", tokenType);
        String accessToken = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + accessTokenExpired))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();

        return accessToken;



    }

    public static boolean isExpired(String token, String secretKey) {
        return Jwts.parserBuilder().setSigningKey(secretKey).build() //JwtParser객체를 빌드
                .parseClaimsJws(token) //토큰을 파싱하고, 해당 토큰의 서명을 검증. Jws객체를 반환
                .getBody() //Jws객체에서 본문(Claims)를 추출
                .getExpiration() //추출된 본문(CLaims)에서 만료 날짜를 얻음
                .before(new Date()); //만료 날짜가 현재 시간 보다 이전인지. 이전이면 true반환. 즉 만료 되었다는 것.
    }


    public static String getUsername(String token, String secretKey) {
        return Jwts.parserBuilder().setSigningKey(secretKey).build()
                .parseClaimsJws(token)
                .getBody()
                .get("username", String.class);
    }

    public static String getTokenType(String token, String secretKey) {
        return Jwts.parserBuilder().setSigningKey(secretKey).build()
                .parseClaimsJws(token)
                .getBody()
                .get("tokenType", String.class);
    }

    public static List<String> getRole(String token, String secretKey) {
        Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
        return (List<String>) claims.get("role");
    }

    public Authentication getAuthentication(String token) {
        String username = getUsername(token, secretKey);
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
        System.out.println("userDetails.getClass() = " + userDetails.getClass());
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}