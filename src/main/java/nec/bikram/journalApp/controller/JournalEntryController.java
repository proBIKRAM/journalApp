package nec.bikram.journalApp.controller;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import nec.bikram.journalApp.dto.JournalDto;
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

@Tag(name = "Journal API", description = "Create, Read, Update, Delete Journal Entries")
@Slf4j
@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;
    @Autowired
    private UserService userService;

    @PostMapping
    @Operation(summary = "Create a new Journal Entry")
    public ResponseEntity<?> createJournalEntryForUser(@RequestBody JournalDto Entry) {
        JournalEntry myEntry = new JournalEntry();
        myEntry.setTitle(Entry.getTitle());
        myEntry.setContent(Entry.getContent());
        myEntry.setSentiment(Entry.getSentiment());
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            journalEntryService.saveEntry(myEntry, username);
            return new ResponseEntity<>(myEntry, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    @Operation(summary = "Get all Journal Entries of a User")
    public ResponseEntity<?> getAllJournalEntriesOfUser() {

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            User user = userService.getUserByUsername(username);
            List<JournalEntry> all = user.getJournalEntries();
            if (all.isEmpty() || all == null) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(all, HttpStatus.OK);
    }

    @GetMapping("/id/{myid}")
    @Operation(summary = "Get a Journal Entry by ID")
    public ResponseEntity<?> getJournalEntryById(@PathVariable String myid) {
        ObjectId id = new ObjectId(myid);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.getUserByUsername(username);
        List<JournalEntry> collect = user.getJournalEntries().stream().filter(x -> x.getId().equals(id)).collect(Collectors.toList());
        if (!collect.isEmpty()) {
            Optional<JournalEntry> journalEntry = journalEntryService.findById(id);
            if (journalEntry.isPresent()) {
                return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/id/{myid}")
    @Operation(summary = "Delete a Journal Entry by ID")
    public ResponseEntity<?> deleteJournalEntryById(@PathVariable String myid) {



        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        boolean removed = journalEntryService.deleteById(myid, username);
        if (removed) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/id/{id}")
    @Operation(summary = "Update a Journal Entry by ID")
    public ResponseEntity<?> updateJournalEntryById(@PathVariable String id,
                                                    @RequestBody JournalDto entryDto) {


        if (!ObjectId.isValid(id)) {
            return ResponseEntity.badRequest().body("Invalid ID format");
        }

        ObjectId myid = new ObjectId(id);


        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User user = userService.getUserByUsername(username);


        Optional<JournalEntry> journalOpt = user.getJournalEntries()
                .stream()
                .filter(j -> j.getId().equals(id))
                .findFirst();

        if (journalOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Journal not found or not authorized");
        }

        JournalEntry oldEntry = journalOpt.get();


        if (entryDto.getTitle() != null && !entryDto.getTitle().isEmpty()) {
            oldEntry.setTitle(entryDto.getTitle());
        }
        if (entryDto.getContent() != null && !entryDto.getContent().isEmpty()) {
            oldEntry.setContent(entryDto.getContent());
        }
        if (entryDto.getSentiment() != null) {
            oldEntry.setSentiment(entryDto.getSentiment());
        }

        journalEntryService.saveEntry(oldEntry);

        return ResponseEntity.ok(oldEntry);
    }
}

