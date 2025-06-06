package nec.bikram.journalApp.controller;

import nec.bikram.journalApp.entity.JournalEntry;
import nec.bikram.journalApp.entity.User;
import nec.bikram.journalApp.service.JournalEntryService;
import nec.bikram.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<?> getAllUser(){
        List<User> all=userService.getAll();
        if(!all.isEmpty() && all!=null){
            return new ResponseEntity<>(all,HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
    @GetMapping("/id/{id}")
    public ResponseEntity<?> getUserById(@PathVariable ObjectId id){

           Optional<User> usr = userService.findById(id);
           if(usr.isPresent()) {
                return new ResponseEntity<>(usr.get(), HttpStatus.OK);
           }
           else {
               return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
           }
    }
    @DeleteMapping("/id/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable ObjectId id){
        userService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody User user){
        userService.saveNewUser(user);
        return new ResponseEntity<>(user,HttpStatus.CREATED);
    }
    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody User user){
       try {
           Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
           String username=authentication.getName();
           User userInDb = userService.getUserByUsername(username);
           if (userInDb != null) {
               userInDb.setUsername(user.getUsername());
               userInDb.setPassword(user.getPassword());
           }
           userService.saveNewUser(userInDb);
           return new ResponseEntity<>(userInDb, HttpStatus.OK);
       }catch (Exception e){
           return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
       }
    }
/// here
@GetMapping("/{username}")
public ResponseEntity<?> gettingUserByUsername(@PathVariable String username) {
    User user = userService.getUserByUsername(username);

    if (user != null) {
        return ResponseEntity.ok(user); // 200 OK with user data
    } else {
        return ResponseEntity
                .status(404)
                .body("User not found"); // 404 Not Found
    }
}





}
