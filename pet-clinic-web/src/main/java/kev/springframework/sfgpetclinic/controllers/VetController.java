package kev.springframework.sfgpetclinic.controllers;

import kev.springframework.sfgpetclinic.model.Vet;
import kev.springframework.sfgpetclinic.services.VetService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Set;

@Controller
public class VetController {
    private final VetService vetService;

    public VetController(VetService vetService) {
        this.vetService = vetService;
    }

    @GetMapping({"/vets", "/vets/index", "/vets.html"})
    public String listVets(Model model) {
        model.addAttribute("listVets", vetService.findAll());
        return "vets/vetList";
    }

    @GetMapping("/api/vets")
    public @ResponseBody
    Set<Vet> getVetsJson() {
        return vetService.findAll();
    }

}
