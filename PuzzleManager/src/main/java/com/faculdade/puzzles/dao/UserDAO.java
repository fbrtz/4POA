package com.faculdade.puzzles.dao;

import com.faculdade.puzzles.model.User;

import java.sql.*;

/**
 * DAO para users.
 */
public class UserDAO {

    public User getByUsername(String username) {
        String sql = "SELECT id, username, password, role, created_at FROM user_account WHERE username=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    User u = new User();
                    u.setId(rs.getInt("id"));
                    u.setUsername(rs.getString("username"));
                    u.setPassword(rs.getString("password"));
                    u.setRole(rs.getString("role"));
                    u.setCreatedAt(rs.getTimestamp("created_at"));
                    return u;
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public boolean create(User u) {
        String sql = "INSERT INTO user_account (username, password, role) VALUES (?,?,?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, u.getUsername());
            ps.setString(2, u.getPassword()); // em producao, armazene hash
            ps.setString(3, u.getRole());
            int affected = ps.executeUpdate();
            if (affected == 1) {
                try (ResultSet g = ps.getGeneratedKeys()) {
                    if (g.next()) u.setId(g.getInt(1));
                }
                return true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }
}
