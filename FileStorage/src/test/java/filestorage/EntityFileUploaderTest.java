package filestorage;

import filestorage.enums.Container;
import filestorage.models.EntityFile;
import filestorage.models.FileItem;
import filestorage.models.MultipleEntityFile;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
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
    public void uploadToEntityPublicContainer() {
        File file = new File("testFile.txt");
        try {
            FileWriter fileWriter = new FileWriter("testFile.txt");
            fileWriter.write("test file");
            fileWriter.close();
            EntityFileUploader fileUploader = new EntityFileUploader(EntityFile.builder().entityUid("eUid").container(Container.PUBLIC).name("logo").file(file).build());
            Map<String, String> location = fileUploader.upload();
            assertEquals("http://194.35.120.40:10000/devstoreaccount1/euid/logo", location.get("logo"));
        } catch (Exception ex) {
            ex.printStackTrace();
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