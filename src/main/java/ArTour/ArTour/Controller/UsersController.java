package ArTour.ArTour.Controller;

import ArTour.ArTour.JWT.AuthenticationRequest;
import ArTour.ArTour.JWT.AuthenticationResponse;
import ArTour.ArTour.JWT.JwtUtil;
import ArTour.ArTour.Model.User;
import ArTour.ArTour.Request.SettingsRequest;
import ArTour.ArTour.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

@RestController
@CrossOrigin
public class UsersController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtTokenUtil;

    @Autowired
    UserService userService;

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @RequestMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {

        User user = userService.getUserDetails(authenticationRequest.getUsername());
        String passwordHash = userService.hashPassword(authenticationRequest.getPassword(), user.getSalt());
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), passwordHash)
            );
        }
        catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password", e);
        }

        final UserDetails userDetails =  userService.loadUserByUsername(authenticationRequest.getUsername());
        final String jwt = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponse(jwt, user.getSettings()));
    }

    @GetMapping("/logoutUser")
    @ResponseBody
    public ResponseEntity<?> logout() throws Exception {
        String jwt = "";
        return ResponseEntity.ok(new AuthenticationResponse(jwt, 0));
    }

    @PostMapping("/createUser")
    public String postUser(@RequestBody User user) throws ExecutionException, InterruptedException {
       return userService.saveUserDetails(user);
    }

    @GetMapping("/getUser")
    @ResponseBody
    public UserDetails getUser(@RequestParam String username) throws ExecutionException, InterruptedException {
        UserDetails user = userService.loadUserByUsername(username);
        return user;
    }

    @RequestMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody User user) throws Exception {
        String result = userService.saveUserDetails(user);
        if(result.equals("A new row has been inserted.")){
            final UserDetails userDetails = userService.loadUserByUsername(user.getUsername());

            final String jwt = jwtTokenUtil.generateToken(userDetails);

            return ResponseEntity.ok(new AuthenticationResponse(jwt,0));
        }else {
            throw new Exception("Signup failed.", new RuntimeException());
        }
    }

    @RequestMapping("/changeSettings")
    public ResponseEntity<?> changeSettings(@RequestBody SettingsRequest settings, @RequestHeader("Authorization") String token) throws Exception {
        System.out.println(""+settings.getSettings());
        String username = jwtTokenUtil.extractUsername(token.split(" ")[1]);
        System.out.println(""+username);
        User user = userService.getUserDetails(username);
        System.out.println(""+user.getUsername());
        String result =  userService.changeSettings(user,settings.getSettings());
        if(result.equals("User has been updated.")){
            return ResponseEntity.ok(settings);
        }else {
            throw new Exception("Update failed.", new RuntimeException());
        }
    }

}
