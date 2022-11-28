package com.ex.commercetestbackjpa.repository.cache;

import com.ex.commercetestbackjpa.domain.dto.common.RankDTO;

import java.util.List;

public interface CacheRepository {

    public void sortSetAdd(String key, String value, Long score);

    public List<RankDTO.Response> sortSetFind(String key);
}
