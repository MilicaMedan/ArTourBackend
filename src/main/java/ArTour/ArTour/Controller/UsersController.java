package ArTour.ArTour.Controller;

import ArTour.ArTour.JWT.AuthenticationRequest;
import ArTour.ArTour.JWT.AuthenticationResponse;
import ArTour.ArTour.JWT.JwtUtil;
import ArTour.ArTour.Model.User;
import ArTour.ArTour.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

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


    @RequestMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
            );
        }
        catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password", e);
        }


        final UserDetails userDetails = userService
                .loadUserByUsername(authenticationRequest.getUsername());

        final String jwt = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponse(jwt));
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



}
