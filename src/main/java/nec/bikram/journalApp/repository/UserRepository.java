package nec.bikram.journalApp.repository;

import nec.bikram.journalApp.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

@Component
public interface UserRepository extends MongoRepository<User,ObjectId>{
    User findByUsername(String username);

}
