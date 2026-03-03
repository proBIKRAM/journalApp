package nec.bikram.journalApp.Service;

import nec.bikram.journalApp.service.UserService;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Disabled
@SpringBootTest
public class UserServiceTests {

    @Autowired
    UserService userService;

    @Disabled
    @ParameterizedTest
//    @CsvSource({"admin",
//        "bikram",
//        "john_doe",})
    @ValueSource(strings = {"admin", "bikram", "john_doe"})
    public void Addtest(String username){
        assertEquals(4,2+2);
        assertNotNull(userService.getUserByUsername(username));
    }

}
