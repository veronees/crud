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
public class User {

    @Id @GeneratedValue
    @Column(name = "USER_ID")
    private Long id;

    private String name;

    private String nickname;

    @Enumerated(value = EnumType.STRING)
    private Gender gender; //[MAN, WOMAN]

    @OneToMany(mappedBy = "user")
    private List<Post> posts = new ArrayList<>();

    public void changeInfo(String name, String nickname, Gender gender) {
        this.name = name;
        this.nickname = nickname;
        this.gender = gender;
    }
}
