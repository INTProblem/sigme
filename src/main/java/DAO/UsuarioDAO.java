package DAO;

import modelo.Usuario;
import DataBase.Conexion;

import java.sql.*;

public class UsuarioDAO {
    public Usuario obtenerUsuario(String username, String password) {
        try (Connection conn = Conexion.getConexion()) {
            String sql = "SELECT * FROM usuarios WHERE username = ? AND password = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Usuario(
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("rol")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
