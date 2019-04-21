package de.osp.springbootworkshop.domain.service;

import de.osp.springbootworkshop.domain.model.Pet;
import de.osp.springbootworkshop.domain.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Denny
 */
@Service
public class PetShopService {
    private final PetRepository petRepository;

    @Autowired
    public PetShopService(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    public Iterable<Pet> listPets() {
        return petRepository.findAll();
    }

    public Pet createPet(final Pet pet) {
        if(petRepository.existsById(pet.getName())) {
            throw new PetAlreadyExistsException("pet '" + pet.getName() + "' already exists");
        }

        return petRepository.save(pet);
    }

    public void deletePet(final String name) {
        if(!petRepository.existsById(name)) {
            throw new PetNotExistsException("pet '" + name + "' doesn't exists");
        }

        petRepository.deleteById(name);
    }
}
