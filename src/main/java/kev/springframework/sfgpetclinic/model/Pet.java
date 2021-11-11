package kev.springframework.sfgpetclinic.model;

import java.time.LocalDate;

public class Pet {

    private PetType petType;
    private Owner owner;
    private LocalDate birdthDate;

    public PetType getPetType() {
        return petType;
    }

    public void setPetType(PetType petType) {
        this.petType = petType;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public LocalDate getBirdthDate() {
        return birdthDate;
    }

    public void setBirdthDate(LocalDate birdthDate) {
        this.birdthDate = birdthDate;
    }
}
