package DAO;
import DataBase.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
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
    
    public List<Recurso> listarRecursos(){
        List<Recurso> resultado = new ArrayList<>();
        String sql = "SELECT * FROM recurso";
        try (Connection con = Conexion.getConexion();
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(sql)){
            
            while(rs.next()){
                Recurso rec = new Recurso(
                        rs.getString("nombre"),
                        rs.getString("descripcionRecurso"),
                        rs.getInt("cantidadDisponible")
                );
                resultado.add(rec);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return resultado;
    }
}

/*
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
*/


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