package carddeck.rest;

import java.util.function.Supplier;

public class EntityNotFoundExceptionSuppliers {
    ;

    public static Supplier<EntityNotFoundException> game(String id) {
        return () -> new EntityNotFoundException("Game", id);
    }

    public static Supplier<EntityNotFoundException> player(String id) {
        return () -> new EntityNotFoundException("Player", id);
    }

}
