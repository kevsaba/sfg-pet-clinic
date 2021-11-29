package kev.springframework.sfgpetclinic.services.springdatajpa;

import kev.springframework.sfgpetclinic.model.Pet;
import kev.springframework.sfgpetclinic.repositories.PetRepository;
import kev.springframework.sfgpetclinic.services.PetService;
import org.springframework.context.annotation.Profile;

import javax.persistence.Entity;
import java.util.HashSet;
import java.util.Set;

@Entity
@Profile("springdatajpa")
public class PetSDJpaService implements PetService {

    private final PetRepository petRepository;

    public PetSDJpaService(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    @Override
    public Set<Pet> findAll() {
        final var pets = new HashSet<Pet>();
        petRepository.findAll().forEach(pets::add);
        return pets;
    }

    @Override
    public Pet findById(Long id) {
        return petRepository.findById(id).orElse(null);
    }

    @Override
    public Pet save(Pet pet) {
        return petRepository.save(pet);
    }

    @Override
    public void delete(Pet pet) {
        petRepository.delete(pet);
    }

    @Override
    public void deleteById(Long id) {
        petRepository.deleteById(id);
    }
}
