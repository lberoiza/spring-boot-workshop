package de.osp.springbootworkshop.domain.model;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
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
@Entity
@Table(name = "pets")
@Validated
public class Pet {
    @Id
    @NotNull
    @NotEmpty
    private String name;

    @ManyToOne
    @JoinColumn(name = "type", nullable = false)
    @NotNull
    private PetType type;

    @Column(name = "birth_date", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull
    private LocalDate birthDay;

    @Column(name ="price", nullable = false, columnDefinition = "DECIMAL(6,2)")
    @NotNull
    @Digits(integer = 6, fraction = 2)
    @DecimalMin("0.00")
    private BigDecimal price;

    public Pet() {
    }

    public Pet(PetType type,
               String name,
               LocalDate birthDay,
               BigDecimal price) {
        this.type = type;
        this.name = name;
        this.birthDay = birthDay;
        this.price = price;
    }

    public static Builder builder() {
        return new Builder();
    }

    public PetType getType() {
        return type;
    }

    public void setType(PetType type) {
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

    public static final class Builder {
        private PetType type;
        private String name;
        private LocalDate birthDay;
        private BigDecimal price;

        private Builder() {
        }

        public Builder type(PetType type) {
            this.type = type;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder birthDay(LocalDate birthDay) {
            this.birthDay = birthDay;
            return this;
        }

        public Builder price(BigDecimal price) {
            this.price = price;
            return this;
        }

        public Pet build() {
            return new Pet(type, name, birthDay, price);
        }
    }
}
