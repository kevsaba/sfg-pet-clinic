package kev.springframework.sfgpetclinic.services.springdatajpa;

import kev.springframework.sfgpetclinic.model.Owner;
import kev.springframework.sfgpetclinic.repositories.OwnerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OwnerSDJpaServiceTest {

    @Mock
    OwnerRepository ownerRepository;

    @InjectMocks
    OwnerSDJpaService service;

    Owner owner1;

    @BeforeEach
    void setUp() {
        owner1 = new Owner();
        owner1.setId(1L);
        owner1.setLastName("saba");
    }

    @Test
    void findAll() {
        final Set<Owner> owners = new HashSet<>();
        Owner owner2 = Owner.builder().build();
        owner2.setId(2L);
        owners.add(owner1);
        owners.add(owner2);
        when(ownerRepository.findAll()).thenReturn(owners);

        var returned = service.findAll();
        assertEquals(returned.size(), 2);

    }

    @Test
    void findById() {
        when(ownerRepository.findById(any())).thenReturn(Optional.of(owner1));
        var owner = service.findById(1L);
        assertEquals(owner1, owner);
    }

    @Test
    void findByIdNull() {
        when(ownerRepository.findById(anyLong())).thenReturn(Optional.empty());
        var owner = service.findById(1L);
        assertNull(owner);
    }

    @Test
    void save() {
        Owner ownerToSave = Owner.builder().build();
        when(ownerRepository.save(any())).thenReturn(owner1);

        var owner = service.save(ownerToSave);
        assertEquals(owner1, owner);
        verify(ownerRepository).save(any());
    }

    @Test
    void delete() {
        service.delete(owner1);
        verify(ownerRepository,times(1)).delete(any());
    }

    @Test
    void deleteById() {
        service.deleteById(owner1.getId());
        verify(ownerRepository).deleteById(anyLong());
    }

    @Test
    void findByLastName() {
        //given
        when(ownerRepository.findByLastName(any())).thenReturn(java.util.Optional.ofNullable(owner1));
        //when
        var owner = service.findByLastName("saba");
        //then
        assertEquals(owner1, owner);
        verify(ownerRepository).findByLastName(any());
    }
}