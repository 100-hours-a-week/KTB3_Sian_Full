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

    public Optional<T> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    public List<T> findAll() {
        return new ArrayList<>(store.values());
    }

    public void delete(Long id) {
        store.remove(id);
    }

    protected abstract Long getId(T model);
    protected abstract void setId(T model, Long id);
}
