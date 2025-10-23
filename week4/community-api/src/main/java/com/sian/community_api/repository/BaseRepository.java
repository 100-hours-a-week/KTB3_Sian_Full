package com.sian.community_api.repository;

import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public abstract class BaseRepository<T> {

    protected final Map<Long, T> store = new LinkedHashMap<>();
    protected AtomicLong sequence = new AtomicLong();

    public T save(T model) {
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
}
