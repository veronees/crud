package hello.mycrud.crud.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.List;

public class JwtUtil {

    public static String createToken(String username, String role, String secretKey, Long expireTimeMs) {
        Claims claims = Jwts.claims();
        claims.put("username", username);
        claims.put("authorities", List.of(role));
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expireTimeMs))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
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

    public static List<String> getAuthorities(String token, String secretKey) {
        Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
        return (List<String>) claims.get("authorities");
    }
}
