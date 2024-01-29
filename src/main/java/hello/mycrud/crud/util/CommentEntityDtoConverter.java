package hello.mycrud.crud.util;

import hello.mycrud.crud.domain.entity.Comment;
import hello.mycrud.crud.domain.entity.Post;
import hello.mycrud.crud.domain.entity.User;
import hello.mycrud.crud.domain.requestdto.CommentRequestDto;
import hello.mycrud.crud.domain.responsedto.CommentResponseDto;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class CommentEntityDtoConverter {

    public Comment convertToEntity(Post post, User user, Comment comment, CommentRequestDto commentRequestDto) {
        Comment entity = Comment.builder()
                .post(post)
                .user(user)
                .parentComment(comment)
                .content(commentRequestDto.getContent())
//                .createdDate(LocalDateTime.now())
//                .lastModifiedDate(LocalDateTime.now())
                .build();

        return entity;
    }

    public CommentResponseDto convertToDto(Comment comment) {
        CommentResponseDto dto = CommentResponseDto.builder()
                .userId(comment.getUser().getId())
                .content(comment.getContent())
                .createdDate(comment.getCreatedDate())
                .lastModifiedDate(comment.getLastModifiedDate())
                .build();

        return dto;
    }
}
