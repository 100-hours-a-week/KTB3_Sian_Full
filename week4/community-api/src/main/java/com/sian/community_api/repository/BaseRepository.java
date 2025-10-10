package com.sian.community_api.repository;

import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public abstract class BaseRepository<T> {

    protected final Map<Long, T> store = new LinkedHashMap<>();
    protected long sequence = 0;

    public T save(T model) {
        if (getId(model) == null) {
            setId(model, ++sequence);
        }
        store.put(getId(model), model);
        return model;
    }

    // id로 조회
    public Optional<T> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    // 전체 조회
    public List<T> findAll() {
        return new ArrayList<>(store.values());
    }

    // 초기화
    public void clear() {
        store.clear();
        sequence = 0;
    }

    // 각 레포지토리에서 정의
    protected abstract Long getId(T model);
    protected abstract void setId(T model, Long id);
}
