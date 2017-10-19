package io.hyi.domain;

import lombok.Data;
import lombok.NonNull;

import javax.persistence.Embeddable;

@Data
@Embeddable
public class Coordinates {
    private Double lat;
    private Double lng;

    public Coordinates() {
    }

    public Coordinates(@NonNull Double lat, @NonNull Double lng) {
        this.lat = lat;
        this.lng = lng;
    }
}
