package ru.javaops.topjava2.to;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
@ToString
public class DishTo extends NamedTo {
    @NotNull
    private LocalDate date;

    @Range(min = 1, max = 100000)
    private int price;

    public DishTo(Integer id, String name, LocalDate date, int price) {
        super(id, name);
        this.date = date;
        this.price = price;
    }
}
