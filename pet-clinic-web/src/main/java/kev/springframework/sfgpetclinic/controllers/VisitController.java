package kev.springframework.sfgpetclinic.controllers;

import kev.springframework.sfgpetclinic.model.Pet;
import kev.springframework.sfgpetclinic.model.Visit;
import kev.springframework.sfgpetclinic.services.PetService;
import kev.springframework.sfgpetclinic.services.VisitService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class VisitController {

    private final VisitService visitService;
    private final PetService petService;

    public VisitController(VisitService visitService, PetService petService) {
        this.visitService = visitService;
        this.petService = petService;
    }

    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder){
        dataBinder.setDisallowedFields("id");
    }

    @ModelAttribute("visit")
    public Visit loadPetWithVisit(@PathVariable("petId") Long id, Model model){
        Pet pet = petService.findById(id);
        model.addAttribute("pet", pet);
        Visit visit = new Visit();
        pet.getVisits().add(visit);
        visit.setPet(pet);
        return visit;
    }

    //loadPetWithVisit will be call first by spring MVC and it will load the visit
    @GetMapping("/owners/*/pets/{petId}/visits/new")
    public String initNewVisitForm(@PathVariable Long petId){
        return "pets/createOrUpdateVisitForm";
    }

    @PostMapping("/owners/{ownerId}/pets/{petId}/visits/new")
    public String postNewVisitForm(@Valid Visit visit, BindingResult result){
        if (result.hasErrors()){
            return "pets/createOrUpdateVisitForm";
        }
        visitService.save(visit);
        return "redirect:/owners/{ownerId}";
    }
}
