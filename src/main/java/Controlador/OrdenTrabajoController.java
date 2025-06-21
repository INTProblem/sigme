package Controlador;
import modelo.*;

public class OrdenTrabajoController {
    public OrdenTrabajo generarOTDesdeNota(Nota nota) {
        if (!nota.isJustificada()) throw new RuntimeException("Nota no justificada");
        OrdenTrabajo ot = new OrdenTrabajo();
        ot.setEstado(EstadoOrden.EVALUACION);
        // completar datos
        return ot;
    }

    public void asignarTecnico(OrdenTrabajo ot, Tecnico t) {
        ot.setTecnicoAsignado(t);
        ot.setEstado(EstadoOrden.EN_PROCESO);
    }

    public void cerrarOTNoSolucionable(OrdenTrabajo ot) {
        ot.setEstado(EstadoOrden.NO_SOLUCIONABLE);
    }
}