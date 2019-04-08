package de.osp.springbootworkshop.domain.repository;

import de.osp.springbootworkshop.domain.model.Pet;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * @author Denny
 */
@Repository
public interface PetRepository extends CrudRepository<Pet, String> {
    List<Pet> findByBirthDay(LocalDate birthDay);
}
