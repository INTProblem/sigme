package DAO;

import modelo.OrdenTrabajo;
import DataBase.Conexion;

import java.sql.*;

public class OrdenTrabajoDAO {
    public void insertarOT(OrdenTrabajo ot) {
        String sql = "INSERT INTO orden_trabajo (numeroTramite, prioridad, estado, responsable, tecnicoAsignado, recurso, fechaAsignacion, fechaFinalizacion) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = Conexion.getConexion(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, ot.getNumeroTramite());
            stmt.setString(2, ot.getPrioridad());
            stmt.setString(3, ot.getEstado().name());
            stmt.setString(4, ot.getResponsable());
            stmt.setString(5, ot.getTecnicoAsignado().getNombre());
            stmt.setString(6, ot.getRecurso());
            stmt.setDate(7, Date.valueOf(ot.getFechaAsignacion()));
            stmt.setDate(8, Date.valueOf(ot.getFechaFinalizacion()));
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}