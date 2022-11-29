package com.ex.commercetestbackjpa.repository.product;


import com.ex.commercetestbackjpa.domain.entity.product.CommentImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentImageRepository extends JpaRepository <CommentImage, Long> {
}
