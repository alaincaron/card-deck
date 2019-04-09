package carddeck.dao;

import java.util.Collection;
import java.util.Optional;

public interface GenericDAO<T extends WithId> {
    T create();
    Optional<T> fetch(String id);
    T save(T game);
    Collection<T> list();
    Optional<T> delete(String id);
}
