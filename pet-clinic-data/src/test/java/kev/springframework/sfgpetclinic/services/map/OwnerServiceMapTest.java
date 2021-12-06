package kev.springframework.sfgpetclinic.services.map;

import kev.springframework.sfgpetclinic.model.Owner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class OwnerServiceMapTest {

    private OwnerServiceMap serviceMap;
    private final long ownerId = 1L;
    private final String lastName = "saba";

    @BeforeEach
    void setUp() {
        this.serviceMap = new OwnerServiceMap(new PetTypeServiceMap(), new PetServiceMap());
        Owner owner = new Owner();
        owner.setId(ownerId);
        owner.setLastName(lastName);
        serviceMap.save(owner);
    }

    @Test
    void findAll() {
        Set<Owner> owners = serviceMap.findAll();
        assertEquals(owners.size(), 1);
    }

    @Test
    void deleteById() {
        serviceMap.deleteById(ownerId);
        assertEquals(0,serviceMap.findAll().size());
    }

    @Test
    void delete() {
        serviceMap.delete(serviceMap.findById(ownerId));
        assertEquals(0,serviceMap.findAll().size());
    }

    @Test
    void save() {
        Owner owner = new Owner();
        final var saved = serviceMap.save(owner);
        assertEquals(saved.getId(), 2L);
    }

    @Test
    void saveExistentId() {
        Owner owner = new Owner();
        owner.setId(5L);
        final var saved = serviceMap.save(owner);
        assertEquals(saved.getId(), 5L);
    }

    @Test
    void findById() {
        Owner owner = serviceMap.findById(ownerId);
        assertEquals(owner.getId(), 1L);
    }

    @Test
    void findByLastName() {
        final var result = serviceMap.findByLastName(lastName);
        assertNotNull(result);
        assertEquals(result.getLastName(),"saba");
    }

    @Test
    void notFindByLastName() {
        final var result = serviceMap.findByLastName("sabas");
        assertNull(result);
    }
}