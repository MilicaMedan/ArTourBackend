package ArTour.ArTour.Controller;
import ArTour.ArTour.JWT.AuthenticationRequest;
import ArTour.ArTour.JWT.JwtUtil;
import ArTour.ArTour.Model.Post;
import ArTour.ArTour.Model.PostResponse;
import ArTour.ArTour.Service.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import com.google.cloud.Timestamp;
import org.springframework.web.multipart.MultipartFile;

import java.util.concurrent.ExecutionException;


@RestController
@CrossOrigin
public class MainController {

    @Autowired
    MainService mainService;

    @Autowired
    private JwtUtil jwtTokenUtil;

    @PostMapping("/uploadFilePost")
    public String uploadFile(@RequestBody Post obj, @RequestHeader("Authorization") String token) throws ExecutionException, InterruptedException, IOException {
        /*
        if(obj == null){
            System.out.println("null");
            return null;
        }else {
            try {

                byte[] decodedBytes = Base64.getDecoder().decode(obj.getUsername());
                File targetFile = new File("C:/Users/Milica/Desktop/ArTourBack/ArTour/files/"+obj.getName());
                OutputStream outStream = new FileOutputStream(targetFile);
                outStream.write(decodedBytes);
                outStream.close();

                Post post=new Post();
                //post.setDatetime(Timestamp.now());
                post.setName(obj.getName());
                String username = jwtTokenUtil.extractUsername(token.split(" ")[1]);
                post.setUsername(username);

               return mainService.savePostDetails(post);

            } catch (Exception e) {
                throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
                //return null;
            }
        }
        */
        return "";
    }

    @PostMapping(value = "/uploadFile", consumes =  {MediaType.MULTIPART_FORM_DATA_VALUE})
    public String uploadPost(@RequestPart (value="file") MultipartFile obj, @RequestHeader("Authorization") String token) throws ExecutionException, InterruptedException, IOException {
        if(obj == null){
            System.out.println("null");
            return null;
        }else {
            try {

                byte[] decodedBytes = obj.getBytes();
                File targetFile = new File("C:/Users/Milica/Desktop/ArTourBack/ArTour/files/"+obj.getName());
                OutputStream outStream = new FileOutputStream(targetFile);
                outStream.write(decodedBytes);
                outStream.close();

                Post post=new Post();
                //post.setDatetime(Timestamp.now());
                post.setName(obj.getName());
                String username = jwtTokenUtil.extractUsername(token.split(" ")[1]);
                post.setUsername(username);

                return mainService.savePostDetails(post);

            } catch (Exception e) {
                throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
                //return null;
            }
        }

    }

    @GetMapping("/getPosts")
    @ResponseBody
    public List<PostResponse> getPosts() throws ExecutionException, InterruptedException {
        List<Post> posts = mainService.getPosts();
        List<PostResponse> responses = new ArrayList<>();
        for(Post p : posts){
           PostResponse pr = new PostResponse();
           pr.setName(p.getName());
           pr.setUsername(p.getUsername());
           //pr.setDatetime(new Date(p.getDatetime().getSeconds()));
           String fileName = "C:/Users/Milica/Desktop/ArTourBack/ArTour/files/"+p.getName();
           String base64 = "";
            try{
                InputStream finput  = new FileInputStream(fileName);
                byte[] imageBytes = new byte[1000000];
                finput.read(imageBytes, 0, imageBytes.length);
                finput.close();
                base64 = org.apache.commons.codec.binary.Base64.encodeBase64String(imageBytes);
            }catch(FileNotFoundException e){
                e.printStackTrace();
            }catch(IOException e){
                e.printStackTrace();
            }

           pr.setBase64(base64);

           responses.add(pr);
        }
        return responses;
    }
}
