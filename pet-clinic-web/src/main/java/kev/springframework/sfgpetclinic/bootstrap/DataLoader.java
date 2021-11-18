package kev.springframework.sfgpetclinic.bootstrap;

import kev.springframework.sfgpetclinic.model.Owner;
import kev.springframework.sfgpetclinic.model.Vet;
import kev.springframework.sfgpetclinic.service.OwnerService;
import kev.springframework.sfgpetclinic.service.VetService;
import kev.springframework.sfgpetclinic.service.map.OwnerServiceMap;
import kev.springframework.sfgpetclinic.service.map.VetServiceMap;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component//CommandLineRunner Spring boot to initialize data when all context is ready is called
public class DataLoader implements CommandLineRunner {

    private final OwnerService ownerService;
    private final VetService vetService;

    public DataLoader(OwnerServiceMap ownerService, VetServiceMap vetService) {
        this.ownerService = ownerService;
        this.vetService = vetService;
    }

    @Override
    public void run(String... args) throws Exception {
        Owner owner1 = new Owner();
        owner1.setFirstName("kev");
        owner1.setLastName("saba");

        ownerService.save(owner1);

        Owner owner2 = new Owner();
        owner2.setFirstName("julie");
        owner2.setLastName("pozun");

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
