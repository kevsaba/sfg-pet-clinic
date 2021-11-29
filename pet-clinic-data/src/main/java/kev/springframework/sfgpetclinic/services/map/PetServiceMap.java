package kev.springframework.sfgpetclinic.services.map;

import kev.springframework.sfgpetclinic.model.Pet;
import kev.springframework.sfgpetclinic.services.PetService;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class PetServiceMap extends AbstractMapService<Pet, Long> implements PetService {

    @Override
    public Set<Pet> findAll() {
        return super.findAll();
    }

    @Override
    public void deleteById(Long id) {
        super.deleteById(id);
    }

    @Override
    public void delete(Pet t) {
        super.delete(t);
    }

    @Override
    public Pet save(Pet pet) {
        return super.save( pet);
    }

    @Override
    public Pet findById(Long id) {
        return super.findById(id);
    }
}
