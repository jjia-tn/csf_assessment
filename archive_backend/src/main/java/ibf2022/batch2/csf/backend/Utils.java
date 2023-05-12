package ibf2022.batch2.csf.backend;

import java.io.StringReader;

import org.bson.Document;

import ibf2022.batch2.csf.backend.models.Archive;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

public class Utils {

    public static JsonObject toJson(Document doc) {

        JsonReader reader = Json.createReader(new StringReader(doc.toJson()));

        return reader.readObject();
    }

    public static Archive toArchive(JsonObject obj) {

        Archive archive = new Archive();

        archive.setBundleId(obj.getString("bundleId"));
        // archive.setDate(obj.getString("date[$date]"));
        archive.setTitle(obj.getString("title"));
        archive.setName(obj.getString("name"));
        archive.setComments(obj.getString("comments"));
        archive.setUrl(obj.getString("url"));
        
        return archive;
    }
    
}
