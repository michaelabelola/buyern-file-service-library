package filestorage.models;

import filestorage.enums.Container;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Item {
    private String entityUid;
    private Container container;
    private boolean isMultipleFiles = false;
}
