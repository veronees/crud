package hello.mycrud.crud.controller;

import hello.mycrud.crud.domain.requestdto.UserRequestDto;
import hello.mycrud.crud.domain.responsedto.UserResponseDto;
import hello.mycrud.crud.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    //회원가입
    @PostMapping("/join")
    public ResponseEntity<String> joinUser(@RequestBody UserRequestDto userRequestDto) {
        boolean joinResult = userService.join(userRequestDto);

        if(joinResult) {
            return new ResponseEntity<>("회원 가입 완료!", HttpStatus.OK);
        }
        return new ResponseEntity<>("사용할 수 없는 아이디입니다.", HttpStatus.OK);
    }

    //회원정보보기
    @GetMapping("/user/{userId}")
    public ResponseEntity<UserResponseDto> findUser(@PathVariable Long userId) {
        UserResponseDto findUser = userService.findOneUser(userId);
        return ResponseEntity.ok(findUser);
    }

    //전체 회원 리스트 보기 (ADMIN만 접근 가능)
    @GetMapping("/users")
    public ResponseEntity<List<UserResponseDto>> findAll(Authentication authentication) {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        for (GrantedAuthority authority : authorities) {
            String role = authority.getAuthority();
            System.out.println("role = " + role);
        }

        List<UserResponseDto> findAllUser = userService.findAllUsers();
        return ResponseEntity.ok(findAllUser);
    }

    //회원정보 수정
    @PutMapping("/user/{userId}")
    public ResponseEntity<UserResponseDto> updateUserInfo(@PathVariable Long userId, @RequestBody UserRequestDto userRequestDto) {
        UserResponseDto modifiedResult = userService.modified(userId, userRequestDto.getUsername(), userRequestDto.getNickname(), userRequestDto.getGender());
        return ResponseEntity.ok(modifiedResult);
    }

    //회원 탈퇴
    @DeleteMapping("/user/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return new ResponseEntity<>("삭제 완료", HttpStatus.OK);
    }

}
