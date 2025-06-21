package DAO;
import modelo.Nota;
import DataBase.Conexion;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class NotaDAO {
    public void insertarNota(Nota nota) {
        String sql = "INSERT INTO notas (descripcion, area, firmante, mail, tecnicoAsignado, fecha, justificada) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = Conexion.getConexion(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nota.getDescripcion());
            stmt.setString(2, nota.getArea());
            stmt.setString(3, nota.getFirmante());
            stmt.setString(4, nota.getMail());
            stmt.setString(5, nota.getTecnicoAsignado());
            stmt.setTimestamp(6, Timestamp.valueOf(nota.getFecha()));
            stmt.setBoolean(7, nota.isJustificada());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Nota> obtenerTodas() {
        List<Nota> notas = new ArrayList<>();
        String sql = "SELECT * FROM notas";
        try (Connection conn = Conexion.getConexion(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Nota nota = new Nota(
                    rs.getInt("id"),
                    rs.getString("descripcion"),
                    rs.getString("area"),
                    rs.getString("firmante"),
                    rs.getString("mail"),
                    rs.getString("tecnicoAsignado"),
                    rs.getTimestamp("fecha").toLocalDateTime(),
                    rs.getBoolean("justificada")
                );
                notas.add(nota);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return notas;
    }
}

