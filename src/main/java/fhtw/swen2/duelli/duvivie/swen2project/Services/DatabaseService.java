package fhtw.swen2.duelli.duvivie.swen2project.Services;

import com.fasterxml.jackson.annotation.JsonGetter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class DatabaseService {

    private Connection connection;

    public DatabaseService() throws SQLException {
        connection = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5431/postgres",
                "admin",
                "admin-pw"
        );
    }

    public Connection getConnection() {
        return connection;
    }
}

