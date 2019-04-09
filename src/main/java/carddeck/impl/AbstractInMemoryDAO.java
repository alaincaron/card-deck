package carddeck.impl;

import carddeck.dao.GenericDAO;
import carddeck.dao.WithId;
import org.springframework.cglib.core.internal.Function;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

abstract class AbstractInMemoryDAO<T extends WithId> implements GenericDAO<T> {
    private final Map<String, T> allInstances = new ConcurrentHashMap<>();
    private final IdGenerator idGenerator = new IdGenerator();
    private final Function<String, T> creator;

    protected AbstractInMemoryDAO(Function<String, T> creator) {
        this.creator = creator;
    }
    @Override
    public T create() {
        final String id = idGenerator.generate();
        final T instance = creator.apply(id);
        allInstances.put(instance.getId(), instance);
        return instance;
    }

    @Override
    public Optional<T> fetch(String id) {
        return Optional.ofNullable(allInstances.get(id));
    }

    @Override
    public T save(T instance) {
        if (instance == null) {
            throw new IllegalArgumentException("Invalid null instance");
        }
        return instance;
    }

    @Override
    public Collection<T> list() {
        return allInstances.values();

    }

    @Override
    public Optional<T> delete(String id) {
        return Optional.ofNullable(allInstances.remove(id));
    }

}
