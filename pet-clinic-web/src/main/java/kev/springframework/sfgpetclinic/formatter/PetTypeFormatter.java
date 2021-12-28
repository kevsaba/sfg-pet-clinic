package kev.springframework.sfgpetclinic.formatter;

import kev.springframework.sfgpetclinic.model.PetType;
import kev.springframework.sfgpetclinic.services.PetTypeService;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class PetTypeFormatter implements Formatter<PetType> {

    private final PetTypeService petTypeService;

    public PetTypeFormatter(PetTypeService petTypeService) {
        this.petTypeService = petTypeService;
    }

    @Override
    public PetType parse(String text, Locale locale) {
        return petTypeService.findByName(text);
    }

    @Override
    public String print(PetType petType, Locale locale) {
        return petType.getName();
    }
}
