package kev.springframework.sfgpetclinic.repositories;

import kev.springframework.sfgpetclinic.model.PetType;
import org.springframework.data.repository.CrudRepository;

public interface PetTypeRepository extends CrudRepository<PetType, Long> {

    PetType findByName(String name);
}
