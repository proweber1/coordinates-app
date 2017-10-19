package io.hyi.event;

import io.hyi.domain.ShopRequestLog;
import io.hyi.repository.ShopRequestLogRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class LogRequestEventListener
        implements ApplicationListener<LogRequestEvent> {

    private final ShopRequestLogRepository shopRequestLogRepository;

    @Autowired
    public LogRequestEventListener(ShopRequestLogRepository shopRequestLogRepository) {
        this.shopRequestLogRepository = shopRequestLogRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onApplicationEvent(LogRequestEvent logRequestEvent) {
        log.info("Request log event received {}", logRequestEvent);

        ShopRequestLog requestLog = ShopRequestLog.of(
                logRequestEvent.getUser(), logRequestEvent.getFoundedShops());

        shopRequestLogRepository.save(requestLog);

        log.info("Request log event successful persist");
    }
}
