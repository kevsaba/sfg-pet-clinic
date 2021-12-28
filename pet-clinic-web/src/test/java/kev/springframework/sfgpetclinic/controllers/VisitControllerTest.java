package kev.springframework.sfgpetclinic.controllers;

import kev.springframework.sfgpetclinic.model.Pet;
import kev.springframework.sfgpetclinic.model.Visit;
import kev.springframework.sfgpetclinic.services.PetService;
import kev.springframework.sfgpetclinic.services.VisitService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ExtendWith(MockitoExtension.class)
class VisitControllerTest {

    @Mock
    VisitService visitService;
    @Mock
    PetService petService;
    @InjectMocks
    VisitController controller;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        Pet pet = new Pet();
        pet.setId(1L);
        when(petService.findById(anyLong())).thenReturn(pet);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void initNewVisitForm() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/owners/*/pets/1/visits/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("pets/createOrUpdateVisitForm"));
    }

    @Test
    void postNewVisitForm() throws Exception {
        mockMvc.perform(post("/owners/1/pets/1/visits/new"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/owners/{ownerId}"));

        verify(visitService, times(1)).save(any(Visit.class));
    }
}