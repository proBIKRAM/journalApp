package nec.bikram.journalApp.repository;

import nec.bikram.journalApp.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserRepoImpl {

    @Autowired
    private  MongoTemplate mongoTemplate;

    public List<User> getUsersForSA(){
        Query query = new Query();
        query.addCriteria(Criteria.where("email").regex("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,1}$"));
        query.addCriteria(Criteria.where("sentimentalAnalysis").is(true));
        return mongoTemplate.find(query, User.class);
    }


}
