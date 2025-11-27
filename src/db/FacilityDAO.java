package db;

import model.Facilities;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FacilityDAO {

    public static List<Facilities> loadAllFacilities() {
        List<Facilities> list = new ArrayList<>();

        String sql = "SELECT facility_id, name, capacity, is_outside FROM facilities ORDER BY facility_id";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new Facilities(
                        rs.getInt("facility_id"),
                        rs.getString("name"),
                        rs.getBoolean("is_outside"),
                        rs.getInt("capacity")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
}
