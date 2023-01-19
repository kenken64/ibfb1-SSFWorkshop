package sg.edu.nus.iss.app.workshop16.model;

import java.io.Serializable;

import jakarta.json.Json;
import jakarta.json.JsonNumber;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;

public class Rulebook implements Serializable {
    private int total_count;
    private String file;

    public String getFile() {
        return file;
    }

    public int getTotal_count() {
        return total_count;
    }

    public void setTotal_count(int total_count) {
        this.total_count = total_count;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public JsonObjectBuilder toJSON() {
        return Json.createObjectBuilder()
                .add("total_count", this.getTotal_count())
                .add("file", this.getFile());
    }

    public static Rulebook createJson(JsonObject o) {
        Rulebook rb = new Rulebook();
        JsonNumber totalCnt = o.getJsonNumber("total_count");
        String file = o.getString("file");
        rb.total_count = totalCnt.intValue();
        rb.file = file;
        return rb;
    }

}
