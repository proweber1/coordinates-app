package io.hyi.domain;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

@Data
@Embeddable
public class User {
    @NotEmpty
    @Column(name = "user_name")
    private String username;
    @NotNull
    @Range(min = -180, max = 180)
    private Double lat;
    @NotNull
    @Range(min = -90, max = 90)
    private Double lng;

    public Coordinates getCoordinates() {
        return new Coordinates(lat, lng);
    }
}
