package com.ex.commercetestbackjpa.repository.product;

import com.ex.commercetestbackjpa.domain.dto.product.ProductDTO;
import com.ex.commercetestbackjpa.domain.entity.product.Product;
import static com.ex.commercetestbackjpa.domain.entity.product.QProduct.product;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@RequiredArgsConstructor
public class ProductRepositoryCustomImpl implements ProductRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Product> findByFilters(ProductDTO.Request productRequstDto, Pageable pageable) {

        List<Product> productList = queryFactory
                .selectFrom(product)
                .where(
                        condition(productRequstDto.getKeyword(), product.keyword::contains),
                        condition(productRequstDto.getProductName(), product.productName::contains),
                        condition(productRequstDto.getLgroup(), product.lgroup::eq),
                        condition(productRequstDto.getMgroup(), product.mgroup::eq),
                        condition(productRequstDto.getSgroup(), product.sgroup::eq),
                        condition(productRequstDto.getSaleFlag(), product.saleFlag::eq),
                        condition(productRequstDto.getSignFlag(), product.signFlag::eq)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return new PageImpl<>(productList, pageable, productList.size());
    }

    private <T> BooleanExpression condition(T value, Function<T, BooleanExpression> function) {
        return Optional.ofNullable(value).map(function).orElse(null);
    }
}
