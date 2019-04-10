package carddeck.dao;

import java.util.Collection;
import java.util.Optional;

public interface GenericDAO<T extends WithId> extends Factory<T> {
    Optional<T> fetch(String id);
    T save(T t);
    Collection<T> list();
    Optional<T> delete(String id);
}
