package nec.bikram.journalApp.Service;

import nec.bikram.journalApp.service.EmailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmailServiceTest {
    @Autowired
    EmailService emailService;

    @Test
    void sendEmail(){
        emailService.sendEmail("lb9770099@gmail.com","Testing java mail sender","How are you doing? This is a test email from Java Mail Sender.");
    }

}
