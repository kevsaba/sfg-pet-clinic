package kev.springframework.sfgpetclinic.controllers;

import kev.springframework.sfgpetclinic.services.OwnerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@RequestMapping("/owners")
@Controller
public class OwnerController {

    private final OwnerService ownerService;

    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @RequestMapping({"", "/index", "/index.html"})
    public String listOwners(Model model) {
        model.addAttribute("owners", ownerService.findAll());
        return "owner/index";
    }

    @GetMapping("/{id}")
    public ModelAndView findOwners(@PathVariable Long id) {
        ModelAndView mav = new ModelAndView("owner/ownerDetails");
        mav.addObject(ownerService.findById(id));
        return mav;
    }

}
