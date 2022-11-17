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
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

@RequiredArgsConstructor
public class ProductRepositoryCustomImpl implements ProductRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Product> findByFilters(Map<String, String> filterMap, Pageable pageable) {

        List<Product> productList = queryFactory
                .selectFrom(product)
                .where(
                        condition(Optional.ofNullable(filterMap.get("productNo")).map(Long::valueOf).orElse(null), product.productNo::eq),
                        condition(filterMap.get("productName"), product.productName::contains),
                        condition(filterMap.get("keyword"), product.keyword::contains),
                        condition(filterMap.get("lgroup"), product.lgroup::eq),
                        condition(filterMap.get("mgroup"), product.mgroup::eq),
                        condition(filterMap.get("sgroup"), product.sgroup::eq),
                        condition(filterMap.get("saleFlag"), product.saleFlag::eq),
                        condition(filterMap.get("signFlag"), product.signFlag::eq)
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
