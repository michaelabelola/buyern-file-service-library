package filestorage.dtos;

import lombok.Data;

import java.io.File;
import java.io.Serializable;

@Data
public class EntityFileUploadDTO implements Serializable {
    private String entityUid;
    //    should be saved in the entities public container or private container
    private boolean isPublic;
    private File file;
    private String name;

    @Data
    public static class EntityRegistrationFilesByteArrayDTO implements Serializable {
        private String entityUid;
        //    should be saved in the entities public container or private container
        private boolean isPublic;
        private byte[] file;
        private String name;
        private String contentType;

    }
}
