package ArTour.ArTour.Controller;

import ArTour.ArTour.JWT.JwtUtil;
import ArTour.ArTour.Model.Mark;
import ArTour.ArTour.Model.MultimediaFile;
import ArTour.ArTour.Model.User;
import ArTour.ArTour.Response.MultimediaFileResponse;
import ArTour.ArTour.Service.MainService;
import ArTour.ArTour.Service.UserService;
import com.google.api.client.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.sql.Timestamp;
import org.springframework.web.multipart.MultipartFile;

import java.util.ListIterator;
import java.util.concurrent.ExecutionException;
import org.springframework.jdbc.core.JdbcTemplate;


@RestController
@CrossOrigin
public class MainController {

    @Autowired
    MainService mainService;

    @Autowired
    UserService userService;

    @Autowired
    private JwtUtil jwtTokenUtil;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostMapping(value = "/uploadFile", consumes =  {MediaType.MULTIPART_FORM_DATA_VALUE})
    public String uploadPost(@RequestPart (value="file") MultipartFile obj, @RequestHeader("Authorization") String token) throws ExecutionException, InterruptedException, IOException {
        if(obj == null){
            System.out.println("null");
            return null;
        }else {
            try {
                System.out.println(obj.getOriginalFilename());
                byte[] decodedBytes = obj.getBytes();
                File targetFile = new File("C:/Users/Milica/Desktop/ArTourBack/ArTour/files/"+obj.getOriginalFilename());
                OutputStream outStream = new FileOutputStream(targetFile);
                outStream.write(decodedBytes);
                outStream.close();
                String username = jwtTokenUtil.extractUsername(token.split(" ")[1]);
                User user = userService.getUserDetails(username);
                MultimediaFile mFile= new MultimediaFile();
                mFile.setId(0);
                mFile.setDatetime(new Timestamp(System.currentTimeMillis()));
                mFile.setName(obj.getOriginalFilename());
                mFile.setUser_id( user.getId());

                return mainService.savePostDetails(mFile);

            } catch (Exception e) {
                throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
            }
        }

    }

    @GetMapping(value = "/image/", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] getImage(@RequestParam String name) throws IOException {
        byte[] imageBytes = null;
        imageBytes =  Files.readAllBytes(Paths.get("C:/Users/Milica/Desktop/ArTourBack/ArTour/files/"+name));
        return imageBytes;
    }

    @GetMapping(value = "/video/", produces = "video/mp4")
    public @ResponseBody byte[] getVideo(@RequestParam String name) throws IOException {
        byte[] imageBytes = null;
        imageBytes =  Files.readAllBytes(Paths.get("C:/Users/Milica/Desktop/ArTourBack/ArTour/files/"+name));
        return imageBytes;
    }

    @GetMapping(value = "/audio/", produces = "audio/mp3")
    public @ResponseBody byte[] getAudio(@RequestParam String name) throws IOException {
        byte[] imageBytes = null;
        imageBytes =  Files.readAllBytes(Paths.get("C:/Users/Milica/Desktop/ArTourBack/ArTour/files/"+name));
        return imageBytes;
    }

    @GetMapping("/getPosts")
    @ResponseBody
    public List<MultimediaFileResponse> getPosts( @RequestHeader("Authorization") String token) throws ExecutionException, InterruptedException {
        String username = jwtTokenUtil.extractUsername(token.split(" ")[1]);
        User user = userService.getUserDetails(username);
        List<MultimediaFileResponse> files = mainService.getFiles(user);
        List<MultimediaFileResponse> responses = new ArrayList<>();
        for(MultimediaFileResponse f : files){
            MultimediaFileResponse mfr = new MultimediaFileResponse();
            mfr.setId(f.getId());
            mfr.setName(f.getName());
            mfr.setDatetime(f.getDatetime());
            mfr.setUser_id(f.getUser_id());
            Integer user_id = f.getUser_id().intValue();
            mfr.setUsername(userService.getUserById(user_id).getUsername());

            List<Mark> marks = mainService.getMarksByPost(mfr.getId().intValue());
            if(!marks.isEmpty()){
                Double averageMark = 0.0;
                Double sum = 0.0;
                ListIterator<Mark> iterator = marks.listIterator();
                while(iterator.hasNext()){
                    sum += iterator.next().getMark();
                }
                System.out.println(""+sum);
                System.out.println(""+(marks.size()));

                averageMark = sum / (marks.size());
                mfr.setAverageMark(averageMark);
            }else {
                mfr.setAverageMark(0.0);
            }

            Mark markByYou = marks.stream().filter(mark -> user.getId() == mark.getUser_id()).findFirst().orElse(null);
            if(markByYou == null){
                mfr.setRatedByYou(false);
            }else {
                mfr.setRatedByYou(true);
            }
            responses.add(mfr);
        }
        return responses;
    }


    @GetMapping("/getMyPosts")
    @ResponseBody
    public List<MultimediaFileResponse> getMyPosts( @RequestHeader("Authorization") String token) throws ExecutionException, InterruptedException {
        String username = jwtTokenUtil.extractUsername(token.split(" ")[1]);
        User user = userService.getUserDetails(username);
        List<MultimediaFileResponse> files = mainService.getMyFiles(user);
        List<MultimediaFileResponse> responses = new ArrayList<>();
        for(MultimediaFileResponse f : files){
            MultimediaFileResponse mfr = new MultimediaFileResponse();
            mfr.setId(f.getId());
            mfr.setName(f.getName());
            mfr.setDatetime(f.getDatetime());
            mfr.setUser_id(f.getUser_id());
            Integer user_id = f.getUser_id().intValue();
            mfr.setUsername(userService.getUserById(user_id).getUsername());

            List<Mark> marks = mainService.getMarksByPost(mfr.getId().intValue());
            if(!marks.isEmpty()){
                Double averageMark = 0.0;
                Double sum = 0.0;
                ListIterator<Mark> iterator = marks.listIterator();
                while(iterator.hasNext()){
                    sum += iterator.next().getMark();
                }
                System.out.println(""+sum);
                System.out.println(""+(marks.size()));

                averageMark = sum / (marks.size());
                mfr.setAverageMark(averageMark);
            }else {
                mfr.setAverageMark(0.0);
            }

            mfr.setRatedByYou(true);
            responses.add(mfr);
        }
        return responses;
    }

    @PostMapping("/uploadMark")
    public Double markUpload(@RequestBody Mark mark,@RequestHeader("Authorization") String token) throws ExecutionException, InterruptedException {
        String username = jwtTokenUtil.extractUsername(token.split(" ")[1]);
        User user = userService.getUserDetails(username);
        mark.setId(0);
        mark.setUser_id(user.getId());
        String result =  mainService.uploadMark(mark);
        Double averageMark = 0.0;
        if(result.equals("A new row has been inserted.")){
            List<Mark> marks = mainService.getMarksByPost(mark.getPost_id());
            if(!marks.isEmpty()){
                Double sum = 0.0;
                ListIterator<Mark> iterator = marks.listIterator();
                while(iterator.hasNext()){
                    sum += iterator.next().getMark();
                }
                averageMark = sum / (marks.size());
            }
        }

        return averageMark;
    }
}
