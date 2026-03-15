package data.repositories;

import data.models.Resident;
import java.util.ArrayList;
import java.util.List;

public class Residents implements ResidentRepo {

    private static List<Resident> residents = new ArrayList<>();
    private static int nextId = 1;

    @Override
    public List<Resident> findAll() {
        return new ArrayList<>(residents);
    }

    @Override
    public Resident findById(String id) {
        for (Resident resident : residents) {
            if (resident.getId().equals(id)) {
                return resident;
            }
        }
        return null;
    }

    @Override
    public Resident findByPhoneNumber(String phoneNumber) {
        for (Resident resident : residents) {
            if (resident.getPhoneNumber().equals(phoneNumber)) {
                return resident;
            }
        }
        return null;
    }

    @Override
    public Resident findByEmail(String email) {
        for (Resident resident : residents) {
            if (resident.getEmailAddress().equals(email)) {
                return resident;
            }
        }
        return null;
    }

    @Override
    public void save(Resident resident) {

        if (resident.getId() == null) {
            resident.setId(String.valueOf(nextId++));
            residents.add(resident);
            return;
        }

        for (Resident existing : residents) {
            if (existing.getId().equals(resident.getId())) {

                existing.setName(resident.getName());
                existing.setPhoneNumber(resident.getPhoneNumber());
                existing.setHouseAddress(resident.getHouseAddress());

                return;
            }
        }

        residents.add(resident);
    }

    @Override
    public void delete(Resident resident) {
        residents.remove(resident);
    }

    @Override
    public void deleteById(String id) {
        Resident resident = findById(id);
        if (resident != null) {
            residents.remove(resident);
        }
    }

    @Override
    public void deleteByObject(Resident resident) {
        delete(resident);
    }

    @Override
    public void deleteAll() {
        residents.clear();
    }

    @Override
    public int getTotal() {
        return residents.size();
    }

    public void clear() {
        residents.clear();
    }
}