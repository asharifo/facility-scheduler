package db;

import model.User;
import util.PasswordUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    public static int createUser(String username, String plainPassword) {

        String salt = PasswordUtil.generateSalt();
        String hashed = PasswordUtil.hashPassword(plainPassword, salt);

        String sql = "INSERT INTO users (username, password_hash, salt) VALUES (?, ?, ?)";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, username);
            ps.setString(2, hashed);
            ps.setString(3, salt);

            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) return rs.getInt(1);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1;
    }

    public static User findByUsernameAndPassword(String username, String passwordEntered) {

        String sql = "SELECT user_id, password_hash, salt FROM users WHERE username = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) return null;

            String storedHash = rs.getString("password_hash");
            String storedSalt = rs.getString("salt");

            String enteredHash = PasswordUtil.hashPassword(passwordEntered, storedSalt);

            if (storedHash.equals(enteredHash)) {
                return new User(rs.getInt("user_id"), username);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static boolean usernameExists(String username) {
        String sql = "SELECT COUNT(*) FROM users WHERE username = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            return rs.next() && rs.getInt(1) > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // ===== ADMIN METHODS =====

    public static List<User> getAllUsers() {
        List<User> list = new ArrayList<>();

        String sql = "SELECT user_id, username FROM users ORDER BY user_id";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new User(rs.getInt("user_id"), rs.getString("username")));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public static User findById(int id) {

        String sql = "SELECT user_id, username FROM users WHERE user_id = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) return new User(id, rs.getString("username"));

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static User findByUsername(String username) {

        String sql = "SELECT user_id, username FROM users WHERE username = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) return new User(rs.getInt("user_id"), username);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
