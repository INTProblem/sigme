package DAO;
import DataBase.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import modelo.Recurso;

public class RecursoDAO {
    
    public RecursoDAO() {
    }
    
    public void insertarRecurso(Recurso rec){
        String sql = "INSERT INTO recurso (nombreRecurso, descripcionRecurso, cantidadDisponible) VALUES (?,?,?)";
        try (Connection con = Conexion.getConexion(); PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, rec.getNombre());
            stmt.setString(2, rec.getDescripcionRecurso());
            stmt.setInt(3, rec.getCantidadDisponible());
            stmt.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
    
}

/*
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
*/