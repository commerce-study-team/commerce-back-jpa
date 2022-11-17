package com.ex.commercetestbackjpa.repository.common;

import com.ex.commercetestbackjpa.domain.entity.common.Code;
import com.ex.commercetestbackjpa.domain.entity.common.CodeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CodeRepository extends JpaRepository<Code, CodeId> {

}
