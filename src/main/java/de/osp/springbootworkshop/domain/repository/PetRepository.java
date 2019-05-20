package de.osp.springbootworkshop.domain.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import de.osp.springbootworkshop.domain.model.Pet;

@Repository
public interface PetRepository extends CrudRepository<Pet, String>{


	
	
}
