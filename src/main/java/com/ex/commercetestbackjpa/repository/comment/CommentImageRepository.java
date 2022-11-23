package com.ex.commercetestbackjpa.repository.comment;


import com.ex.commercetestbackjpa.domain.entity.comment.CommentImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentImageRepository extends JpaRepository <CommentImage, Long> {
}
