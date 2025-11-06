package com.faculdade.puzzles.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Classe utilitária para conexão com o banco MySQL.
 */
public class DBConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/puzzle_db?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USER = "puzzleuser";
    private static final String PASSWORD = "PuzzlePass123!";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // garante que o driver seja carregado
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Driver MySQL não encontrado!", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
