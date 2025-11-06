package com.faculdade.puzzles.dao;

import com.faculdade.puzzles.model.Puzzle;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PuzzleDAO {

    public List<Puzzle> getAll() {
        List<Puzzle> list = new ArrayList<>();
        String sql = "SELECT id, title, image_url, pieces, start_date, end_date, complete FROM puzzle ORDER BY created_at DESC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Puzzle p = new Puzzle();
                p.setId(rs.getInt("id"));
                p.setTitle(rs.getString("title"));
                p.setImageUrl(rs.getString("image_url"));
                p.setPieces(rs.getInt("pieces"));
                p.setStartDate(rs.getDate("start_date"));
                p.setEndDate(rs.getDate("end_date"));
                p.setComplete(rs.getBoolean("complete"));
                list.add(p);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }

    public Puzzle getById(int id) {
        String sql = "SELECT id, title, image_url, pieces, start_date, end_date, complete FROM puzzle WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Puzzle p = new Puzzle();
                    p.setId(rs.getInt("id"));
                    p.setTitle(rs.getString("title"));
                    p.setImageUrl(rs.getString("image_url"));
                    p.setPieces(rs.getInt("pieces"));
                    p.setStartDate(rs.getDate("start_date"));
                    p.setEndDate(rs.getDate("end_date"));
                    p.setComplete(rs.getBoolean("complete"));
                    return p;
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public boolean create(Puzzle p) {
        String sql = "INSERT INTO puzzle (title, image_url, pieces, start_date, end_date, complete) VALUES (?,?,?,?,?,?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, p.getTitle());
            ps.setString(2, p.getImageUrl());
            ps.setInt(3, p.getPieces());
            ps.setDate(4, p.getStartDate());
            ps.setDate(5, p.getEndDate());
            ps.setBoolean(6, p.isComplete());

            int affected = ps.executeUpdate();
            if (affected == 1) {
                try (ResultSet g = ps.getGeneratedKeys()) {
                    if (g.next()) p.setId(g.getInt(1));
                }
                return true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public boolean update(Puzzle p) {
        String sql = "UPDATE puzzle SET title=?, image_url=?, pieces=?, start_date=?, end_date=?, complete=? WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, p.getTitle());
            ps.setString(2, p.getImageUrl());
            ps.setInt(3, p.getPieces());
            ps.setDate(4, p.getStartDate());
            ps.setDate(5, p.getEndDate());
            ps.setBoolean(6, p.isComplete());
            ps.setInt(7, p.getId());

            return ps.executeUpdate() == 1;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM puzzle WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() == 1;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }
}
