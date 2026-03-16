package com.sammyCee.data.repositories;

import com.sammyCee.data.models.GatePass;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GatePassRepo extends MongoRepository<GatePass, String> {

    GatePass findByCode(String code);
}
