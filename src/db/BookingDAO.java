package db;

import model.Booking;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookingDAO {

    public static int createBooking(Booking booking) {

        String sql = "INSERT INTO bookings (user_id, facility_id, day_of_week, period, person_count) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, booking.getUserId());
            ps.setInt(2, booking.getFacilityId());
            ps.setString(3, booking.getDayOfWeek());
            ps.setInt(4, booking.getPeriod());
            ps.setInt(5, booking.getPersonCount());

            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) return rs.getInt(1);

        } catch (SQLIntegrityConstraintViolationException dup) {
            return -2;  // conflict
        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1;
    }

    public static List<Booking> getBookingsForUser(int userId) {
        List<Booking> list = new ArrayList<>();

        String sql = "SELECT booking_id, user_id, facility_id, day_of_week, period, person_count " +
                "FROM bookings WHERE user_id = ? ORDER BY day_of_week, period";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(map(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public static void deleteBooking(int bookingId, int userId) {
        String sql = "DELETE FROM bookings WHERE booking_id = ? AND user_id = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, bookingId);
            ps.setInt(2, userId);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ======================
    // ADMIN SQL ADDITIONS
    // ======================

    public static void deleteBookingById(int bookingId) {
        String sql = "DELETE FROM bookings WHERE booking_id = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, bookingId);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<Object[]> getOutsideBookingsForDay(String day) {
        List<Object[]> rows = new ArrayList<>();

        String sql =
                "SELECT b.booking_id, u.username, f.name, b.day_of_week, b.period, b.person_count " +
                "FROM bookings b " +
                "JOIN facilities f ON b.facility_id = f.facility_id " +
                "JOIN users u ON b.user_id = u.user_id " +
                "WHERE f.is_outside = 1 AND b.day_of_week = ? " +
                "ORDER BY b.period";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, day);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                rows.add(new Object[]{
                        rs.getInt("booking_id"),
                        rs.getString("username"),
                        rs.getString("name"),
                        rs.getString("day_of_week"),
                        rs.getInt("period"),
                        rs.getInt("person_count")
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return rows;
    }

    public static List<Booking> getBookingsForFacilityAndDay(int facilityId, String day) {
        List<Booking> list = new ArrayList<>();

        String sql =
                "SELECT booking_id, user_id, facility_id, day_of_week, period, person_count " +
                "FROM bookings WHERE facility_id = ? AND day_of_week = ? ORDER BY period";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, facilityId);
            ps.setString(2, day);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) list.add(map(rs));

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // helper
    private static Booking map(ResultSet rs) throws Exception {
        return new Booking(
                rs.getInt("booking_id"),
                rs.getInt("user_id"),
                rs.getInt("facility_id"),
                rs.getString("day_of_week"),
                rs.getInt("period"),
                rs.getInt("person_count")
        );
    }
}
