package ArTour.ArTour.Model;

import java.util.Date;

public class PostResponse {
    private String name;
    private String username;
    private Date datetime;
    private String base64;

    public PostResponse() {
    }

    public PostResponse(String name, String username, Date datetime, String base64) {
        this.name = name;
        this.username = username;
        this.datetime = datetime;
        this.base64 = base64;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    public String getBase64() {
        return base64;
    }

    public void setBase64(String base64) {
        this.base64 = base64;
    }
}
