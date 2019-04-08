package de.osp.springbootworkshop.domain.model;

import org.springframework.validation.annotation.Validated;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * @author denny pazak
 */
@Entity
@Table(name = "pet_types")
@Validated
public class PetType {
    @Id
    @NotNull
    @NotEmpty
    private String name;

    public PetType() {
    }

    public PetType(String name) {
        this.name = name;
    }

    public static PetType of(String name) {
        return new PetType(name);
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PetType petType = (PetType) o;
        return Objects.equals(name, petType.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
