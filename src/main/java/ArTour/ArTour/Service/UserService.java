package ArTour.ArTour.Service;

import ArTour.ArTour.Model.User;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

@Service
public class UserService implements UserDetailsService {

    public String saveUserDetails(User user) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> collectionApiFuture = dbFirestore.collection("users").document(user.getUsername()).set(user);
        return collectionApiFuture.get().getUpdateTime().toString();
    }

    public User getUserDetails(String id) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        DocumentReference dr = dbFirestore.collection("users").document(id);
        ApiFuture<DocumentSnapshot> future = dr.get();
        DocumentSnapshot ds = future.get();
        User user = null;
        if(ds.exists()){
            user = ds.toObject(User.class);
            return user;
        }else {
            return null;
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        try {
            Firestore dbFirestore = FirestoreClient.getFirestore();
            DocumentReference dr = dbFirestore.collection("users").document(username);
            ApiFuture<DocumentSnapshot> future = dr.get();
            DocumentSnapshot ds = null;
            ds = future.get();
            User user = null;
            if(ds.exists()){
                user = ds.toObject(User.class);
                return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPasswordHash(), new ArrayList<>());
                // return user;
            }else {
                return null;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }
}
