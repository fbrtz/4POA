package com.faculdade.puzzles.dao;

import com.faculdade.puzzles.model.Vote;
import java.sql.*;
import java.util.*;

/**
 * DAO de votos e sessão de votação.
 */
public class VoteDAO {

    public int createVotingSession(String title, List<Integer> puzzleIds) {
        String insertSession = "INSERT INTO voting_session (title, active) VALUES (?, TRUE)";
        String insertOption = "INSERT INTO vote_option (session_id, puzzle_id) VALUES (?, ?)";

        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false);
            try (PreparedStatement ps = conn.prepareStatement(insertSession, Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, title);
                ps.executeUpdate();

                int sessionId = -1;
                try (ResultSet g = ps.getGeneratedKeys()) {
                    if (g.next()) sessionId = g.getInt(1);
                }

                if (sessionId == -1) {
                    conn.rollback();
                    return -1;
                }

                try (PreparedStatement ps2 = conn.prepareStatement(insertOption)) {
                    for (Integer pid : puzzleIds) {
                        ps2.setInt(1, sessionId);
                        ps2.setInt(2, pid);
                        ps2.addBatch();
                    }
                    ps2.executeBatch();
                }

                conn.commit();
                return sessionId;

            } catch (SQLException ex) {
                conn.rollback();
                ex.printStackTrace();
            } finally {
                conn.setAutoCommit(true);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return -1;
    }

    public boolean closeVotingSession(int sessionId) {
        String sql = "UPDATE voting_session SET active=FALSE WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, sessionId);
            return ps.executeUpdate() == 1;

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public Integer getActiveSessionId() {
        String sql = "SELECT id FROM voting_session WHERE active=TRUE ORDER BY created_at DESC LIMIT 1";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) return rs.getInt("id");

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public List<Integer> getOptionsForSession(int sessionId) {
        List<Integer> res = new ArrayList<>();
        String sql = "SELECT puzzle_id FROM vote_option WHERE session_id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, sessionId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) res.add(rs.getInt("puzzle_id"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return res;
    }

    public boolean hasUserVoted(int sessionId, int userId) {
        String sql = "SELECT COUNT(*) AS cnt FROM vote WHERE session_id=? AND user_id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, sessionId);
            ps.setInt(2, userId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt("cnt") > 0;
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public boolean castVote(int sessionId, int puzzleId, int userId) {
        if (hasUserVoted(sessionId, userId)) return false;
        String sql = "INSERT INTO vote (session_id, puzzle_id, user_id) VALUES (?,?,?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, sessionId);
            ps.setInt(2, puzzleId);
            ps.setInt(3, userId);
            return ps.executeUpdate() == 1;

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public Map<Integer, Integer> getResults(int sessionId) {
        Map<Integer, Integer> map = new HashMap<>();
        String sql = "SELECT puzzle_id, COUNT(*) AS cnt FROM vote WHERE session_id=? GROUP BY puzzle_id";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, sessionId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    map.put(rs.getInt("puzzle_id"), rs.getInt("cnt"));
                }
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return map;
    }
}
