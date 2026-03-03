package nec.bikram.journalApp.controller;

import lombok.extern.slf4j.Slf4j;
import nec.bikram.journalApp.entity.User;
import nec.bikram.journalApp.repository.UserRepository;
import nec.bikram.journalApp.service.UserDetailsServiceImpl;
import nec.bikram.journalApp.service.UserService;
import nec.bikram.journalApp.utils.JwtUtil;
import nec.bikram.journalApp.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserService userService;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/health-check")
    public String healthCheck(){return "OK";}

    @PostMapping("/login")
    public ResponseEntity<String> logIn (@RequestBody User user) {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            user.getUsername(), user.getPassword()));
            UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
            String jwt = jwtUtil.generateToken(userDetails.getUsername());
            return ResponseEntity.ok(jwt);
        }catch (Exception e){
            log.error("Error while logging in user:{}",user.getUsername());
            return ResponseEntity.badRequest().body("Invalid username or password");
        }
    }


    @PostMapping("/signup")
    public void signUp (@RequestBody User user) {
        userService.saveNewUser(user);
    }
    @PutMapping("/{create-user}")
    public ResponseEntity<?> updateUser(@RequestBody User user){
        try {
            userService.saveUser(user);
            return new ResponseEntity<>(user, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
