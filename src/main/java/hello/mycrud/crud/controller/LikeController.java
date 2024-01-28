package hello.mycrud.crud.controller;

import hello.mycrud.crud.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/like/{postId}/{userId}")
public class LikeController {

    private final LikeService likeService;

    @GetMapping
    public ResponseEntity<String> likePostV2(@PathVariable Long postId, @PathVariable Long userId) {
        likeService.likePostV2(postId, userId);
        return new ResponseEntity<>("좋아요!", HttpStatus.OK);
    }
    @DeleteMapping
    public ResponseEntity<String> unlikePostV2(@PathVariable Long postId, @PathVariable Long userId) {
        likeService.unlikePostV2(postId, userId);
        return new ResponseEntity<>("좋아요 취소", HttpStatus.OK);
    }
}





    /**
     * 순수 Jpa 로직
     */
//    @GetMapping("/like/{postId}/{userId}")
//    public ResponseEntity<String> likePost(@PathVariable Long postId, @PathVariable Long userId) {
//        likeService.likePost(postId, userId);

//        return new ResponseEntity<>("좋아요!", HttpStatus.OK);

//    }

    //    @DeleteMapping("/like/{postId}/{userId}")
//    public ResponseEntity<String> unlikePost(@PathVariable Long postId, @PathVariable Long userId) {
//        likeService.unlikePost(postId, userId);
//        return new ResponseEntity<>("좋아요 취소", HttpStatus.OK);
//    }
