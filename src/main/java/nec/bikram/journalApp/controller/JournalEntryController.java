package nec.bikram.journalApp.controller;

import nec.bikram.journalApp.entity.JournalEntry;
import nec.bikram.journalApp.service.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    @PostMapping
    public JournalEntry createJournalEntry(@RequestBody JournalEntry myEntry) {
        myEntry.setDate(LocalDateTime.now());
        journalEntryService.saveEntry(myEntry);
        return myEntry;
    }
    @GetMapping
    public List<JournalEntry> getAllEntries(){
        return journalEntryService.getAll();
    }
    @GetMapping("/id/{myid}")
    public JournalEntry getJournalEntryById(@PathVariable ObjectId myid){
        return journalEntryService.findById(myid).orElse(null) ;
    }
    @DeleteMapping("/id/{myid}")
    public boolean deleteJournalEntryById(@PathVariable ObjectId myid)
    {
        journalEntryService.deleteById(myid);
        return true;
    }
    @PutMapping("/id/{myId}")
    public JournalEntry updateJournalEntryById(@PathVariable ObjectId myId, @RequestBody JournalEntry myEntry){
        JournalEntry old = journalEntryService.findById(myId).orElse(null) ;
        if(old!=null){
            old.setTitle(myEntry.getTitle()!=null && !myEntry.getTitle().equals("") ?myEntry.getTitle():old.getTitle());
            old.setContent(myEntry.getContent()!=null && !myEntry.getContent().equals("") ?myEntry.getContent():old.getContent());
        }
        journalEntryService.saveEntry(old);

        return old;
    }




}
