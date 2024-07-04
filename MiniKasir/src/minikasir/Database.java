/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package minikasir;

/**
 *
 * @author waski
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Database {
    private static final String URL = "jdbc:mysql://localhost:3306/kasir?zeroDateTimeBehavior=CONVERT_TO_NULL";
    private static final String USER = "admin"; // Sesuaikan dengan username database Anda
    private static final String PASSWORD = "admin"; // Sesuaikan dengan password database Anda

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error connecting to the database", e);
        }
    }
}
