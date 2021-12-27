package kev.springframework.sfgpetclinic.controllers;

import kev.springframework.sfgpetclinic.model.Owner;
import kev.springframework.sfgpetclinic.services.OwnerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RequestMapping("/owners")
@Controller
public class OwnerController {

    private final OwnerService ownerService;

    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    //its to have a control over how the "magic" its done about the web data bind into java objects (deserialization)
    //here we are disallowing id because we are doing defensing coding, so the web forms for example can not address or manipulate
    //the id property of objects stored in the database, to avoid people to hack the db
    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

    @GetMapping("/{id}")
    public ModelAndView findOwners(@PathVariable Long id) {
        ModelAndView mav = new ModelAndView("owner/ownerDetails");
        mav.addObject(ownerService.findById(id));
        return mav;
    }

    @GetMapping("/form")
    public String findOwnersForm(Model model) {
        model.addAttribute("owner", Owner.builder().build());
        return "owner/findOwners";
    }

    @GetMapping("/find")
    public String processFindForm(Owner owner, BindingResult bindingResult, Model model) {
        if (owner.getLastName() == null) {
            owner.setLastName("");
        }
        var owners = ownerService.findAllByLastName("%" + owner.getLastName() + "%"); //to do the like
        if (owners.isEmpty()) {
            //no owners found
            bindingResult.rejectValue("lastName", "notFound", "notFound");
            return "owner/findOwners";
        } else if (owners.size() == 1) {
            //only 1 found so return details directly
            var foundOwner = owners.stream().findFirst().get();
            return "redirect:/owners/" + foundOwner.getId();
        } else {
            //multiple owners found so return table with list
            model.addAttribute("listOwners", owners);
            model.addAttribute("totalPages", 1);
            model.addAttribute("currentPage", 1);
            return "owner/ownersList";
        }
    }

    @GetMapping("/new")
    public String initCreationForm(Model model) {
        model.addAttribute("owner", Owner.builder().build());
        return "owner/createOrUpdateOwnerForm";
    }

    @PostMapping("/new")
    public String postCreationForm(Owner owner, BindingResult result) {
        if (result.hasErrors()) {
            return "owner/createOrUpdateOwnerForm";
        }
        var savedOwner = ownerService.save(owner);
        return "redirect:/owners/" + savedOwner.getId();
    }

    @GetMapping("/{id}/edit")
    public String initUpdateForm(@PathVariable Long id, Model model) {
        model.addAttribute("owner", ownerService.findById(id));
        return "owner/createOrUpdateOwnerForm";
    }

    @PostMapping("/{id}/edit")
    public String postUpdateForm(@PathVariable Long id, Owner owner, BindingResult result) {
        if (result.hasErrors()) {
            return "owner/createOrUpdateOwnerForm";
        }
        owner.setId(id);
        var savedOwner = ownerService.save(owner);
        return "redirect:/owners/" + savedOwner.getId();
    }
}
