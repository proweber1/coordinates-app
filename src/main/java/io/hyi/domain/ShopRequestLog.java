package io.hyi.domain;

import lombok.Data;
import lombok.NonNull;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "search_requests_log")
public class ShopRequestLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Embedded
    private User requestOwner;
    @CreationTimestamp
    private Date searchDatetime;
    @ManyToMany
    @JoinTable(
            name = "search_requests_founded_shops",
            joinColumns = @JoinColumn(name = "search_request_id"),
            inverseJoinColumns = @JoinColumn(name = "shop_id")
    )
    private List<Shop> searchFoundedShops;

    public ShopRequestLog() {
    }

    private ShopRequestLog(User requestOwner, List<Shop> searchFoundedShops) {
        this.requestOwner = requestOwner;
        this.searchFoundedShops = searchFoundedShops;
    }

    public static ShopRequestLog of(@NonNull User requestOwner, @NonNull List<Shop> searchFoundedShops) {
        return new ShopRequestLog(requestOwner, searchFoundedShops);
    }
}
