package nec.bikram.journalApp.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nec.bikram.journalApp.enums.Sentiment;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JournalDto {
    @NotEmpty
    private String title;

    private String content;

    private Sentiment sentiment;
}
