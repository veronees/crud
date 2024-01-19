package hello.mycrud.crud.util;

import hello.mycrud.crud.domain.entity.Role;
import hello.mycrud.crud.domain.entity.User;
import hello.mycrud.crud.domain.requestdto.UserRequestDto;
import hello.mycrud.crud.domain.responsedto.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserEntityDtoConverter {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public User convertToEntity(UserRequestDto userRequestDto) {

        User entity = User.builder()
                .username(userRequestDto.getUsername())
                .password(bCryptPasswordEncoder.encode(userRequestDto.getPassword()))
                .nickname(userRequestDto.getNickname())
                .role(userRequestDto.getRole())
                .gender(userRequestDto.getGender())
                .build();

        return entity;
    }

    public UserResponseDto convertToDto(User user) {
        UserResponseDto dto = UserResponseDto.builder()
                .username(user.getUsername())
                .nickname(user.getNickname())
                .role(user.getRole())
                .gender(user.getGender())
                .build();

        return dto;
    }
}
