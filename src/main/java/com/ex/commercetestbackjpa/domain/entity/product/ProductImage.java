package com.ex.commercetestbackjpa.domain.entity.product;

import com.ex.commercetestbackjpa.domain.base.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

@Entity
@Table(name="tproductImage")
@Getter
@NoArgsConstructor
@DynamicInsert
public class ProductImage extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productImageNo;

    @Column(nullable = false, length = 100)
    private String imageName;

    @Column(nullable = false, length = 100)
    private String imageRealName;

    @ColumnDefault("1")
    private Boolean useYn;

    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

    @Builder
    private ProductImage(String imageName, String imageRealName, Product product) {

        this.imageName = imageName;
        this.imageRealName = imageRealName;
        this.product = product;
    }

    public void settingProduct(Product product) {
        this.product = product;
        this.product.getProductImageList().add(this);
    }

    public void settingImageName(String imageName) {
        this.imageName = imageName;
    }

    public void updateImageName(String imageName, String imageRealName) {
        this.imageName = imageName;
        this.imageRealName = imageRealName;
    }

    public void updateUseYn(Boolean useYn) {
        this.useYn = useYn;
    }
}
