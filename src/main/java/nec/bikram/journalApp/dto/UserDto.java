package nec.bikram.journalApp.dto;

import com.mongodb.lang.NonNull;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    @NotEmpty
    private String username;
    @NotEmpty
    private String password;
    private String email;
    private boolean sentimentAnalysis;
}
