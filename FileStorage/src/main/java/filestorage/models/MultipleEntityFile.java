package filestorage.models;

import filestorage.enums.Container;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class MultipleEntityFile extends Item {
    @Getter
    @Setter
    private List<FileItem> fileItems;

    public MultipleEntityFile(String entityUid, Container container, List<FileItem> fileItems) {
        super(entityUid, container, true);
        this.fileItems = fileItems;
    }
}
