package hello.mycrud.security.service;

import hello.mycrud.crud.domain.entity.User;
import hello.mycrud.crud.repository.UserRepository;
import hello.mycrud.crud.util.JwtUtil;
import hello.mycrud.security.dto.LoginRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    @Value("${jwt.secret}")
    private String secretKey;
    private Long expireTimeMs = 1000 * 60 * 60L;

    public String loginProcess(LoginRequestDTO dto) {
        //username(ID) 일치하는지
        Optional<User> byUsername = userRepository.findByUsername(dto.getUsername());

        if(byUsername.isEmpty()) {
            return "username(ID)가 틀렸습니다.";
        }

        //비밀번호 맞는지
        User user = byUsername.get();
        if(!bCryptPasswordEncoder.matches(dto.getPassword(), user.getPassword())) {
            return "비밀번호가 일치하지 않습니다.";
        }
        String role = String.valueOf(user.getRole());
        String token = JwtUtil.createToken(user.getUsername(), role, secretKey, expireTimeMs);

        return token;




    }
}
