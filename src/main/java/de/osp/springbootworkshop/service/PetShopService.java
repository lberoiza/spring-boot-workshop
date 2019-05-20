package de.osp.springbootworkshop.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import de.osp.springbootworkshop.application.rest.PetAlreadyExistsException;
import de.osp.springbootworkshop.application.rest.PetNotExistsException;
import de.osp.springbootworkshop.domain.model.Pet;


@Service
public class PetShopService {

	private final Map<String, Pet> pets;
	
	public PetShopService() {
        this.pets = new ConcurrentHashMap<>();

        Pet klaus = new Pet("Klaus", "Hamster", LocalDate.of(2019, 4, 13), BigDecimal.valueOf(20));
        Pet rubert = new Pet("Rubert","Hund", LocalDate.of(2018, 9, 18), BigDecimal.valueOf(550));
        Pet blacky = new Pet("Blacky","Katze", LocalDate.of(2018, 12, 12),  BigDecimal.valueOf(350));

        this.pets.put(klaus.getName().toLowerCase().trim(), klaus);
        this.pets.put(rubert.getName().toLowerCase().trim(), rubert);
        this.pets.put(blacky.getName().toLowerCase().trim(), blacky);
    }
	
	
	
	public Iterable<Pet> getList(){
		return this.pets.values();
	}
	
	public Pet createPet(Pet pet){
		
        if(pets.containsKey(pet.getName())) {
            throw new PetAlreadyExistsException("pet '" + pet.getName() + "' already exists");
        }

        pets.put(pet.getName(), pet);

       return pets.get(pet.getName());
		
	}
	
	public void deletePet(String name) {
        if(pets.get(name) == null) {
            throw new PetNotExistsException("pet '" + name + "' doesn't exists");
        }

        pets.remove(name);
		
	} 
	
	
}
