package hello.mycrud.crud.controller;

import hello.mycrud.crud.domain.requestdto.CommentRequestDto;
import hello.mycrud.crud.domain.responsedto.CommentResponseDto;
import hello.mycrud.crud.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post/{postId}/comment")
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<String> createCommentV2(@PathVariable Long postId, @RequestBody CommentRequestDto commentRequestDto) {
        commentService.createCommentV2(postId, null, commentRequestDto);
        return new ResponseEntity<>("댓글 작성 완료", HttpStatus.OK);
    }

    @PostMapping("/{parentCommentId}")
    public ResponseEntity<String> createReplyCommentV2(@PathVariable Long postId, @PathVariable Long parentCommentId, @RequestBody CommentRequestDto commentRequestDto) {
        commentService.createCommentV2(postId, parentCommentId, commentRequestDto);
        return new ResponseEntity<>("대댓글 작성 완료", HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<CommentResponseDto>> commentListByPostIdV2(@PathVariable Long postId) {
        List<CommentResponseDto> commentResponseDtoList = commentService.findAllByPostIdV2(postId);
        return ResponseEntity.ok(commentResponseDtoList);
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<String> updateCommentV2(@PathVariable Long postId, @PathVariable Long commentId , @RequestBody CommentRequestDto commentRequestDto) {
        commentService.modifiedCommentV2(postId, commentId, commentRequestDto);
        return new ResponseEntity<>("댓글 수정 완료", HttpStatus.OK);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<String> deleteCommentV2(@PathVariable Long postId, @PathVariable Long commentId) {
        commentService.deleteCommentV2(postId, commentId);
        return new ResponseEntity<>("댓글 삭제 완료", HttpStatus.OK);
    }
}


/**
 * 순수 Jpa 로직
 */
//    @PostMapping
//    public ResponseEntity<String> createComment(@PathVariable Long postId, @RequestBody CommentRequestDto commentRequestDto) {
//        commentService.createComment(postId, null, commentRequestDto);
//        return new ResponseEntity<>("댓글 작성 완료", HttpStatus.OK);
//    }
//
//    @PostMapping("/{parentCommentId}")
//    public ResponseEntity<String> createReplyComment(@PathVariable Long postId, @PathVariable Long parentCommentId, @RequestBody CommentRequestDto commentRequestDto) {
//        commentService.createComment(postId, parentCommentId, commentRequestDto);
//        return new ResponseEntity<>("대댓글 작성 완료", HttpStatus.OK);
//    }
//
//    @GetMapping
//    public ResponseEntity<List<CommentResponseDto>> commentListByPostId(@PathVariable Long postId) {
//        List<CommentResponseDto> commentResponseDtoList = commentService.findAllByPostId(postId);
//        return ResponseEntity.ok(commentResponseDtoList);
//    }
//
//    @PutMapping("/{commentId}")
//    public ResponseEntity<String> updateComment(@PathVariable Long postId, @PathVariable Long commentId , @RequestBody CommentRequestDto commentRequestDto) {
//        commentService.modifiedComment(postId, commentId, commentRequestDto);
//        return new ResponseEntity<>("댓글 수정 완료", HttpStatus.OK);
//    }
//
//    @DeleteMapping("/{commentId}")
//    public ResponseEntity<String> deleteComment(@PathVariable Long postId, @PathVariable Long commentId) {
//        commentService.deleteComment(postId, commentId);
//        return new ResponseEntity<>("댓글 삭제 완료", HttpStatus.OK);
//    }
