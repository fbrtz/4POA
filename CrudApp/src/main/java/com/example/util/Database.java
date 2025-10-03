package com.example.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    // Ajuste os valores conforme seu ambiente
    private static final String URL = "jdbc:mysql://localhost:3306/clientesdb?useSSL=false&serverTimezone=UTC";
    private static final String USER = "appuser";
    private static final String PASSWORD = "senha123";

    static {
        try {
            // Carrega driver MySQL (necessário em algumas versões)
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Driver JDBC do MySQL não encontrado.", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
