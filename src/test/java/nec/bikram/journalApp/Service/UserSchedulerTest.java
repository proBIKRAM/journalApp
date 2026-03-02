package nec.bikram.journalApp.Service;

import nec.bikram.journalApp.scheduler.UserScheduler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserSchedulerTest {
    @Autowired
    UserScheduler userScheduler;

     @Test
    public void testScheduler(){
         userScheduler.fetchUsesAndSendSaMail();
     }
}
