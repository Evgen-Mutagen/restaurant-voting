package ru.github.evgen.votingsystem.to;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.github.evgen.votingsystem.model.BaseEntity;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDate;

@Getter
@Setter
@ToString
public class MenuTo extends BaseEntity {
    @NotNull
    private LocalDate date;

    @PositiveOrZero
    private int restaurantId;
    @PositiveOrZero
    private int dishId;

    public MenuTo(Integer id, LocalDate date, int restaurantId, int dishId) {
        super(id);
        this.date = date;
        this.restaurantId = restaurantId;
        this.dishId = dishId;
    }
}
