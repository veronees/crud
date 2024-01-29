package hello.mycrud.crud.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "USER_ID")
    private Long id;

    private String username;

    private String password;

    private String nickname;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(value = EnumType.STRING)
    private Gender gender; //[MAN, WOMAN]

    @OneToMany(mappedBy = "user")
    private List<Post> posts = new ArrayList<>();

    public void changeInfo(String name, String nickname, Gender gender) {
        this.username = name;
        this.nickname = nickname;
        this.gender = gender;
    }
}