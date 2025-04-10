package com.loscuchurrumines.config;

import io.github.cdimascio.dotenv.Dotenv;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;



public class NeonConnection {

    private static final Dotenv dotenv = Dotenv.load();
    private static final String DATABASE_URL = dotenv.get("DATABASE_URL");
    private static final String DATABASE_USER = dotenv.get("DATABASE_USER");
    private static final String DATABASE_PASSWORD = dotenv.get(
        "DATABASE_PASSWORD"
    );

    public NeonConnection() {
        throw new IllegalStateException("Utility class");
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(
                DATABASE_URL,
                DATABASE_USER,
                DATABASE_PASSWORD
            );
        } catch (SQLException ex) {
            return null;
        }
    }
}
