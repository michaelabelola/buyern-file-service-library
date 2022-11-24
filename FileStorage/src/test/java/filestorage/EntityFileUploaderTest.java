package filestorage;

import com.mashape.unirest.http.exceptions.UnirestException;
import filestorage.enums.Container;
import filestorage.exceptions.FileUploadException;
import filestorage.models.EntityFile;
import filestorage.models.FileItem;
import filestorage.models.MultipleEntityFile;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class EntityFileUploaderTest {
    private final List<FileItem> fileItems = new ArrayList<>();
    @Before
    public void setUp() throws Exception {
        //create files
        int filesCount = 5;
        for (int i = 0; i < filesCount; i++) {
            String filePath = "testFile" + i + ".txt";
            File file = new File(filePath);
            FileWriter fileWriter = new FileWriter(filePath);
            fileWriter.write("test file: " + i);
            fileWriter.close();
            fileItems.add(new FileItem(String.valueOf(i), file));
        }
    }

    @Test
    public void uploadToEntityPublicContainer() throws UnirestException, IOException {
        File file = new File("testFile.txt");
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter("testFile.txt");

        fileWriter.write("test file");
            fileWriter.close();
            EntityFileUploader fileUploader = new EntityFileUploader(EntityFile.builder().entityUid("testEntity").container(Container.PUBLIC).name("logo").file(file).build());
            Map<String, String> location = fileUploader.upload();
            assertNotNull(location.get("logo"));
        } catch (FileUploadException e) {
            assertEquals("entity storage does not exist","", e.getMessage());
        }
    }

    @Test
    public void uploadMultipleToEntityPublicContainer() {
        try {
            EntityFileUploader fileUploader = new EntityFileUploader(new MultipleEntityFile("eUid", Container.PUBLIC, fileItems));
            Map<String, String> location = fileUploader.upload();
            assertEquals(5, location.size());
            location.forEach((s, s2) -> assertNotNull(s2));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}