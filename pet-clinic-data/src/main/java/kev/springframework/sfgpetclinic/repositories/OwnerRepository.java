package kev.springframework.sfgpetclinic.repositories;

import kev.springframework.sfgpetclinic.model.Owner;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.Set;

public interface OwnerRepository extends CrudRepository<Owner,Long> {

    Optional<Owner> findByLastName(String lastName);

    Set<Owner> findAllByLastNameLike(String anyString);
}
