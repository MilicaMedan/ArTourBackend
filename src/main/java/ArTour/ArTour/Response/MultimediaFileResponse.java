package ArTour.ArTour.Response;

import java.io.Serializable;
import java.time.LocalDateTime;

public class MultimediaFileResponse  implements Serializable {
    private Long id;
    private String name;
    private LocalDateTime datetime;
    private Long user_id;
    private String username;
    private Boolean ratedByYou;
    private Double averageMark;

    public MultimediaFileResponse() {
    }

    public MultimediaFileResponse(Long id, String name, LocalDateTime datetime, Long user_id, String username, Boolean ratedByYou) {
        this.id = id;
        this.name = name;
        this.datetime = datetime;
        this.user_id = user_id;
        this.username = username;
        this.ratedByYou = ratedByYou;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getDatetime() {
        return datetime;
    }

    public void setDatetime(LocalDateTime datetime) {
        this.datetime = datetime;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Boolean getRatedByYou() {
        return ratedByYou;
    }

    public void setRatedByYou(Boolean ratedByYou) {
        this.ratedByYou = ratedByYou;
    }

    public Double getAverageMark() {
        return averageMark;
    }

    public void setAverageMark(Double averageMark) {
        this.averageMark = averageMark;
    }
}
