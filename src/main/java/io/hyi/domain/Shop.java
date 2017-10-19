package io.hyi.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "shops")
public class Shop {
    public static final String IMMEDIATE_SHOPS = "Shop.findAllImmediateShopsByCoordinates";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected long id;
    protected String name;
    @Embedded
    @JsonIgnore
    protected Coordinates coordinates;
    @Temporal(TemporalType.TIME)
    protected Date openTime;
    @Temporal(TemporalType.TIME)
    protected Date closeTime;
    @Transient
    private Double distanceInKm;
}
