package nec.bikram.journalApp.service;

import nec.bikram.journalApp.entity.JournalEntry;
import nec.bikram.journalApp.entity.User;
import nec.bikram.journalApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {


    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserService userService;


    @Transactional
    public void saveEntry(JournalEntry entry, String username){
        try {
            User user = userService.getUserByUsername(username);
            entry.setDate(LocalDateTime.now());
            JournalEntry saved = journalEntryRepository.save(entry);
            user.getJournalEntries().add(saved);
            userService.saveUser(user);
        }catch(Exception e){
            throw new RuntimeException("Error while saving Journal Entry.",e);
        }
    }
    public void saveEntry(JournalEntry entry){

        journalEntryRepository.save(entry);
    }
    public List<JournalEntry> getAll(){
        return journalEntryRepository.findAll();
    }
    public Optional<JournalEntry> findById(ObjectId id){
        return journalEntryRepository.findById(id);
    }

    @Transactional
    public boolean deleteById(ObjectId id, String username) {
        boolean removed = false;
        try {
            User user = userService.getUserByUsername(username);
            removed= user.getJournalEntries().removeIf(x -> x.getId().equals(id));
            if (removed) {
                userService.saveUser(user);
                journalEntryRepository.deleteById(id);
            }

        }catch(Exception e){
            System.out.println(e);
            throw new RuntimeException("Error while deleting Journal Entry.",e);
        }
        return removed;

    }
   /* public List<JournalEntry> findByUsername(String username){

    }

    */
//    public JournalEntry updateById(ObjectId id, JournalEntry entry){
//
//
//    }
}
