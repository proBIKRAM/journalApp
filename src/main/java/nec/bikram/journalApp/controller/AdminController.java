package nec.bikram.journalApp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import nec.bikram.journalApp.dto.UserDto;
import nec.bikram.journalApp.entity.User;
import nec.bikram.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Admin API", description = "get all users, create new admin ")
@Slf4j
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;


    @GetMapping("/all-users")
    @Operation(summary = "Get all users in JournalApp")
    public ResponseEntity<?> getAllUsers(){
        List<User> all=userService.getAll();
        if(all!=null && !all.isEmpty()){
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @PostMapping("/create-admin")
    @Operation(summary = "Create a new admin")
    public ResponseEntity<?> createUser(@RequestBody UserDto usr){

        User user = new User();
        user.setUsername(usr.getUsername());
        user.setPassword(usr.getPassword());
        user.setEmail(usr.getEmail());
        user.setSentimentAnalysis(user.isSentimentAnalysis());
        try {
            userService.saveAdmin(user);
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        }catch (Exception e){
            log.error("Error while creating new user(signup):{}",user.getUsername());
            return new ResponseEntity<>("Error while creating admin new user.",HttpStatus.BAD_REQUEST);
        }
    }

}
