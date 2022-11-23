package filestorage;

import com.mashape.unirest.http.exceptions.UnirestException;
import filestorage.exceptions.EntityStorageException;
import org.junit.Assert;
import org.junit.Test;


public class EntityStorageTest {

    @Test
    public void registerEntity() throws UnirestException, EntityStorageException {
        Assert.assertTrue("Entity Storage initialized successfully", EntityStorage.Register("eeuidd"));
    }
}