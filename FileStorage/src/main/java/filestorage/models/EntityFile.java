package filestorage.models;

import filestorage.enums.Container;
import lombok.*;

import java.io.File;

public class EntityFile extends Item {
    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private File file;

    public EntityFile(String entityUid, Container container, String name, File file) {
        super(entityUid, container, false);
        this.name = name;
        this.file = file;
    }

    public static EntityFileBuilder builder() {
        return new EntityFileBuilder();
    }

    public static class EntityFileBuilder {
        private String entityUid;
        private Container container;
        private File file;
        private String name;

        private EntityFileBuilder() {
        }

        public EntityFileBuilder entityUid(String entityUid) {
            this.entityUid = entityUid;
            return this;
        }

        public EntityFileBuilder container(Container container) {
            this.container = container;
            return this;
        }

        public EntityFileBuilder file(File file) {
            this.file = file;
            return this;
        }

        public EntityFileBuilder name(String name) {
            this.name = name;
            return this;
        }

        @Override
        public String toString() {
            return "EntityFileBuilder(entityUid = " + entityUid + ", container = " + container.toString() + ", file = " + (file != null ? "true" : "false") + ")";
        }

        public EntityFile build() {
            return new EntityFile(entityUid, container, name, file);
        }
    }
}
