package kev.springframework.sfgpetclinic.controllers;

import kev.springframework.sfgpetclinic.model.Owner;
import kev.springframework.sfgpetclinic.model.Pet;
import kev.springframework.sfgpetclinic.model.PetType;
import kev.springframework.sfgpetclinic.services.OwnerService;
import kev.springframework.sfgpetclinic.services.PetService;
import kev.springframework.sfgpetclinic.services.PetTypeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class PetControllerTest {

    @Mock
    OwnerService ownerService;
    @Mock
    PetService petService;
    @Mock
    PetTypeService petTypeService;

    @InjectMocks
    PetController petController;

    MockMvc mockMvc;
    Owner owner;
    Set<PetType> petTypes;

    @BeforeEach
    void setUp() {
        owner = new Owner();
        owner.setId(1L);
        petTypes = new HashSet<>();
        var dog = new PetType();
        dog.setId(1L);
        dog.setName("dog");
        var cat = new PetType();
        cat.setId(2L);
        cat.setName("cat");
        petTypes.add(dog);
        petTypes.add(cat);
        given(ownerService.findById(1L)).willReturn(owner);
        mockMvc = MockMvcBuilders.standaloneSetup(petController).build();
    }


    @Test
    void initCreationForm() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/owners/1/pets/new"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("pet"))
                .andExpect(view().name("pets/createOrUpdatePetForm"));

    }

    @Test
    void postOwnerForm() throws Exception {
        when(petTypeService.findAll()).thenReturn(petTypes);
        mockMvc.perform(MockMvcRequestBuilders.post("/owners/1/pets/new").param("name","milo"))
                .andExpect(status().is3xxRedirection())
                .andExpect(model().attributeExists("owner"))
                .andExpect(view().name("redirect:/owners/1"));

        verify(petService, times(1)).save(any());
    }

    @Test
    void initUpdateOwnerForm() throws Exception {
        Pet pet = new Pet();
        pet.setId(1L);
        when(petService.findById(ArgumentMatchers.anyLong())).thenReturn(pet);
        when(petTypeService.findAll()).thenReturn(petTypes);

        mockMvc.perform(MockMvcRequestBuilders.get("/owners/1/pets/1/edit"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("pet"))
                .andExpect(view().name("pets/createOrUpdatePetForm"));

    }

    @Test
    void postUpdateOwnerForm() throws Exception {
        when(petTypeService.findAll()).thenReturn(petTypes);
        mockMvc.perform(MockMvcRequestBuilders.post("/owners/1/pets/1/edit").param("name","milo2").param("id","1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(model().attributeExists("owner"))
                .andExpect(view().name("redirect:/owners/1"));

        verify(petService, times(1)).save(any());
    }
}