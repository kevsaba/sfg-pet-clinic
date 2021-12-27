package kev.springframework.sfgpetclinic.controllers;

import kev.springframework.sfgpetclinic.model.Owner;
import kev.springframework.sfgpetclinic.services.OwnerService;
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

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class OwnerControllerTest {

    @Mock
    OwnerService ownerService;
    @InjectMocks
    OwnerController controller;

    Set<Owner> owners;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        owners = new HashSet<>();
        Owner o1 = new Owner();
        o1.setId(1L);
        owners.add(o1);
        Owner o2 = new Owner();
        o1.setId(2L);
        owners.add(o2);

        //this is to test controllers without needing to bring the spring context which is heavy
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void displayOwner() throws Exception {
        Owner o = new Owner();
        o.setId(1L);
        when(ownerService.findById(anyLong())).thenReturn(o);
        mockMvc.perform(MockMvcRequestBuilders.get("/owners/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("owner/ownerDetails"))
                .andExpect(model().attribute("owner",hasProperty("id",is(1L))));
    }

    @Test
    void findOwners() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/owners/form"))
                .andExpect(status().isOk())
                .andExpect(view().name("owner/findOwners"));
    }

    @Test
    void findManyOwners() throws Exception {
        when(ownerService.findAllByLastName(anyString())).thenReturn(owners);
        mockMvc.perform(MockMvcRequestBuilders.get("/owners/find"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("listOwners",hasSize(2)))
                .andExpect(view().name("owner/ownersList"));
    }

    @Test
    void findOneOwner() throws Exception {
        when(ownerService.findAllByLastName(anyString())).thenReturn(Set.of(new Owner()));
        mockMvc.perform(MockMvcRequestBuilders.get("/owners/find"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void initCreationForm() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/owners/new"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("owner"))
                .andExpect(view().name("owner/createOrUpdateOwnerForm"));

        verifyNoInteractions(ownerService);
    }

    @Test
    void postOwnerForm() throws Exception {
        Owner owner = new Owner();
        owner.setId(1L);
        when(ownerService.save(ArgumentMatchers.any())).thenReturn(owner);
        mockMvc.perform(MockMvcRequestBuilders.post("/owners/new"))
                .andExpect(status().is3xxRedirection())
                .andExpect(model().attributeExists("owner"))
                .andExpect(view().name("redirect:/owners/1"));

        verify(ownerService,times(1)).save(any());
    }

    @Test
    void initUpdateOwnerForm() throws Exception {
        Owner owner = new Owner();
        owner.setId(1L);
        when(ownerService.findById(ArgumentMatchers.anyLong())).thenReturn(owner);

        mockMvc.perform(MockMvcRequestBuilders.get("/owners/1/edit"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("owner"))
                .andExpect(view().name("owner/createOrUpdateOwnerForm"));

    }

    @Test
    void postUpdateOwnerForm() throws Exception {
        Owner owner = new Owner();
        owner.setId(1L);
        when(ownerService.save(ArgumentMatchers.any())).thenReturn(owner);
        mockMvc.perform(MockMvcRequestBuilders.post("/owners/1/edit"))
                .andExpect(status().is3xxRedirection())
                .andExpect(model().attributeExists("owner"))
                .andExpect(view().name("redirect:/owners/1"));

        verify(ownerService,times(1)).save(any());
    }
}