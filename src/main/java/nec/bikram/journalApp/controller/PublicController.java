package nec.bikram.journalApp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import nec.bikram.journalApp.dto.UserDto;
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

@Tag(name = "Public API", description = "Login, Signup")
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
    @Operation(summary = "1st step - Initial Health Check")
    public String healthCheck(){return "OK";}


    @PostMapping("/login")
    @Operation(summary = "Login a User")
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
    @Operation(summary = "Create a new User")
    public void signUp (@RequestBody UserDto user) {
        User newuser = new User();
        newuser.setUsername(user.getUsername());
        newuser.setPassword(user.getPassword());
        newuser.setEmail(user.getEmail());
        newuser.setSentimentAnalysis(user.isSentimentAnalysis());
        userService.saveNewUser(newuser);
    }


}
