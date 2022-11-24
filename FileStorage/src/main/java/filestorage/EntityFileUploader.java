package filestorage;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import filestorage.exceptions.FileUploadException;
import filestorage.models.EntityFile;
import filestorage.models.FileItem;
import filestorage.models.Item;
import filestorage.models.MultipleEntityFile;
import lombok.Data;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

@Data
public class EntityFileUploader {
    private final Item item;
    private static Logger logger = Logger.getLogger("EntityFileUploader");

    public EntityFileUploader(MultipleEntityFile items) {
        this.item = items;
    }

    public EntityFileUploader(EntityFile item) {
        this.item = item;
    }

    /**
     * response format:
     * <b>
     * <code>
     * {"code":"00","message":"SUCCESSFUL","data":"http://194.35.120.40:10000/devstoreaccount1/euid/logo"}
     * </code>
     * </b>
     */
    public Map<String, String> upload() throws UnirestException, FileUploadException {
        if (getItem().isMultipleFiles())
            return uploadAll();
        else
            return uploadOne(null);
    }

    private Map<String, String> uploadOne(@Nullable EntityFile file) throws UnirestException, FileUploadException {
        if (file == null)
            file = (EntityFile) getItem();

        Map<String, String> headers = new HashMap<>();
        headers.put("accept", "application/json");

        Map<String, Object> fields = new HashMap<>();
        fields.put("entityUid", item.getEntityUid());
        fields.put("container", item.getContainer());
        fields.put("file", file.getFile());
        fields.put("name", file.getName());

        HttpResponse<JsonNode> jsonResponse = Unirest.post("http://localhost:9081/api/v1/entity/uploadFile")
                .headers(headers).fields(fields).asJson();
        if (jsonResponse.getStatus() == 200 && Objects.equals(jsonResponse.getBody().getObject().get("code").toString(), "00")) {
            Map<String, String> response = new HashMap<>();
            response.put(file.getName(), jsonResponse.getBody().getObject().get("data").toString());
            return response;
        } else if (Objects.equals(jsonResponse.getBody().getObject().get("code").toString(), "05")) {
            throw new FileUploadException("Entity Storage account not registered");
        } else
            throw new FileUploadException("Error Uploading file: " + jsonResponse.getBody().getObject().get("message").toString());
    }

    private Map<String, String> uploadAll() throws UnirestException {
        MultipleEntityFile entityFiles = (MultipleEntityFile) getItem();
        Map<String, String> response = new HashMap<>();
        for (FileItem fileItem : entityFiles.getFileItems()) {
            String uploadLink;
            try {
                uploadLink = uploadOne(EntityFile.builder().entityUid(entityFiles.getEntityUid()).container(entityFiles.getContainer()).name(fileItem.getName()).file(fileItem.getFile()).build()).get(fileItem.getName());
            } catch (FileUploadException ex) {
                uploadLink = "";
            }
            response.put(fileItem.getName(), uploadLink);
        }
        return response;
    }

}
