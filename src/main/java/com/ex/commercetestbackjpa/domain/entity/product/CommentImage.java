package com.ex.commercetestbackjpa.domain.entity.product;

import com.ex.commercetestbackjpa.domain.base.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

@Entity
@Table(name = "tcommentImage")
@Getter
@NoArgsConstructor
@DynamicInsert
public class CommentImage extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentImageNo;

    @Column(nullable = false, length = 100)
    private String imageName;

    @Column(nullable = false, length = 100)
    private String imageRealName;

    @ManyToOne(fetch = FetchType.LAZY)
    private Comment comment;

    @Builder
    public CommentImage(String imageName, String imageRealName) {
        this.imageName = imageName;
        this.imageRealName = imageRealName;
    }

    public void settingImageName(String imageName) {
        this.imageName = imageName;
    }

    public void addComment(Comment comment) {
        this.comment = comment;
    }
}
