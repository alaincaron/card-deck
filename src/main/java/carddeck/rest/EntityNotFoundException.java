package carddeck.rest;

import java.util.function.Supplier;

public class EntityNotFoundException extends RuntimeException {
    private final String entity;
    private final String id;

    public EntityNotFoundException(String entity, String id) {
        super(String.format("Unable to find entity %s with id %s", entity, id));
        this.entity = entity;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getEntity() {
        return entity;
    }


}
