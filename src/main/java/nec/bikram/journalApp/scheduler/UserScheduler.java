package nec.bikram.journalApp.scheduler;

import nec.bikram.journalApp.entity.JournalEntry;
import nec.bikram.journalApp.entity.User;
import nec.bikram.journalApp.repository.UserRepoImpl;
import nec.bikram.journalApp.service.EmailService;
import nec.bikram.journalApp.service.SentimentAnalysisService;
import nec.bikram.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserScheduler {

    @Autowired
    private UserRepoImpl userRepoImpl;

    @Autowired
    private EmailService emailService;

    @Autowired
    private SentimentAnalysisService sentimentsAnalysisService;

    public void fetchUsesAndSendSA(){

        List<User> users = userRepoImpl.getUsersForSA();
        for(User user : users){
            List<JournalEntry> entries = user.getJournalEntries();
            List<String> filteredEntries = entries.stream().filter(x->x.getDate().isAfter(LocalDateTime.now().minus(7, ChronoUnit.DAYS))).map(x->x.getContent()).collect(Collectors.toList());
            String entry = String.join(" ",filteredEntries);
            String sentiment = sentimentsAnalysisService.getSentiment(entry);
            emailService.sendEmail(user.getEmail(),"sentiment for last 7 days", sentiment);
        }


    }


}
