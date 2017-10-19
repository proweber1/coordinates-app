package io.hyi.jdbc;

import io.hyi.domain.Coordinates;
import io.hyi.domain.Shop;

import java.util.List;

public interface NearestShopsProvider {
    List<Shop> findNearestShops(Coordinates userCoordinate);
}
