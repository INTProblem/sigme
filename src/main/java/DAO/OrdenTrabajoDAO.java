package DAO;

import modelo.*;
import modelo.OrdenTrabajo;
import modelo.EstadoOrden;
import DataBase.Conexion;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OrdenTrabajoDAO {
    public void insertarOT(OrdenTrabajo ot) {
        String sql = "INSERT INTO orden_trabajo (numeroTramite, prioridad, estado, responsable, tecnicoAsignado, mailTecnico, recurso, problema, fechaAsignacion, fechaFinalizacion) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = Conexion.getConexion(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, ot.getNumeroTramite());
            stmt.setString(2, ot.getPrioridad());
            stmt.setString(3, ot.getEstado().name());
            stmt.setString(4, ot.getResponsable());
            stmt.setString(5, ot.getTecnicoAsignado().getNombre());
            stmt.setString(6, ot.getTecnicoAsignado().getMail());
            stmt.setString(7, ot.getRecurso());
            stmt.setString(8, ot.getProblema());
            stmt.setDate(9, Date.valueOf(ot.getFechaAsignacion()));
            if (ot.getFechaFinalizacion() != null) {
                stmt.setDate(10, Date.valueOf(ot.getFechaFinalizacion()));
            } else {
                stmt.setNull(10, Types.DATE);
            }
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
                    new Tecnico(rs.getString("tecnicoAsignado"), rs.getString("mailTecnico")),
                    rs.getString("recurso"),
                    rs.getString("problema"),
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
            if (nuevoEstado.equals("FINALIZADO") || nuevoEstado.equals("CANCELADO")) {
                stmt.setDate(2, Date.valueOf(LocalDate.now()));
            } else {
                stmt.setNull(2, Types.DATE);
            }
            stmt.setInt(3, numeroOT);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void eliminarFinalizadasOCanceladas() {
        String sql = "DELETE FROM orden_trabajo WHERE estado = ? OR estado = ?";
        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, EstadoOrden.FINALIZADO.name());
            stmt.setString(2, EstadoOrden.CANCELADO.name());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public OrdenTrabajo obtenerPorNumero(int numeroOT) {
        String sql = "SELECT * FROM orden_trabajo WHERE numero = ?";
        try (Connection conn = Conexion.getConexion(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, numeroOT);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new OrdenTrabajo(
                        rs.getInt("numero"),
                        rs.getInt("numeroTramite"),
                        rs.getString("prioridad"),
                        EstadoOrden.valueOf(rs.getString("estado")),
                        rs.getString("responsable"),
                        new Tecnico(rs.getString("tecnicoAsignado"), rs.getString("mailTecnico")),
                        rs.getString("recurso"),
                        rs.getString("problema"),
                        rs.getDate("fechaAsignacion").toLocalDate(),
                        rs.getDate("fechaFinalizacion") != null ? rs.getDate("fechaFinalizacion").toLocalDate() : null
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public OrdenTrabajo obtenerPorNumeroTramite(int numeroOT) {
        String sql = "SELECT * FROM orden_trabajo WHERE numeroTramite = ?";
        try (Connection conn = Conexion.getConexion(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, numeroOT);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new OrdenTrabajo(
                        rs.getInt("numero"),
                        rs.getInt("numeroTramite"),
                        rs.getString("prioridad"),
                        EstadoOrden.valueOf(rs.getString("estado")),
                        rs.getString("responsable"),
                        new Tecnico(rs.getString("tecnicoAsignado"), rs.getString("mailTecnico")),
                        rs.getString("recurso"),
                        rs.getString("problema"),
                        rs.getDate("fechaAsignacion").toLocalDate(),
                        rs.getDate("fechaFinalizacion") != null ? rs.getDate("fechaFinalizacion").toLocalDate() : null
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void actualizarOT(OrdenTrabajo ot) {
        String sql = "UPDATE orden_trabajo SET prioridad = ?, estado = ?, tecnicoAsignado = ?, mailTecnico = ?, recurso = ?, problema = ?, fechaFinalizacion = ? WHERE numero = ?";
        try (Connection conn = Conexion.getConexion(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, ot.getPrioridad());
            stmt.setString(2, ot.getEstado().name());
            stmt.setString(3, ot.getTecnicoAsignado().getNombre());
            stmt.setString(4, ot.getTecnicoAsignado().getMail());
            stmt.setString(5, ot.getRecurso());
            stmt.setString(6, ot.getProblema());
            if (ot.getEstado() == EstadoOrden.FINALIZADO || ot.getEstado() == EstadoOrden.CANCELADO) {
                stmt.setDate(7, Date.valueOf(LocalDate.now()));
            } else {
                stmt.setNull(7, Types.DATE);
            }
            stmt.setInt(8, ot.getNumero());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
