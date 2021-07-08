package ArTour.ArTour.Service;

import ArTour.ArTour.Model.Post;
import com.google.api.core.ApiFuture;
import com.google.cloud.Timestamp;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class MainService {

    public String savePostDetails(Post post) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> collectionApiFuture = dbFirestore.collection("posts").document(post.getName()).set(post);
        return collectionApiFuture.get().getUpdateTime().toString();
    }

    public List<Post> getPosts() throws ExecutionException, InterruptedException {
        //Class<Post> parameterizedType = Post.class;
        Firestore dbFirestore = FirestoreClient.getFirestore();
        CollectionReference collectionReference = dbFirestore.collection("posts");
        ApiFuture<QuerySnapshot> querySnapshotApiFuture = collectionReference.get();
        try {
            List<QueryDocumentSnapshot> queryDocumentSnapshots = querySnapshotApiFuture.get().getDocuments();

            var snapshots = queryDocumentSnapshots.iterator();
                  /*  .map(queryDocumentSnapshot -> queryDocumentSnapshot.toObject(parameterizedType))
                    .collect(Collectors.toList());*/
            List<Post> posts = new ArrayList<Post>();
            while(snapshots.hasNext()){
                QueryDocumentSnapshot snapshot = snapshots.next();
                Post post = new Post();
                post.setUsername(snapshot.getData().get("username").toString());
                post.setName(snapshot.getData().get("name").toString());
                Timestamp pom = (Timestamp) snapshot.getData().get("datetime");
                post.setDatetime(pom);

                posts.add(post);

            }
            return posts;
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException("Exception occurred while retrieving all document for posts");
        }
        /*Iterable<DocumentReference> dr = dbFirestore.collection("post").listDocuments();
        ApiFuture<DocumentSnapshot> future = dr.get();
        DocumentSnapshot ds = future.get();
        User user = null;
        if(ds.exists()){
            user = ds.toObject(User.class);
            return user;
        }else {
            return null;
        }*/
    }

}
