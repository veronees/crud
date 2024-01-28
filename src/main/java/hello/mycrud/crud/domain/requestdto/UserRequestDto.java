package hello.mycrud.crud.domain.requestdto;

import hello.mycrud.crud.domain.entity.Gender;
import hello.mycrud.crud.domain.entity.Role;
import hello.mycrud.crud.domain.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
public class UserRequestDto {

    private String username;
    private String password;
    private String nickname;
    private Role role;
    private Gender gender;


}
