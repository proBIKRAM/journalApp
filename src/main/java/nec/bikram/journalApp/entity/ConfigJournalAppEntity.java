package nec.bikram.journalApp.entity;

import com.mongodb.lang.NonNull;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "config_journal_app")
@Getter
@Setter
public class ConfigJournalAppEntity {

        private String key;
        private String value;


}
