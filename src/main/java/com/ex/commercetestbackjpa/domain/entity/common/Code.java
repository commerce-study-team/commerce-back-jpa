package com.ex.commercetestbackjpa.domain.entity.common;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

@Entity
@Table(name="tcode")
@Getter
@NoArgsConstructor
@DynamicInsert
@IdClass(CodeId.class)
public class Code {

    @Id
    @Column(nullable = false, length = 4)
    private String codeLgroup;

    @Id
    @Column(nullable = false, length = 100)
    private String codeMgroup;

    @Column(length = 100)
    private String codeSgroup;

    @Column(length = 100)
    private String codeName;

    @Column(length = 100)
    private String remark;

    @Builder
    private Code(String codeLgroup, String codeMgroup, String codeSgroup, String codeName, String remark) {
        this.codeLgroup = codeLgroup;
        this.codeMgroup = codeMgroup;
        this.codeSgroup = codeSgroup;
        this.codeName = codeName;
        this.remark = remark;
    }
}
