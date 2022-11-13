package com.ex.commercetestbackjpa.domain.entity.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CodeId implements Serializable {

    private String codeLgroup;

    private String codeMgroup;
}
