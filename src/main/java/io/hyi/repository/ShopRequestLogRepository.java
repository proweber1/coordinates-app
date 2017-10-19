package io.hyi.repository;

import io.hyi.domain.ShopRequestLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopRequestLogRepository
        extends JpaRepository<ShopRequestLog, Long> {
}
