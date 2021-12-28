package kev.springframework.sfgpetclinic.services;

import kev.springframework.sfgpetclinic.model.PetType;

public interface PetTypeService extends CrudService<PetType, Long> {

    PetType findByName(String text);
}
