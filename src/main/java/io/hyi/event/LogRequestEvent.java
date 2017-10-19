package io.hyi.event;

import io.hyi.domain.Shop;
import io.hyi.domain.User;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import org.springframework.context.ApplicationEvent;

import java.util.List;

@Getter
@ToString
@EqualsAndHashCode(callSuper = true)
public class LogRequestEvent extends ApplicationEvent {
    private final User user;
    private final List<Shop> foundedShops;

    /**
     * {@inheritDoc}
     *
     * @param user         User what send search request
     * @param foundedShops List of shops ...
     */
    public LogRequestEvent(
            @NonNull Object source,
            @NonNull User user,
            @NonNull List<Shop> foundedShops) {
        super(source);

        this.user = user;
        this.foundedShops = foundedShops;
    }
}
