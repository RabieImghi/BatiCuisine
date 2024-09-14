package config;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {
    private Connection connection;
    private static DatabaseConnection instance;
    public  DatabaseConnection() {
        try {
            String url = "jdbc:mysql://localhost:3306/BatiCuisine";
            String user = "BatCuisine";
            String password = "";
            connection = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            System.out.println("Error : "+e.getMessage());
        }
    }

    public static DatabaseConnection getInstance(){
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }
    public Connection getConnection() {
        return connection;
    }
}
