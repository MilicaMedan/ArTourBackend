package ArTour.ArTour.Model;

import java.sql.Timestamp;

public class MultimediaFile {

    private Integer id;
    private String name;
    private Timestamp datetime;
    private Integer user_id;

    public MultimediaFile() {
    }

    public MultimediaFile(Integer id, String name, Timestamp datetime, Integer user_id) {
        this.id = id;
        this.name = name;
        this.datetime = datetime;
        this.user_id = user_id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Timestamp getDatetime() {
        return datetime;
    }

    public void setDatetime(Timestamp datetime) {
        this.datetime = datetime;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }
}
