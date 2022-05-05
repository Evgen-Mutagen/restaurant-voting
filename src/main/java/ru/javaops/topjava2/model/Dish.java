package ru.javaops.topjava2.model;

import lombok.*;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id")
    @NotNull
    @ToString.Exclude
    private Menu menu;

    public Dish(Integer id, String name, Integer price, Menu menu) {
        super(id, name);
        this.price = price;
        this.menu = menu;
    }
}