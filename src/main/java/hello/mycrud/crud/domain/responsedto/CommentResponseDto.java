package hello.mycrud.crud.domain.responsedto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
@Builder
public class CommentResponseDto {

    private Long userId;
    private String content;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
}
