package io.hyi.rest;

import io.hyi.domain.Shop;
import io.hyi.domain.User;
import io.hyi.event.LogRequestEvent;
import io.hyi.jdbc.NearestShopsProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/shops")
public class ShopsController {
    private final ApplicationEventPublisher eventPublisher;
    private final NearestShopsProvider nearestShopsProvider;

    @Autowired
    public ShopsController(
                           ApplicationEventPublisher eventPublisher,
                           NearestShopsProvider nearestShopsProvider) {

        this.eventPublisher = eventPublisher;
        this.nearestShopsProvider = nearestShopsProvider;
    }

    /**
     * This method catch user request for search shops.
     *
     * Important: this method send async event for logging all
     * user requests.
     *
     * @param user User pojo
     * @return List with founded shops
     */
    @GetMapping
    public List<Shop> findImmediateShops(@Valid User user) {
        List<Shop> nearestShops = nearestShopsProvider.findNearestShops(user.getCoordinates());

        eventPublisher.publishEvent(new LogRequestEvent(this, user, nearestShops));

        return nearestShops;
    }
}
