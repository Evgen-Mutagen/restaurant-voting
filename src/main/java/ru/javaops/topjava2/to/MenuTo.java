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
public class MenuTo extends NamedTo {
    @NotNull
    private LocalDate date;

    @Range(min = 1, max = 1000000)
    private int restaurantId;

    public MenuTo(Integer id, String name, LocalDate date, int restaurantId) {
        super(id, name);
        this.date = date;
        this.restaurantId = restaurantId;
    }
}
