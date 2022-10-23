package com.ex.commercetestbackjpa.domain.entity.customer;

import com.ex.commercetestbackjpa.domain.base.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Entity(name="tcustomer")
@Getter
@NoArgsConstructor
public class Customer extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="cust_no")
    private Long custNo;
    @Column(nullable = false, length = 20, unique = true)
    private String email;
    @Column(nullable = false, length = 20)
    private String password; // TODO 암호화는 나중에..
    @Column(nullable = false, length = 20)
    private String name;

    @ColumnDefault("1")
    private boolean useYn;

    // CreatedBy, LastModifiedBy 추후에 추가

    // private으로 외부에서 생성자 못쓰게 막음
    private Customer(String userId, String password, String name) {
        this.email = userId;
        this.password = password;
        this.name = name;
    }

    // Customer Entity 직접 생성을 막기위해 static으로 함수를 만들어두고 RequestDto 안에서 이쪽을 호출해 우회 생성되도록 함
    public static Customer SaveCustomer(String email, String password, String name){
        return new Customer(email, password, name);
    }

    // 수정을 허용할 컬럼만 분리해서 함수생성
    public void update(String password, String name) {
        this.password = password;
        this.name = name;
    }

    // 삭제 시 디비삭제 대신 사용여부 업데이트 처리
    public void delete() {
        this.useYn = false;
    }
    
    // 비교이슈
    @Override
    public boolean equals(Object obj) {
        if (obj == null) { // null인지
            return false;
        }

        if (obj == this) { // this인지
            return true;
        }

        if(getClass() != obj.getClass()){ // 참조 클래스가 같은지
            return false;
        }

        Customer board = (Customer)obj; // 같은 클래스인 경우 형변환
        return (this.getCustNo() == board.getCustNo()); // this와 obj의 pk값 비교
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = 1;
        result = (int) (PRIME * result + getCustNo());
        return result;
    }
}