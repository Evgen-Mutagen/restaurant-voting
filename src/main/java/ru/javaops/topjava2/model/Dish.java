package ru.javaops.topjava2.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "dishes")
@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
public class Dish extends NamedEntity {

    @Column(name = "price", nullable = false)
    @Range(min = 1, max = 10000000)
    @NotNull
    private Integer price;

    public Dish(Integer id, String name, Integer price) {
        super(id, name);
        this.price = price;
    }
}