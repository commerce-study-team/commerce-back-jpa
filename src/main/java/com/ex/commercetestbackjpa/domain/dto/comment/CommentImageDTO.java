package com.ex.commercetestbackjpa.domain.dto.comment;

import com.ex.commercetestbackjpa.domain.entity.product.CommentImage;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

public class CommentImageDTO {

    @Getter
    @Setter
    public static class Request {

        private Long commentImageNo;

        @ApiModelProperty(value = "리뷰 이미지명")
        private String imageName;

        @ApiModelProperty(value = "실제 리뷰 이미지명")
        private String imageRealName;

        private MultipartFile imgFile;

        public CommentImage toEntity() {
            return CommentImage.builder()
                    .imageRealName(this.imageRealName)
                    .build();
        }
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Data
    public static class Response {

        private Long commentImageNo;

        private String imageName;

        private String imageRealName;

        public Response(CommentImage commentImage) {
            this.commentImageNo = commentImage.getCommentImageNo();
            this.imageName = commentImage.getImageName();
            this.imageRealName = commentImage.getImageRealName();
        }
    }
}
