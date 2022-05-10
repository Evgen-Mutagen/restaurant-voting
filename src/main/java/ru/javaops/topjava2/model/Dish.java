package ru.javaops.topjava2.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.PositiveOrZero;

@Entity
@Table(name = "dishes")
@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
public class Dish extends NamedEntity {

    @Column(name = "price", nullable = false)
    @PositiveOrZero
    private int price;

    public Dish(Integer id, String name, int price) {
        super(id, name);
        this.price = price;
    }
}