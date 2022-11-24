package filestorage;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import filestorage.exceptions.EntityStorageException;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class EntityStorage {

    public static boolean Register(String entityUid) throws UnirestException, EntityStorageException {

        Map<String, String> headers = new HashMap<>();
        headers.put("accept", "application/json");

        HttpResponse<JsonNode> jsonResponse = Unirest.post("http://localhost:9081/api/v1/entity/register/" + entityUid)
                .headers(headers).asJson();
        if (jsonResponse.getStatus() == 200 && Objects.equals(jsonResponse.getBody().getObject().get("code").toString(), "00")) {
            return true;
        } else if ( Objects.equals(jsonResponse.getBody().getObject().get("code").toString(), "04")){
            return true;
        } else
            throw new EntityStorageException("Error Registering Entity Storage: " + jsonResponse.getBody().getObject().get("message").toString());

    }
}
