package com.example.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class TestConnection {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/clientesdb?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
        String user = "appuser";     // use o usuário que você criou
        String password = "senha123"; // sua senha

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println("✅ Conexão bem-sucedida!");
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}