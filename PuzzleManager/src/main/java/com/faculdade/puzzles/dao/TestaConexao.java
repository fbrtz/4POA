package com.faculdade.puzzles.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TestaConexao {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/puzzle_db?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
        String user = "puzzleuser";
        String password = "PuzzlePass123!";

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            System.out.println("✅ Conexão bem-sucedida!");
        } catch (SQLException e) {
            System.out.println("❌ Erro ao conectar:");
            e.printStackTrace();
        }
    }
}