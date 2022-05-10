package ru.javaops.topjava2.to;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.javaops.topjava2.model.BaseEntity;

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
