package com.ex.commercetestbackjpa.repository.cache;

public interface CacheRepository {

    public void sortSetAdd(String key, String value, Long score);

    public void sortSetFind(String key);
}
