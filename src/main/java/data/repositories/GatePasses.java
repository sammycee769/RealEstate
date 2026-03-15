package data.repositories;
import data.models.GatePass;

import java.util.ArrayList;
import java.util.List;

public class GatePasses implements GatePassRepo {
    private static List<GatePass> gatePasses = new ArrayList<>();
    private static int nextId = 1;

    @Override
    public List<GatePass> findAll() {
        return new ArrayList<>(gatePasses);
    }

    @Override
    public GatePass findById(String id) {
        for (GatePass gatePass : gatePasses) {
            if (gatePass.getId().equals(id)) {
                return gatePass;
            }
        }
        return null;
    }

    @Override
    public GatePass findByCode(String code) {
        for (GatePass gatePass : gatePasses) {
            if (code.equals(gatePass.getCode())) {
                return gatePass;
            }
        }
        return null;
    }

    @Override
    public void save(GatePass gatePass) {
        if (gatePass.getId() == null) {
            gatePass.setId(String.valueOf(nextId++));
            gatePasses.add(gatePass);
            return;
        }

        for (int count = 0; count < gatePasses.size(); count++) {
            if (gatePasses.get(count).getId().equals(gatePass.getId())) {
                gatePasses.set(count, gatePass);
                return;
            }
        }
    }
    @Override
    public void delete(GatePass gatePass) {
        gatePasses.remove(gatePass);
    }

    @Override
    public void deleteById(String id) {
        GatePass gatePass = findById(id);
        if (gatePass != null) {
            gatePasses.remove(gatePass);
        }
    }

    @Override
    public void deleteByObject(GatePass gatePass) {
        delete(gatePass);
    }

    @Override
    public void deleteAll() {
        gatePasses.clear();
    }

    public int getTotal() {
        return gatePasses.size();
    }

}