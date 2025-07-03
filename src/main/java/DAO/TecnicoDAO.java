package DAO;

import DataBase.Conexion;
import modelo.Tecnico;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TecnicoDAO {

    public List<Tecnico> obtenerTodos() {
        List<Tecnico> lista = new ArrayList<>();
        String sql = "SELECT nombre, email FROM tecnicos";
        
        try (Connection conn = Conexion.getConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Tecnico tecnico = new Tecnico(
                    rs.getString("nombre"),
                    rs.getString("email")
                );
                lista.add(tecnico);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }
}
