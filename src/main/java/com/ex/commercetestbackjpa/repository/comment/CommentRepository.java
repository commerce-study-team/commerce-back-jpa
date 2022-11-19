package com.ex.commercetestbackjpa.repository.comment;

import com.ex.commercetestbackjpa.domain.entity.comment.Comment;
import com.ex.commercetestbackjpa.domain.entity.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommentRepository extends JpaRepository <Comment, Long> {
    @Query(value = "select count(*) from Comment c where c.product = :product")
    public Long countByProduct(@Param(value = "product") Product product);
}
