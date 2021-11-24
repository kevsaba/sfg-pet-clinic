package kev.springframework.sfgpetclinic.bootstrap;

import kev.springframework.sfgpetclinic.model.Owner;
import kev.springframework.sfgpetclinic.model.Pet;
import kev.springframework.sfgpetclinic.model.PetType;
import kev.springframework.sfgpetclinic.model.Vet;
import kev.springframework.sfgpetclinic.service.OwnerService;
import kev.springframework.sfgpetclinic.service.PetTypeService;
import kev.springframework.sfgpetclinic.service.VetService;
import kev.springframework.sfgpetclinic.service.map.OwnerServiceMap;
import kev.springframework.sfgpetclinic.service.map.VetServiceMap;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;


@Component//CommandLineRunner Spring boot to initialize data when all context is ready is called
public class DataLoader implements CommandLineRunner {

    private final OwnerService ownerService;
    private final VetService vetService;
    private final PetTypeService petService;

    public DataLoader(OwnerServiceMap ownerService, VetServiceMap vetService, PetTypeService petService) {
        this.ownerService = ownerService;
        this.vetService = vetService;
        this.petService = petService;
    }

    @Override
    public void run(String... args) throws Exception {
        var dog = new PetType();
        dog.setName("Dog");
        var dogPetType = petService.save(dog);

        var cat = new PetType();
        dog.setName("Cat");
        var catPetType = petService.save(cat);

        Owner owner1 = new Owner();
        owner1.setFirstName("kev");
        owner1.setLastName("saba");
        owner1.setAddress("address1");
        owner1.setCity("Buenos Aires");
        owner1.setTelephone("12341234");

        var mydog = new Pet();
        mydog.setPetType(dogPetType);
        mydog.setBirthDate(LocalDate.now());
        mydog.setName("dogo");
        mydog.setOwner(owner1);
        owner1.getPets().add(mydog);

        ownerService.save(owner1);

        Owner owner2 = new Owner();
        owner2.setFirstName("julie");
        owner2.setLastName("pozun");
        owner2.setAddress("address1");
        owner2.setCity("Buenos Aires");
        owner2.setTelephone("12341234");

        var mycat = new Pet();
        mycat.setPetType(catPetType);
        mycat.setBirthDate(LocalDate.now());
        mycat.setName("milo");
        mycat.setOwner(owner2);
        owner2.getPets().add(mycat);

        ownerService.save(owner2);
        System.out.println("2 OWNERS LOADED.....");



        var vet1 = new Vet();
        vet1.setFirstName("vet name");
        vet1.setLastName("vet last name");

        vetService.save(vet1);

        var vet2 = new Vet();
        vet2.setFirstName("vet 2");
        vet2.setLastName("vet 2 surname");

        vetService.save(vet2);

        System.out.println("VETS LOADEDDDDDD");

    }
}
