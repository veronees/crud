package hello.mycrud.crud.domain.responsedto;

import hello.mycrud.crud.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
@AllArgsConstructor
public class PostResponseDto {
    private Long userId;
    private String title;
    private String content;
    private int viewCount;
    private int likeCount;
    private int commentCount;

}
