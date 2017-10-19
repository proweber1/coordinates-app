package io.hyi.jdbc;

import com.google.common.collect.ImmutableMap;
import io.hyi.domain.Coordinates;
import io.hyi.domain.Shop;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class NearestShopsProviderImpl implements NearestShopsProvider {
    private static final String SQL_QUERY = "SELECT " +
            "  id, name, lat, lng, open_time, close_time, " +
            "  ceil((point(:lat, :lng) <@> point(lat, lng)) * 1.6) distance_in_meters " +
            "FROM shops " +
            "  WHERE (open_time, close_time) OVERLAPS (CURRENT_TIME, CURRENT_TIME) " +
            "  OR close_time IS NULL " +
            "ORDER BY distance_in_meters LIMIT 5";

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public NearestShopsProviderImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * This method execute sql query for search nearest shops by
     * current user coordinate
     *
     * @param userCoordinate Current user coordinate
     * @return List with nearest shops
     */
    @Override
    public List<Shop> findNearestShops(@NonNull Coordinates userCoordinate) {
        return jdbcTemplate.query(
                SQL_QUERY,
                ImmutableMap.of(
                        "lat", userCoordinate.getLat(),
                        "lng", userCoordinate.getLng()
                ),
                ShopRowMapper.newInstance());
    }

    /**
     * This class map result from query to shop entity {@link Shop}
     */
    private static class ShopRowMapper implements RowMapper<Shop> {

        /**
         * {@inheritDoc}
         */
        @Override
        public Shop mapRow(ResultSet resultSet, int i) throws SQLException {
            Shop shop = new Shop();

            shop.setId(resultSet.getLong("id"));
            shop.setName(resultSet.getString("name"));
            shop.setOpenTime(resultSet.getTimestamp("open_time"));
            shop.setCloseTime(resultSet.getTimestamp("close_time"));
            shop.setDistanceInKm(resultSet.getDouble("distance_in_meters"));
            shop.setCoordinates(makeCoordinatesObject(resultSet));

            return shop;
        }

        private Coordinates makeCoordinatesObject(ResultSet resultSet) throws SQLException {
            return new Coordinates(
                    resultSet.getDouble("lat"),
                    resultSet.getDouble("lng")
            );
        }

        static RowMapper<Shop> newInstance() {
            return new ShopRowMapper();
        }
    }
}
