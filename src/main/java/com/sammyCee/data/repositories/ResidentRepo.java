package com.sammyCee.data.repositories;

import com.sammyCee.data.models.Resident;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResidentRepo extends MongoRepository<Resident, String> {

        Resident findByPhoneNumber(String phoneNumber);

        Resident findByEmail(String email);
}
