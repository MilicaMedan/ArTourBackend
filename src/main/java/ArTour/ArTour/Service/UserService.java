package ArTour.ArTour.Service;

import ArTour.ArTour.Model.User;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.security.spec.KeySpec;
import java.util.Base64;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public String saveUserDetails(User user) throws ExecutionException, InterruptedException {

        String sqlInsertUser = "INSERT INTO user (id, mail, name, lastname, passwordHash, username, salt) VALUES (?, ?, ?, ?, ?, ?, ?)";
        byte[] saltBytes = new byte[16];
        new Random().nextBytes(saltBytes);
        String salt = new String(saltBytes, StandardCharsets.UTF_8);
        String passwordHash= this.hashPassword(user.getPasswordHash(), salt);
        int result = jdbcTemplate.update(sqlInsertUser, 0,user.getMail(), user.getName(), user.getLastname(), passwordHash, user.getUsername(), salt);
        if (result > 0) {
            return "A new row has been inserted.";
        }
        return "failed";
    }

    public User getUserDetails(String username) throws ExecutionException, InterruptedException {
        String selectUserByUsername = "SELECT * FROM user WHERE username = ?";
        User user = jdbcTemplate.queryForObject(selectUserByUsername, new Object[]{username}, (rs, rowNum) ->
                new User(
                        rs.getInt("id"),
                        rs.getString("mail"),
                        rs.getString("name"),
                        rs.getString("lastname"),
                        rs.getString("username"),
                        rs.getString("passwordHash"),
                        rs.getString("salt"),
                        rs.getInt("settings")
                ));
        if(user != null){
            return user;
        }
        return null;
    }

    public User getUserById(Integer id) throws ExecutionException, InterruptedException {
        String selectUserByUsername = "SELECT * FROM user WHERE id = ?";
        User user = jdbcTemplate.queryForObject(selectUserByUsername, new Object[]{id}, (rs, rowNum) ->
                new User(
                        rs.getInt("id"),
                        rs.getString("mail"),
                        rs.getString("name"),
                        rs.getString("lastname"),
                        rs.getString("username"),
                        rs.getString("passwordHash"),
                        rs.getString("salt"),
                        rs.getInt("settings")
                ));
        if(user != null){
            return user;
        }
        return null;
    }

    public static String hashPassword(String password, String saltString) {
        Base64.Encoder enc = Base64.getEncoder();
        byte[] hash = null;
        byte[] salt = saltString.getBytes();

        try {
            KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
            SecretKeyFactory f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            hash = f.generateSecret(spec).getEncoded();
        }catch(Exception e){
            e.printStackTrace();
        }

        return  enc.encodeToString(hash);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        String selectUserByUsername = "SELECT * FROM user WHERE username = ?";
        User user = jdbcTemplate.queryForObject(selectUserByUsername, new Object[]{username}, (rs, rowNum) ->
                new User(
                        rs.getInt("id"),
                        rs.getString("mail"),
                        rs.getString("name"),
                        rs.getString("lastname"),
                        rs.getString("username"),
                        rs.getString("passwordHash"),
                        rs.getString("salt"),
                        rs.getInt("settings")
                ));
        if(user != null){
            return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPasswordHash(), new ArrayList<>());
        }
        return null;
    }

    public String changeSettings(User user, Integer settings){
        System.out.println(""+settings);
        String sqlUpdate = "UPDATE user SET settings = ? WHERE id = ?";
        int result = jdbcTemplate.update(sqlUpdate, settings, user.getId());
        if (result > 0) {
            return "User has been updated.";
        }
        return "failed";
    }
}
