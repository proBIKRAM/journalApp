package nec.bikram.journalApp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import nec.bikram.journalApp.api.response.WeatherResponse;
import nec.bikram.journalApp.dto.UserDto;
import nec.bikram.journalApp.entity.JournalEntry;
import nec.bikram.journalApp.entity.User;
import nec.bikram.journalApp.service.JournalEntryService;
import nec.bikram.journalApp.service.UserService;
import nec.bikram.journalApp.service.WeatherService;
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

@Tag(name = "User API", description = "Create, Read, Update, Delete Users")
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private WeatherService weatherService;

    @GetMapping("search/{username}")
    @Operation(summary = "Search other User by Username")
    public ResponseEntity<?> getUserById(@PathVariable String username){

           User usr = userService.getUserByUsername(username);
           if(usr!=null) {
                return new ResponseEntity<>(usr, HttpStatus.OK);
           }
           else {
               return new ResponseEntity<>("User Not Found",HttpStatus.NO_CONTENT);
           }
    }

    @DeleteMapping
    @Operation(summary = "Delete User")
    public ResponseEntity<?> deleteUser(){
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            userService.deleteByUsername(username);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            log.error("Error deleting user: {}", e.getMessage());
            return new ResponseEntity<>("Error deleting user ",HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping
    @Operation(summary = "Update User")
    public ResponseEntity<?> updateUser(@RequestBody UserDto usr){
        User user = new User();
        user.setUsername(usr.getUsername());
        user.setPassword(usr.getPassword());
        user.setEmail(usr.getEmail());
        user.setCity(usr.getCity());
        user.setSentimentAnalysis(user.isSentimentAnalysis());
       try {
           Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
           String username=authentication.getName();
           User userInDb = userService.getUserByUsername(username);
           if (userInDb != null  ) {

               userInDb.setUsername( !user.getUsername().isEmpty() ? user.getUsername() : userInDb.getUsername());
               userInDb.setPassword( !user.getPassword().isEmpty() ? user.getPassword() : userInDb.getPassword());
               userInDb.setEmail(user.getEmail() != null && !user.getEmail().isEmpty() ? user.getEmail()    : userInDb.getEmail());
               userInDb.setCity(user.getCity() != null && !user.getCity().isEmpty() ? user.getCity() : userInDb.getCity());
               userInDb.setSentimentAnalysis(user.isSentimentAnalysis());
           }
           userService.saveNewUser(userInDb);
           return new ResponseEntity<>(userInDb, HttpStatus.OK);
       }catch (Exception e){
           log.error("error updating user:{}",user.getUsername());
           return new ResponseEntity<>("error updating user",HttpStatus.BAD_REQUEST);
       }
    }
/// here
@GetMapping
@Operation(summary = "Get User by Username")
public ResponseEntity<?> gettingUserByUsername() {
    Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
    String username = authentication.getName();
    User user = userService.getUserByUsername(username);

    if (user != null) {
        return ResponseEntity.ok(user); // 200 OK with user data
    } else {
        log.error("User not found for username: {}", username);
        return ResponseEntity
                .status(404)
                .body("User not found"); // 404 Not Found
    }
}
@GetMapping("/greetings")
@Operation(summary = "Get Greetings/weather info(London)")
public ResponseEntity<?> greetings(){
    try {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) {
            log.error("Authentication object is null");
            return ResponseEntity.status(401).body("Unauthorized");
        }

        String name = authentication.getName();
        User userInDb = userService.getUserByUsername(name);
        String city=userInDb.getCity();

        if(city==null || city.isEmpty()){
            city="Kathmandu";
        }

        WeatherResponse weatherResponse = weatherService.getWeather(city);
        String greeting = "";
        if (weatherResponse != null) {
            greeting = city+", Weather feels like " + weatherResponse.getCurrent().getFeelsLike() + " °C";
        }
        return ResponseEntity.ok("Hello dear - " + name +"!"+ greeting);
    }catch (Exception e){
        log.error("Error getting greetings: {}", e.getMessage());
        return ResponseEntity.status(500).body("Error getting greetings");
    }
}

}
