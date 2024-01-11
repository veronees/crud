package hello.mycrud.crud.util;

import hello.mycrud.crud.domain.entity.User;
import hello.mycrud.crud.domain.requestdto.UserRequestDto;
import hello.mycrud.crud.domain.responsedto.UserResponseDto;
import org.springframework.stereotype.Component;

@Component
public class UserEntityDtoConverter {

    public User convertToEntity(UserRequestDto userRequestDto) {
        User entity = User.builder()
                .name(userRequestDto.getName())
                .nickname(userRequestDto.getNickname())
                .gender(userRequestDto.getGender())
                .build();

        return entity;
    }

    public UserResponseDto convertToDto(User user) {
        UserResponseDto dto = UserResponseDto.builder()
                .name(user.getName())
                .nickname(user.getNickname())
                .gender(user.getGender())
                .build();

        return dto;
    }
}
