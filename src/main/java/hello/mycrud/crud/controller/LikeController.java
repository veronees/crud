package hello.mycrud.crud.controller;

import hello.mycrud.crud.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    @GetMapping("/like/{postId}/{userId}")
    public ResponseEntity<String> likePost(@PathVariable Long postId, @PathVariable Long userId) {
        likeService.likePost(postId, userId);
        return new ResponseEntity<>("좋아요!", HttpStatus.OK);
    }

    @DeleteMapping("/like/{postId}/{userId}")
    public ResponseEntity<String> unlikePost(@PathVariable Long postId, @PathVariable Long userId) {
        likeService.unlikePost(postId, userId);
        return new ResponseEntity<>("좋아요 취소", HttpStatus.OK);
    }
}
