package de.osp.springbootworkshop.domain.model;

import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;
import java.util.StringJoiner;

/**
 * @author Denny
 */
@Validated
public class Pet {
    @NotNull
    @NotEmpty
    private String name;

    @NotNull
    @NotEmpty
    private String type;

    @NotNull
    private LocalDate birthDay;

    @NotNull
    @Digits(integer = 6, fraction = 2)
    @DecimalMin("0.00")
    private BigDecimal price;

    public Pet(String name,
               String type,
               LocalDate birthDay,
               BigDecimal price) {
        this.type = type;
        this.name = name;
        this.birthDay = birthDay;
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(LocalDate birthDay) {
        this.birthDay = birthDay;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Pet pet = (Pet) o;
        return Objects.equals(name, pet.name) && Objects.equals(type, pet.type) && Objects.equals(birthDay, pet.birthDay) && Objects.equals(price, pet.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, type, birthDay, price);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Pet.class.getSimpleName() + "[", "]").add("name='" + name + "'")
                .add("type='" + type + "'")
                .add("birthDay=" + birthDay)
                .add("price=" + price)
                .toString();
    }
}
