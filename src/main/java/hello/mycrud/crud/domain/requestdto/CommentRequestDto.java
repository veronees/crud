package hello.mycrud.crud.domain.requestdto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CommentRequestDto {

    private Long userId;
    private String content;
}
