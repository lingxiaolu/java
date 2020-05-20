package com.zwq.mapper;

public final class Mapper {

    protected abstract static class MapElement<T> {

        public final String name;
        public final T object;

        public MapElement(String name, T object) {
            this.name = name;
            this.object = object;
        }
    }

    protected static final class MappedContext extends MapElement {

        public MappedWrapper defaultWrapper = null;

        public MappedContext(String name, MappedWrapper defaultWrapper) {
            super(name, null);
            this.defaultWrapper = defaultWrapper;
        }
    }

    protected static final class MappedWrapper extends MapElement {


        public MappedWrapper(String name, Object object) {
            super(name, object);
        }
    }
}
