package hello.mycrud.security.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RefreshToken {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //누구의 토큰인가
    private String username;

    //토큰 값
    private String refreshToken;

    //토큰 만료 (true:만료, false:유효)
    private boolean expired;

    //토큰 폐지 (true:폐지, false: 유효)
    private boolean revoked;
}
