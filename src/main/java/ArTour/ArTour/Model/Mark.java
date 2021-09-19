package ArTour.ArTour.Model;

import java.io.Serializable;

public class Mark implements Serializable {
    private Integer id;
    private Double mark;
    private Integer user_id;
    private Integer post_id;

    public Mark() {
    }

    public Mark(Integer id, Double mark, Integer user_id, Integer post_id) {
        this.id = id;
        this.mark = mark;
        this.user_id = user_id;
        this.post_id = post_id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getMark() {
        return mark;
    }

    public void setMark(Double mark) {
        this.mark = mark;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Integer getPost_id() {
        return post_id;
    }

    public void setPost_id(Integer post_id) {
        this.post_id = post_id;
    }
}
