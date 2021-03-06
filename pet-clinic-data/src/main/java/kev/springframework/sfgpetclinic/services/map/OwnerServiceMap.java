package kev.springframework.sfgpetclinic.services.map;

import kev.springframework.sfgpetclinic.model.Owner;
import kev.springframework.sfgpetclinic.services.OwnerService;
import kev.springframework.sfgpetclinic.services.PetService;
import kev.springframework.sfgpetclinic.services.PetTypeService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Set;

@Service
@Profile({"default", "map"})
public class OwnerServiceMap extends AbstractMapService<Owner, Long> implements OwnerService {

    private final PetTypeService petTypeService;
    private final PetService petService;

    public OwnerServiceMap(PetTypeService petTypeService, PetService petService) {
        this.petTypeService = petTypeService;
        this.petService = petService;
    }

    @Override
    public Set<Owner> findAll() {
        return super.findAll();
    }

    @Override
    public void deleteById(Long id) {
        super.deleteById(id);
    }

    @Override
    public void delete(Owner t) {
        super.delete(t);
    }

    @Override
    public Owner save(Owner owner) {
        if (owner != null) {
            if (owner.getPets() != null) {
                owner.getPets().stream().filter(Objects::nonNull).forEach(p -> {
                    p.setPetType(petTypeService.save(p.getPetType()));
                    if (p.getId() == null) {
                        var savedPet = petService.save(p);
                        savedPet.setId(savedPet.getId());
                    }
                });
            }
            return super.save(owner);
        } else {
            return null;
        }
    }

    @Override
    public Owner findById(Long id) {
        return super.findById(id);
    }

    @Override
    public Owner findByLastName(String lastName) {
        return findAll().stream().filter(o->o.getLastName().equalsIgnoreCase(lastName)).findFirst().orElse(null);
    }

    @Override
    public Set<Owner> findAllByLastName(String anyString) {
        return null;
    }
}
