package de.osp.springbootworkshop.application.rest;

import de.osp.springbootworkshop.domain.model.Pet;
import de.osp.springbootworkshop.service.PetShopService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

/**
 * @author Denny
 */
@RestController
@RequestMapping("/petshop/pets")
public class PetShopRestController {
	
    private PetShopService petShopService;

    
    @Autowired public PetShopRestController(PetShopService petShopService) {
		this.petShopService = petShopService;
	}

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<Pet> listPets() {
        return this.petShopService.getList();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Pet createPet(@RequestBody @Validated @NotNull final Pet pet) {
    	pet.setName(pet.getName().toLowerCase().trim());
       return this.petShopService.createPet(pet);
    }

    @DeleteMapping("/{name}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePet(@PathVariable @Validated @NotNull String name) {
    	name = name.toLowerCase().trim();
    	this.petShopService.deletePet(name);
    			
    }
}
