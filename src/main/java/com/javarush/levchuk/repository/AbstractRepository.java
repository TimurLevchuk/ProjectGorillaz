package com.javarush.levchuk.repository;

import com.javarush.levchuk.entity.Identifiable;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public abstract class AbstractRepository<T extends Identifiable> implements Repository<T> {
    protected final Map<Long, T> map = new ConcurrentHashMap<>();
    public final AtomicLong id = new AtomicLong();


    public void create(T entity) {
        entity.setId(id.incrementAndGet());
        map.put(entity.getId(), entity);
    }

    public void update(T entity) {
        map.put(entity.getId(), entity);
    }

    public void delete(T entity) {
        map.remove(entity.getId());
    }

    public Optional<T> get(Long id) {
        id = Objects.requireNonNullElse(id, 0L);
        return Optional.ofNullable(map.get(id));
    }

    public Collection<T> getAll() {
        return map.values();
    }
}