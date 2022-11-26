package com.ex.commercetestbackjpa.domain.entity.order;

import com.ex.commercetestbackjpa.domain.base.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name="torderdt")
@Getter
@NoArgsConstructor
public class OrderDt extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long ordNo; // 주문번호

    @Column(nullable = false, length = 3)
    private String ordGSeq; // 상품순번

    @Column(nullable = false, length = 3)
    private String ordDSeq; // 사은품순번

    @Column(nullable = false, length = 3)
    private String ordWSeq; // 처리순번

    @Column(nullable = false, length = 10)
    private long custNo; // 고객번호 (YYYYMM0000)

    @Column(nullable = false, length = 3)
    private long receiverSeq; // 배달지순번

    private LocalDateTime ordDtm; // 주문일시

    private String ordGbCd; // 주문구분 코드

    private String procCd; // 진행단계 코드

    private long productNo; // 상품코드

    private long productDtNo; // 단품코드

    private double slPrice; // 판매가

    @Builder
    public OrderDt(long ordNo, String ordGSeq, String ordDSeq, String ordWSeq, long custNo, long receiverSeq, LocalDateTime ordDtm, String ordGbCd, String procCd, long productNo, long productDtNo, double slPrice) {
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

    //    private long rcptQty; // 주취반교 접수수량
//    private long sysCnclQty; // 취소수량
//    private long sysRemQty; // 잔여수량
//    private double dcRate; // 할인율
//    private double dcAmt; // 할인금액
//    private double sysRemDcAmt; // 잔여 할인금액
//    private double sysRemAmt; // 잔여 금액
//    private String delivCostCd; // 배송비구분코드
//    private String delivMethCd; // 배송방법코드
//    private LocalDate delivDt; // 배송예정일자
//    private LocalDate shipDt; // 출하예정일자


//    private OrderM(String userId, String password, String name) {
//        this.email = userId;
//        this.password = password;
//        this.name = name;
//    }
//
//    public static OrderM SaveCustomer(String email, String password, String name){
//        return new OrderM(email, password, name);
//    }
//
//    @Override
//    public boolean equals(Object obj) {
//        if (obj == null) { // null인지
//            return false;
//        }
//
//        if (obj == this) { // this인지
//            return true;
//        }
//
//        if(getClass() != obj.getClass()){ // 참조 클래스가 같은지
//            return false;
//        }
//
//        OrderM board = (OrderM)obj; // 같은 클래스인 경우 형변환
//        return (this.getCustNo() == board.getCustNo()); // this와 obj의 pk값 비교
//    }
//
//    @Override
//    public int hashCode() {
//        final int PRIME = 31;
//        int result = 1;
//        result = (int) (PRIME * result + getCustNo());
//        return result;
//    }
}