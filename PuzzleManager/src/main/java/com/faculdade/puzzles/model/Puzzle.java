package com.faculdade.puzzles.model;

import java.sql.Date;

public class Puzzle {
    private int id;
    private String title;
    private String imageUrl;
    private int pieces;
    private Date startDate;
    private Date endDate;
    private boolean complete;

    public Puzzle() {}

    public Puzzle(int id, String title, String imageUrl, int pieces, Date startDate, Date endDate, boolean complete) {
        this.id = id;
        this.title = title;
        this.imageUrl = imageUrl;
        this.pieces = pieces;
        this.startDate = startDate;
        this.endDate = endDate;
        this.complete = complete;
    }

    // getters e setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public int getPieces() { return pieces; }
    public void setPieces(int pieces) { this.pieces = pieces; }
    public Date getStartDate() { return startDate; }
    public void setStartDate(Date startDate) { this.startDate = startDate; }
    public Date getEndDate() { return endDate; }
    public void setEndDate(Date endDate) { this.endDate = endDate; }
    public boolean isComplete() { return complete; }
    public void setComplete(boolean complete) { this.complete = complete; }

    public String toJson() {
        String img = imageUrl == null ? "" : imageUrl.replace("\"", "\\\"");
        String t = title == null ? "" : title.replace("\"", "\\\"");
        return String.format(
            "{\"id\":%d,\"title\":\"%s\",\"imageUrl\":\"%s\",\"pieces\":%d,\"startDate\":%s,\"endDate\":%s,\"complete\":%b}",
            id,
            t,
            img,
            pieces,
            startDate == null ? "null" : ("\"" + startDate.toString() + "\""),
            endDate == null ? "null" : ("\"" + endDate.toString() + "\""),
            complete
        );
    }
}
