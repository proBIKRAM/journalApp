package nec.bikram.journalApp.controller;

import nec.bikram.journalApp.entity.User;
import nec.bikram.journalApp.repository.UserRepository;
import nec.bikram.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private UserService userService;

    @GetMapping("/health-check")
    public String healthCheck(){return "OK";}

    @PostMapping("/create-user")
    public void createuser (@RequestBody User user) {
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
