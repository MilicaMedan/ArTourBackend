package ArTour.ArTour.Service;

import ArTour.ArTour.Model.Mark;
import ArTour.ArTour.Model.MultimediaFile;
import ArTour.ArTour.Model.User;
import ArTour.ArTour.Response.MultimediaFileResponse;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Service
public class MainService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public String savePostDetails(MultimediaFile mFile) throws ExecutionException, InterruptedException {

        String sqlInsertPost = "INSERT INTO post (id, name, datetime, user_id) VALUES (?, ?, ?, ?)";

        int result = jdbcTemplate.update(sqlInsertPost, mFile.getId(), mFile.getName(), mFile.getDatetime(), mFile.getUser_id());


        if (result > 0) {
            return "A new row has been inserted.";
        }
        return "failed";

    }

    public List<MultimediaFileResponse> getFiles(User user) throws ExecutionException, InterruptedException {

        try {
            String sql = "SELECT * FROM post WHERE user_id != ?";

            List<MultimediaFileResponse> files = new ArrayList<>();
            List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql, user.getId());
            if(!rows.isEmpty()){
                for (Map row : rows) {
                    MultimediaFileResponse obj = new MultimediaFileResponse();
                    obj.setId((Long) row.get("id"));
                    obj.setName((String) row.get("name"));
                    obj.setDatetime((LocalDateTime) row.get("datetime"));
                    obj.setUser_id((Long) row.get("user_id"));
                    files.add(obj);
                }
            }

            return files;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Exception occurred while retrieving all document for files");
        }
    }

    public List<MultimediaFileResponse> getMyFiles(User user) throws ExecutionException, InterruptedException {

        try {
            String sql = "SELECT * FROM post WHERE user_id = ?";

            List<MultimediaFileResponse> files = new ArrayList<>();

            List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql, user.getId());

            if(!rows.isEmpty()){
                for (Map row : rows) {
                    MultimediaFileResponse obj = new MultimediaFileResponse();
                    obj.setId((Long) row.get("id"));
                    obj.setName((String) row.get("name"));
                    obj.setDatetime((LocalDateTime) row.get("datetime"));
                    obj.setUser_id((Long) row.get("user_id"));
                    files.add(obj);
                }
            }
            return files;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Exception occurred while retrieving all document for files");
        }

    }

    public String uploadMark(Mark mark){
        String sqlInsertPost = "INSERT INTO mark (id, mark, user_id, post_id) VALUES (?, ?, ?, ?)";

        int result = jdbcTemplate.update(sqlInsertPost, mark.getId(), mark.getMark(),  mark.getUser_id(), mark.getPost_id());


        if (result > 0) {
            return "A new row has been inserted.";
        }
        return "failed";
    }

    public List<Mark> getMarksByPost(Integer post_id){
        try {
            String sql = "SELECT * FROM mark where post_id = ?";

            List<Mark> files = new ArrayList<>();

            List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql,post_id);

            for (Map row : rows) {
                Mark obj = new Mark();
                Integer id = ((Long) row.get("id")).intValue();
                Integer userId = ((Long) row.get("user_id")).intValue();
                Integer postId = ((Long) row.get("post_id")).intValue();
                Double mark =  ((BigDecimal) row.get("mark")).doubleValue();
                obj.setId(id);
                obj.setMark(mark);
                obj.setUser_id(userId);
                obj.setPost_id(postId);
                files.add(obj);
            }

            return files;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Exception occurred while retrieving all document for files");
        }
    }

}
