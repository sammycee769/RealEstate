package data.repositories;

import data.models.Resident;

import java.util.List;

public interface ResidentRepo {

        List<Resident> findAll();
        Resident findById(String id);
        Resident findByPhoneNumber(String phoneNumber);
        Resident findByEmail(String email);
        void save(Resident resident);
        void delete(Resident resident);
        void deleteById(String id);
        void deleteByObject(Resident resident);
        void deleteAll();
        int getTotal();
}
