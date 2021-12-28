package kev.springframework.sfgpetclinic.controllers;

import kev.springframework.sfgpetclinic.model.Owner;
import kev.springframework.sfgpetclinic.model.Pet;
import kev.springframework.sfgpetclinic.model.PetType;
import kev.springframework.sfgpetclinic.services.OwnerService;
import kev.springframework.sfgpetclinic.services.PetService;
import kev.springframework.sfgpetclinic.services.PetTypeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RequestMapping("/owners/{ownerId}")
@Controller
public class PetController {

    private final PetTypeService petTypeService;
    private final PetService petService;
    private final OwnerService ownerService;

    public PetController(PetTypeService petTypeService, PetService petService, OwnerService ownerService) {
        this.petTypeService = petTypeService;
        this.petService = petService;
        this.ownerService = ownerService;
    }

    @InitBinder("owner")
    public void initOwnerBinder(WebDataBinder binder) {
        binder.setDisallowedFields("id");
    }

    @ModelAttribute("types")
    //so we will have for example in thymeleaf types list of pet types available to the view always for each model that is used in this controller
    public Collection<PetType> populatePetTypes() {
        return petTypeService.findAll();
    }

    @ModelAttribute("owner")
    public Owner findOwner(@PathVariable Long ownerId) {
        return ownerService.findById(ownerId);
    }

    @GetMapping("/pets/new")
    public String initCreationForm(Owner owner, Model model) {
        Pet pet = new Pet();
        owner.getPets().add(pet);
        pet.setOwner(owner);
        model.addAttribute("pet", pet);
        return "pets/createOrUpdatePetForm";
    }

    @PostMapping("/pets/new")
    public String postCreatePetForm(Owner owner, Pet pet, BindingResult result, @PathVariable Long ownerId) {
        if (!pet.getName().isEmpty() && pet.isNew() && owner.getPet(pet.getName(), true) != null) {
            result.rejectValue("name", "duplicate", "name already exists");
        }
        owner.getPets().add(pet);
        pet.setOwner(owner);
        petService.save(pet);
        return "redirect:/owners/" + ownerId;
    }

    @GetMapping("/pets/{petId}/edit")
    public String initUpdateForm(@PathVariable Long petId, Model model) {
        model.addAttribute("pet", petService.findById(petId));
        return "pets/createOrUpdatePetForm";
    }

    @PostMapping("/pets/{petId}/edit")
    public String postUpdatePetForm(Owner owner, Pet pet, BindingResult result, @PathVariable Long petId) {
        if (!pet.getName().isEmpty() && pet.isNew() && owner.getPet(pet.getName(), true) != null) {
            result.rejectValue("name", "duplicate", "name already exists");
        }
        pet.setOwner(owner);
        petService.save(pet);
        return "redirect:/owners/" + owner.getId();

    }

}
