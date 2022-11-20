package com.ex.commercetestbackjpa.domain.dto.order;

import com.ex.commercetestbackjpa.domain.entity.order.OrderDt;
import lombok.*;

import java.time.LocalDateTime;

public class OrderDtDto {

    @Getter
    @Setter
    public static class Request {
        private long ordNo; // 주문번호
        private String ordGSeq; // 상품순번
        private String ordDSeq; // 사은품번
        private String ordWSeq; // 처리순번
        private long custNo; // 고객번호 (YYYYMM0000)
        private long receiverSeq; // 배달지순번
        private LocalDateTime ordDtm; // 주문일시
        private String ordGbCd; // 주문구분 코드
        private String procCd; // 진행단계 코드
        private long productNo; // 상품코드
        private long productDtNo; // 단품코드
        private double slPrice; // 판매가

        public OrderDt toEntity(OrderDt orderDT) {
            return OrderDt.builder()
                    .ordNo(this.ordNo)
                    .ordGSeq(this.ordGSeq)
                    .ordDSeq(this.ordDSeq)
                    .ordWSeq(this.ordWSeq)
                    .custNo(this.custNo)
                    .receiverSeq(this.receiverSeq)
                    .ordDtm(this.ordDtm)
                    .ordGbCd(this.ordGbCd)
                    .procCd(this.procCd)
                    .productNo(this.productNo)
                    .productDtNo(this.productDtNo)
                    .slPrice(this.slPrice)
                    .build();
        }
    }

    @NoArgsConstructor
    @Builder
    @Data
    public static class Response {
        private long ordNo; // 주문번호
        private String ordGSeq; // 상품순번
        private String ordDSeq; // 사은품번
        private String ordWSeq; // 처리순번
        private long custNo; // 고객번호 (YYYYMM0000)
        private long receiverSeq; // 배달지순번
        private LocalDateTime ordDtm; // 주문일시
        private String ordGbCd; // 주문구분 코드
        private String procCd; // 진행단계 코드
        private long productNo; // 상품코드
        private long productDtNo; // 단품코드
        private double slPrice; // 판매가

        public Response(long ordNo, String ordGSeq, String ordDSeq, String ordWSeq, long custNo, long receiverSeq, LocalDateTime ordDtm, String ordGbCd, String procCd, long productNo, long productDtNo, double slPrice) {
            this.ordNo = ordNo;
            this.ordGSeq = ordGSeq;
            this.ordDSeq = ordDSeq;
            this.ordWSeq = ordWSeq;
            this.custNo = custNo;
            this.receiverSeq = receiverSeq;
            this.ordDtm = ordDtm;
            this.ordGbCd = ordGbCd;
            this.procCd = procCd;
            this.productNo = productNo;
            this.productDtNo = productDtNo;
            this.slPrice = slPrice;
        }

        public Response(OrderDt orderDt) {
            this.ordNo = orderDt.getOrdNo();
            this.ordGSeq = orderDt.getOrdGSeq();
            this.ordDSeq = orderDt.getOrdDSeq();
            this.ordWSeq = orderDt.getOrdWSeq();
            this.custNo = orderDt.getCustNo();
            this.receiverSeq = orderDt.getReceiverSeq();
            this.ordDtm = orderDt.getOrdDtm();
            this.ordGbCd = orderDt.getOrdGbCd();
            this.procCd = orderDt.getProcCd();
            this.productNo = orderDt.getProductNo();
            this.productDtNo = orderDt.getProductDtNo();
            this.slPrice = orderDt.getSlPrice();
        }
    }

}
