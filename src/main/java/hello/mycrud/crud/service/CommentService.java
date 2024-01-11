package hello.mycrud.crud.service;

import hello.mycrud.crud.domain.entity.Comment;
import hello.mycrud.crud.domain.entity.Post;
import hello.mycrud.crud.domain.entity.User;
import hello.mycrud.crud.domain.requestdto.CommentRequestDto;
import hello.mycrud.crud.domain.responsedto.CommentResponseDto;
import hello.mycrud.crud.repository.CommentRepository;
import hello.mycrud.crud.repository.PostRepository;
import hello.mycrud.crud.repository.UserRepository;
import hello.mycrud.crud.util.CommentEntityDtoConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CommentEntityDtoConverter commentEntityDtoConverter;

    //댓글 등록
    public void createComment(Long postId, Long parentCommentId, CommentRequestDto commentRequestDto) {
        Post post = postRepository.findOne(postId);
        User user = userRepository.findOne(commentRequestDto.getUserId());

        //null이 아닌 경우는 대댓글 컨트롤러인 경우
        if (parentCommentId != null) {
            Comment parentComment = commentRepository.findOne(parentCommentId);
            Comment comment = commentEntityDtoConverter.convertToEntity(post, user, parentComment, commentRequestDto);
            commentRepository.save(comment);
        }
        //null인 경우는 대댓글이 아닌 일반 댓글 작성 컨트롤러인 경우
        else {
            Comment comment = commentEntityDtoConverter.convertToEntity(post, user, null, commentRequestDto);
            commentRepository.save(comment);
        }
    }

    //댓글 수정
    public void modifiedComment(Long postId, Long commentId, CommentRequestDto commentRequestDto) {
        Comment findComment = commentRepository.findByPostIdAndCommentId(postId, commentId);
        String content = commentRequestDto.getContent();
        findComment.modifiedContent(content);
    }

    //댓글 삭제
    public void deleteComment(Long postId, Long commentId) {
        Comment comment = commentRepository.findByPostIdAndCommentId(postId, commentId);
        commentRepository.delete(comment.getId());
    }

    //특정 게시글의 전체 댓글 조회
    public List<CommentResponseDto> findAllByPostId(Long postId) {
        Post post = postRepository.findOne(postId);
        List<Comment> comments = post.getComments();
        List<CommentResponseDto> commentResponseDtos = new ArrayList<>();
        for (Comment comment : comments) {
            CommentResponseDto commentResponseDto = commentEntityDtoConverter.convertToDto(comment);
            commentResponseDtos.add(commentResponseDto);
        }
        return commentResponseDtos;
    }

}
