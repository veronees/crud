package hello.mycrud.crud.util;

import hello.mycrud.crud.domain.entity.Post;
import hello.mycrud.crud.domain.entity.User;
import hello.mycrud.crud.domain.requestdto.PostRequestDto;
import hello.mycrud.crud.domain.responsedto.PostResponseDto;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class PostEntityDtoConverter {

    public PostResponseDto convertToDto(Post entity) {
        PostResponseDto dto = PostResponseDto.builder()
                .userId(entity.getId())
                .title(entity.getTitle())
                .content(entity.getContent())
                .viewCount(entity.getViewCount())
                .build();

        return dto;
    }

    public Post convertToEntity(PostRequestDto dto, User user) {
        Post entity = Post.builder()
                .user(user)
                .title(dto.getTitle())
                .content(dto.getContent())
                .createdDate(LocalDateTime.now())
                .lastModifiedDate(LocalDateTime.now())
                .build();
        return entity;
    }
}
