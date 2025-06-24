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