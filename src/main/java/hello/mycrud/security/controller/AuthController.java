package hello.mycrud.security.controller;

import hello.mycrud.crud.util.JwtUtil;
import hello.mycrud.security.domain.dto.JwtDto;
import hello.mycrud.security.domain.dto.LoginRequestDTO;
import hello.mycrud.security.service.LoginService;
import hello.mycrud.security.service.ReIssueService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final LoginService loginService;
    private final ReIssueService reIssueService;
    private final JwtUtil jwtUtil;
    @Value("${jwt.secret}")
    private String secretKey;


    @PostMapping("/login")
    public ResponseEntity<JwtDto> login(@RequestBody LoginRequestDTO dto) {
        JwtDto jwtDto = loginService.loginProcess(dto);

        //refreshToken은 DB에 저장
        reIssueService.save(jwtDto.getRefreshToken());

        return new ResponseEntity<>(jwtDto, HttpStatus.OK);
    }

    //refresh토큰으로 accesstoken 재발급
    @PostMapping("/reissue")
    public ResponseEntity<String> reissue(HttpServletRequest request, HttpServletResponse response) {
        String reissuedToken = reIssueService.reIssueAccessToken(request, response);

        return new ResponseEntity<>(reissuedToken, HttpStatus.OK);
    }


//    @PostMapping("/logout")
//    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {
//        reIssueService.logout(request, response);
//        return new ResponseEntity<>("로그아웃 성공!", HttpStatus.OK);
//    }
}
