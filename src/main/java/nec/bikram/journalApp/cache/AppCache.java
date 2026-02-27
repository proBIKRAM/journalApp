package nec.bikram.journalApp.cache;

import jakarta.annotation.PostConstruct;
import nec.bikram.journalApp.entity.ConfigJournalAppEntity;
import nec.bikram.journalApp.repository.ConfigJournalAppRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AppCache {

    @Autowired
    private ConfigJournalAppRepository configJournalAppRepository;
    public Map <String, String> APP_CACHE= new HashMap<>();

    @PostConstruct
    public void init(){
        List<ConfigJournalAppEntity> all = configJournalAppRepository.findAll();
        for(ConfigJournalAppEntity entity: all){
            APP_CACHE.put(entity.getKey(),entity.getValue());
        }
    }

}
