package hello.mycrud.security.controller;

import hello.mycrud.security.dto.LoginRequestDTO;
import hello.mycrud.security.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestDTO dto) {
        String result = loginService.loginProcess(dto);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/login/test")
    public ResponseEntity<String> loginTest(Authentication authentication) {
        String name = authentication.getName();
        return new ResponseEntity<>(name + "님이 로그인했습니다.", HttpStatus.OK);
    }
}
