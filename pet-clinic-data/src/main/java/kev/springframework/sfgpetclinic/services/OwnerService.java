package kev.springframework.sfgpetclinic.services;

import kev.springframework.sfgpetclinic.model.Owner;

import java.util.Set;

public interface OwnerService extends CrudService<Owner, Long> {

    Owner findByLastName(String lastName);

    Set<Owner> findAllByLastName(String anyString);
}
