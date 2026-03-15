package data.repositories;

import data.models.GatePass;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GatePassesTest {
    private GatePasses gatePasses;

    @BeforeEach
    void setUp() {
        gatePasses = new GatePasses();
    }
    @Test
    void testRepositoryIsInitiallyEmpty_I_save_1_Repository_Becomes1() {
        int total = gatePasses.getTotal();
        assertEquals(0, total);

        GatePass gatePass = new GatePass();
        gatePasses.save(gatePass);

        total = gatePasses.getTotal();
        assertEquals(1, total);
    }
    @Test
    void testThatWhenGatePassIsSaved_AnID_IsAssignedToIt() {
        int total = gatePasses.getTotal();
        assertEquals(0, total);

        GatePass gatePass = new GatePass();
        gatePasses.save(gatePass);

        assertEquals("1", gatePass.getId());
        total = gatePasses.getTotal();
        assertEquals(1, total);
    }

    @Test
    void testThatISaveMultiplePasses_IDsAreIncremented() {
        int total = gatePasses.getTotal();
        assertEquals(0, total);

        GatePass gatePass = new GatePass();
        gatePasses.save(gatePass);

        GatePass gatePass2 = new GatePass();
        gatePasses.save(gatePass2);

        assertEquals("1", gatePass.getId());
        assertEquals("2",gatePass2.getId());
        total = gatePasses.getTotal();
        assertEquals(2, total);
    }

    @Test
    void testThatISaveAGatePass_ICanFindItWithTheID() {
        int total = gatePasses.getTotal();
        assertEquals(0, total);

        GatePass gatePass = new GatePass();
        gatePasses.save(gatePass);

        GatePass foundGatePass = gatePasses.findById("1");
        assertEquals(gatePass, foundGatePass);
        assertEquals("1",foundGatePass.getId());
        total = gatePasses.getTotal();
        assertEquals(1, total);

    }

    @Test
    void testFindById_ReturnsNullIfNotFound() {
        int total = gatePasses.getTotal();
        assertEquals(0, total);

        GatePass foundGatePass = gatePasses.findById("100");
        assertNull(foundGatePass);
        total = gatePasses.getTotal();

        assertEquals(0, total);
    }

    @Test
    void  testFindAll_ReturnsAllGatePasses() {
        int total = gatePasses.getTotal();
        assertEquals(0, total);

        GatePass first = new GatePass();
        GatePass second = new GatePass();

        gatePasses.save(first);
        gatePasses.save(second);

        List<GatePass> all = gatePasses.findAll();
        assertEquals(2, all.size());

        total = gatePasses.getTotal();
        assertEquals(2, total);
    }


    @Test
    void testDelete_RemovesGatePass() {
        int total = gatePasses.getTotal();
        assertEquals(0, total);

        GatePass gatePass = new GatePass();
        gatePasses.save(gatePass);

        gatePasses.delete(gatePass);
        total= gatePasses.getTotal();
        assertEquals(0,total );
    }

    @Test
    void testDeleteById_RemovesCorrectGatePass() {
        int total = gatePasses.getTotal();
        assertEquals(0, total);

        GatePass gatePass = new GatePass();
        gatePasses.save(gatePass);

        gatePasses.deleteById("1");

        total = gatePasses.getTotal();
        assertEquals(0, total);
        assertNull(gatePasses.findById("1"));

    }

    @Test
    public void testDeleteByObject_RemovesGatePass() {
        int total = gatePasses.getTotal();
        assertEquals(0, total);

        GatePass gatePass = new GatePass();
        gatePasses.save(gatePass);

        gatePasses.deleteByObject(gatePass);

        total= gatePasses.getTotal();
        assertEquals(0, total);
    }
    @Test
    public void testDeleteAll_ClearsRepository() {
        int total = gatePasses.getTotal();
        assertEquals(0, total);

        gatePasses.save(new GatePass());
        gatePasses.save(new GatePass());

        gatePasses.deleteAll();

        total = gatePasses.getTotal();
        assertEquals(0, total);
    }

//    @Test
//    public void testSave_DoesNotDuplicateExistingObject() {
//        int total = gatePasses.getTotal();
//        assertEquals(0, total);
//
//        GatePass gatePass = new GatePass();
//        gatePasses.save(gatePass);
//        gatePasses.save(gatePass);
//
//        total = gatePasses.getTotal();
//        assertEquals(1, total);
//    }
//    @Test
//    public void testSave_DoesNotAllowTwoGatePassesWithSameId() {
//        int total = gatePasses.getTotal();
//        assertEquals(0, total);
//
//        GatePass gatePass = new GatePass();
//        gatePasses.save(gatePass);
//
//        GatePass gatePass2 = new GatePass();
//        gatePass2.setId("1");
//        gatePasses.save(gatePass2);
//
//        total = gatePasses.getTotal();
//        assertEquals(1, total);
//    }


}