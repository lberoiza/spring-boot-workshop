package de.osp.springbootworkshop.domain.repository;

import de.osp.springbootworkshop.domain.model.Pet;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;


/**
 * @author Denny
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class PetRepositoryTest {
    @Autowired
    private PetRepository repository;

    @Test
    public void testFindByBirthDay() {
        List<Pet> pets = repository.findByBirthDay(LocalDate.of(2019, 4, 13));
        assertThat(pets)
                .isNotNull()
                .isNotEmpty()
                .allMatch(pet -> pet.getName().equals("Klaus"));
    }
}