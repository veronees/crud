package hello.mycrud.crud.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Post extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "POST_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    @Column(length = 30)
    private String title;

    private String content;

    private int viewCount;

    private int likeCount;

    @OneToMany(mappedBy = "post")
    private List<Like> likes = new ArrayList<>();

    @OneToMany(mappedBy = "post")
    private List<Comment> comments = new ArrayList<>();


    //----------메서드----------//

//    public int countLike() {
//        int count = this.likes.size();
//        return count;
//    }

    public int countComments() {
        System.out.println("================");
        int count = this.comments.size();
        System.out.println("================");
        return count;
    }


    public void updateTitle(String newTitle) {
        this.title = newTitle;
    }

    public void updateContent(String newContent) {
        this.content = newContent;
    }

//    public void updateLastModifiedDate() {
//        this.lastModifiedDate = LocalDateTime.now();
//    }

    public void updateViewCount() {
        this.viewCount += 1;
    }

    public void updateLikeCount() {
        this.likeCount += 1;
    }

    public void downLikeCount() {
        this.likeCount -= 1;
    }
}
