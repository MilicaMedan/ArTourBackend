package ArTour.ArTour.Model;


import com.google.cloud.Timestamp;

public class Post {
    private String name;
    private String username;
    private Timestamp datetime;

    public Post() {
    }

    public Post(String name, String username, Timestamp datetime) {
        this.name = name;
        this.username = username;
        this.datetime = datetime;
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

    public Timestamp getDatetime() {
        return datetime;
    }

    public void setDatetime(Timestamp datetime) {
        this.datetime = datetime;
    }
}
