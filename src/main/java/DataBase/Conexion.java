package DataBase;

import java.sql.*;

public class Conexion {
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/SIGME";
    private static final String USER = "root";
    private static final String PASSWORD = "bocajuniorscampeon021020021234...";

    public static Connection getConexion() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}

