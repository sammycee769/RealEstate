package data.repositories;

import data.models.GatePass;

import java.util.List;

public interface GatePassRepo {
    List<GatePass> findAll();

    GatePass findById(String id);

    GatePass findByCode(String code);

    void save(GatePass pass);

    void delete(GatePass pass);

    void deleteById(String id);

    void deleteByObject(GatePass pass);

    void deleteAll();
}
