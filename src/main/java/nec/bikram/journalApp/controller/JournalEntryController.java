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

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;
    @Autowired
    private UserService userService;

    /*   @PostMapping
       public ResponseEntity<?> createJournalEntry(@RequestBody JournalEntry myEntry) {
          try {
                journalEntryService.saveEntry(myEntry, user);
                return new ResponseEntity<>(myEntry,HttpStatus.CREATED);
          }catch (Exception e){
              return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
          }
       }

     */
    @PostMapping
    public ResponseEntity<?> createJournalEntryForUser(@RequestBody JournalEntry myEntry) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            journalEntryService.saveEntry(myEntry, username);
            return new ResponseEntity<>(myEntry, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
//   @GetMapping
//    public ResponseEntity<?> getAllEntries(){
//        List<JournalEntry> all=journalEntryService.getAll();
//        if(!all.isEmpty() && all != null){
//            return new ResponseEntity<>(all,HttpStatus.OK);
//        }
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }

    @GetMapping
    public ResponseEntity<?> getAllJournalEntriesOfUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.getUserByUsername(username);
        List<JournalEntry> all = user.getJournalEntries();
        if (!all.isEmpty() && all != null) {
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/id/{myid}")
    public ResponseEntity<?> getJournalEntryById(@PathVariable ObjectId myid) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.getUserByUsername(username);
        List<JournalEntry> collect = user.getJournalEntries().stream().filter(x -> x.getId().equals(myid)).collect(Collectors.toList());
        Optional<JournalEntry> journal = journalEntryService.findById(myid);
        if (!collect.isEmpty()) {
            Optional<JournalEntry> journalEntry = journalEntryService.findById(myid);
            if (journalEntry.isPresent()) {
                return new ResponseEntity<>(journal.get(), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/id/{myid}")
    public ResponseEntity<?> deleteJournalEntryById(@PathVariable ObjectId myid) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        boolean removed = journalEntryService.deleteById(myid, username);
        if (removed) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/id/{myId}")
    public ResponseEntity<?> updateJournalEntryById(@PathVariable ObjectId myId, @RequestBody JournalEntry myEntry) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.getUserByUsername(username);
        List<JournalEntry> collect = user.getJournalEntries().stream().filter(x -> x.getId().equals(myId)).collect(Collectors.toList());

        if (!collect.isEmpty()) {
            Optional<JournalEntry> journalEntry = journalEntryService.findById(myId);
            if (journalEntry.isPresent()) {
                JournalEntry old = journalEntry.get();

                old.setTitle(myEntry.getTitle() != null && !myEntry.getTitle().isEmpty() ? myEntry.getTitle() : old.getTitle());
                old.setContent(myEntry.getContent() != null && !myEntry.getContent().isEmpty() ? myEntry.getContent() : old.getContent());

                journalEntryService.saveEntry(old);
                return new ResponseEntity<>(old, HttpStatus.OK);
            }
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);


    }
}

