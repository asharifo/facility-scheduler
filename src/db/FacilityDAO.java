package db;

import model.Facilities;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FacilityDAO {

    public static List<Facilities> loadAllFacilities() {
        List<Facilities> list = new ArrayList<>();

        String sql = "SELECT facility_id, name, is_outside, max_capacity FROM facilities ORDER BY facility_id";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new Facilities(
                        rs.getInt("facility_id"),
                        rs.getString("name"),
                        rs.getBoolean("is_outside"),
                        rs.getInt("max_capacity")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
}
