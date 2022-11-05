package com.ex.commercetestbackjpa.domain.entity.product;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name="tproductPrice")
@Getter
@NoArgsConstructor
@DynamicInsert
public class ProductPrice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ProductPriceNo;

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
    private ProductPrice(LocalDateTime applyDate, Long salePrice, Long costPrice, Long margin, Product product) {

        this.applyDate = applyDate;
        this.salePrice = salePrice;
        this.costPrice = costPrice;
        this.margin = margin;
        this.product = product;
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
