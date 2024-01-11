package hello.mycrud.crud.domain.requestdto;

import hello.mycrud.crud.domain.entity.Post;
import hello.mycrud.crud.domain.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
public class PostRequestDto {

    private Long userId;
    private String title;
    private String content;
    private int viewCount;

}
