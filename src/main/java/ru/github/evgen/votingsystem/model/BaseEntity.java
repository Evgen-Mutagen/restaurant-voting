package ru.github.evgen.votingsystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.data.domain.Persistable;
import org.springframework.data.util.ProxyUtils;
import org.springframework.util.Assert;
import ru.github.evgen.votingsystem.HasId;

import javax.persistence.*;

@MappedSuperclass
//  https://stackoverflow.com/a/6084701/548473
@Access(AccessType.FIELD)
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(callSuper = true)
public abstract class BaseEntity implements Persistable<Integer>, HasId {
    public static final int START_SEQ = 100000;

    @Id
    @SequenceGenerator(name = "global_seq", sequenceName = "global_seq", allocationSize = 1, initialValue = START_SEQ)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "global_seq")
    @Schema(accessMode = Schema.AccessMode.READ_ONLY) // https://stackoverflow.com/a/28025008/548473
    protected Integer id;

    // doesn't work for hibernate lazy proxy
    public int id() {
        Assert.notNull(id, "Entity must have id");
        return id;
    }

    @JsonIgnore
    @Override
    public boolean isNew() {
        return id == null;
    }

    //    https://stackoverflow.com/questions/1638723
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || !getClass().equals(ProxyUtils.getUserClass(o))) {
            return false;
        }
        BaseEntity that = (BaseEntity) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id == null ? 0 : id;
    }
}