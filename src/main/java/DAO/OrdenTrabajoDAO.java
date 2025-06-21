package DAO;


import modelo.*;
import modelo.OrdenTrabajo;
import modelo.EstadoOrden;
import modelo.Fecha;
import DataBase.Conexion;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
            stmt.setDate(8, ot.getFechaFinalizacion() != null ? Date.valueOf(ot.getFechaFinalizacion()) : null);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<OrdenTrabajo> obtenerTodas() {
        List<OrdenTrabajo> lista = new ArrayList<>();
        String sql = "SELECT * FROM orden_trabajo";
        try (Connection conn = Conexion.getConexion(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                OrdenTrabajo ot = new OrdenTrabajo(
                    rs.getInt("numero"),
                    rs.getInt("numeroTramite"),
                    rs.getString("prioridad"),
                    EstadoOrden.valueOf(rs.getString("estado")),
                    rs.getString("responsable"),
                    new Tecnico(rs.getString("tecnicoAsignado")),
                    rs.getString("recurso"),
                    rs.getDate("fechaAsignacion").toLocalDate(),
                    rs.getDate("fechaFinalizacion") != null ? rs.getDate("fechaFinalizacion").toLocalDate() : null
                );
                lista.add(ot);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public void actualizarEstado(int numeroOT, String nuevoEstado) {
        String sql = "UPDATE orden_trabajo SET estado = ?, fechaFinalizacion = ? WHERE numero = ?";
        try (Connection conn = Conexion.getConexion(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nuevoEstado);
            stmt.setDate(2, Date.valueOf(LocalDate.now()));
            stmt.setInt(3, numeroOT);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
