package com.faculdade.puzzles.model;

import java.sql.Timestamp;

public class Vote {
    private int id;
    private int sessionId;
    private int puzzleId;
    private int userId;
    private Timestamp createdAt;

    public Vote(){}

    public Vote(int id, int sessionId, int puzzleId, int userId) {
        this.id = id; this.sessionId = sessionId; this.puzzleId = puzzleId; this.userId = userId;
    }

    // getters / setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getSessionId() { return sessionId; }
    public void setSessionId(int sessionId) { this.sessionId = sessionId; }
    public int getPuzzleId() { return puzzleId; }
    public void setPuzzleId(int puzzleId) { this.puzzleId = puzzleId; }
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
}
