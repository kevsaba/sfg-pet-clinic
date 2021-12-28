package kev.springframework.sfgpetclinic.bootstrap;

import kev.springframework.sfgpetclinic.model.*;
import kev.springframework.sfgpetclinic.services.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.HashSet;


@Component//CommandLineRunner Spring boot to initialize data when all context is ready is called
public class DataLoader implements CommandLineRunner {

    private final OwnerService ownerService;
    private final VetService vetService;
    private final PetTypeService petTypeService;
    private final SpecialityService specialityService;
    private final VisitService visitService;

    public DataLoader(OwnerService ownerService, VetService vetService, PetTypeService petService, SpecialityService specialityService, VisitService visitService) {
        this.ownerService = ownerService;
        this.vetService = vetService;
        this.petTypeService = petService;
        this.specialityService = specialityService;
        this.visitService = visitService;
    }

    @Override
    public void run(String... args) {

        int count = petTypeService.findAll().size();
        if (count == 0) {
            loadData();
        }
    }

    private void loadData() {
        var dog = new PetType();
        dog.setName("Dog");
        var dogPetType = petTypeService.save(dog);

        var cat = new PetType();
        cat.setName("Cat");
        var catPetType = petTypeService.save(cat);


        Owner owner1  = Owner.builder().firstName("kev").lastName("saba").address("address1").city("buenos aires").telephone("12345678").pets(new HashSet<>()).build();
//        Owner owner1 = new Owner();
//        owner1.setFirstName("kev");
//        owner1.setLastName("saba");
//        owner1.setAddress("address1");
//        owner1.setCity("Buenos Aires");
//        owner1.setTelephone("12341234");

        var mydog = new Pet();
        mydog.setPetType(dogPetType);
        mydog.setBirthDate(LocalDate.now());
        mydog.setName("dogo");
        mydog.setOwner(owner1);
        owner1.getPets().add(mydog);

        ownerService.save(owner1);

        var catVisit = new Visit();
        catVisit.setPet(mydog);
        catVisit.setDate(LocalDate.now());
        catVisit.setDescription("ooops it was the dogo actually visited :D");

        visitService.save(catVisit);

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


        var radiology = new Speciality();
        radiology.setDescription("radiology");
        var savedRadiology = specialityService.save(radiology);
        var surgery = new Speciality();
        surgery.setDescription("surgery");
        var savedSurgery = specialityService.save(surgery);
        var dentestry = new Speciality();
        dentestry.setDescription("dentestry");
        var savedDentestry = specialityService.save(dentestry);

        var vet1 = new Vet();
        vet1.setFirstName("vet name");
        vet1.setLastName("vet last name");
        vet1.getSpecialities().add(savedRadiology);
        vet1.getSpecialities().add(savedSurgery);

        vetService.save(vet1);

        var vet2 = new Vet();
        vet2.setFirstName("vet 2");
        vet2.setLastName("vet 2 surname");
        vet2.getSpecialities().add(savedSurgery);
        vet2.getSpecialities().add(savedDentestry);

        vetService.save(vet2);

        System.out.println("VETS LOADEDDDDDD");
    }
}
