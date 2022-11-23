package filestorage.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.File;

@Data
@AllArgsConstructor
public class FileItem {
    private String name;
    private File file;
}
