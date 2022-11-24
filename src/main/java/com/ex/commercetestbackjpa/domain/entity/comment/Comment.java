package com.ex.commercetestbackjpa.domain.entity.comment;

import com.ex.commercetestbackjpa.domain.base.BaseEntity;
import com.ex.commercetestbackjpa.domain.entity.product.Product;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tcomment")
@Getter
@NoArgsConstructor
@DynamicInsert
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commnetNo;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(nullable = false, length = 1000)
    private String content;

    @Column(nullable = false, length = 3)
    @ColumnDefault("5")
    private Integer grade;

    @Column(nullable = false, length = 1)
    @ColumnDefault("0")
    private Boolean deleteYn;

    private Long custNo; // CustomerEntity 대체 예정

    private Long orderNo; // OrderEntity 대체 예정

    @Column(nullable = false)
    @ColumnDefault("0")
    private Long likes;

    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

    @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CommentImage> commentImageList = new ArrayList<>();

    @Builder
    private Comment(String title, String content, Integer grade, Long likes, Boolean deleteYn) {
        this.title = title;
        this.content = content;
        this.grade = grade;
        this.deleteYn = deleteYn;
        this.likes = likes;
    }

    public void settingProduct(Product product) {
        this.product = product;
        product.getProductCommentList().add(this);
    }

    // 추후 엔티티로 교체 예정
    public void settingCustomer(Long custNo) {
        this.custNo = custNo;
    }

    // 추후 엔티티로 교체 예정
    public void settingOrder(Long orderNo) {
        this.orderNo = orderNo;
    }

    public void addCommentImage(CommentImage commentImage) {
        this.getCommentImageList().add(commentImage);
        commentImage.addComment(this);
    }
}
