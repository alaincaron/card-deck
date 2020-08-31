package carddeck.impl;

import carddeck.dao.Factory;

import java.util.function.Function;

class AbstractFactory<T> implements Factory<T> {
    private final IdGenerator idGenerator = new IdGenerator();
    private final Function<String, T> creator;

    AbstractFactory(Function<String, T> creator) {
        this.creator = creator;
    }

    @Override
    public T create() {
        final String id = idGenerator.generate();
        return creator.apply(id);
    }
}
