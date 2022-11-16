package com.ex.commercetestbackjpa.domain.entity.product;

import com.ex.commercetestbackjpa.domain.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name="tproductPrice")
@Getter
@NoArgsConstructor
@DynamicInsert
public class ProductPrice extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productPriceNo;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime applyDate;

    @Column(nullable = false, length = 100)
    private Long salePrice;

    @Column(nullable = false, length = 100)
    private Long costPrice;

    @Column(nullable = false, length = 100)
    private Long margin;

    @ColumnDefault("1")
    private Boolean useYn;

    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

    @Builder
    private ProductPrice(LocalDateTime applyDate, Long salePrice, Long costPrice, Product product) {

        this.applyDate = applyDate;
        this.salePrice = salePrice;
        this.costPrice = costPrice;
        this.margin = salePrice - costPrice;
        this.product = product;
    }

    public void settingProduct(Product product) {
        this.product = product;
        this.product.addApplyProductPrice(this);
    }

    public void updateSalePrice(Long salePrice) {
        this.salePrice = salePrice;
        this.margin = salePrice - this.costPrice;
    }

    public void updateCostPrice(Long costPrice) {
        this.costPrice = costPrice;
    }

    public void updateUseYn(Boolean useYn) {
        this.useYn = useYn;
    }
}
