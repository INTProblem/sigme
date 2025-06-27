package modelo;

import java.time.LocalDate;

public class OrdenTrabajo {
    private int numero;
    private int numeroTramite;
    private String prioridad;
    private EstadoOrden estado;
    private String responsable;
    private Tecnico tecnicoAsignado;
    private String recurso;
    private String problema;
    private LocalDate fechaAsignacion;
    private LocalDate fechaFinalizacion;

    public OrdenTrabajo(int numero, int numeroTramite, String prioridad, EstadoOrden estado,
                        String responsable, Tecnico tecnicoAsignado, String recurso,String problema,
                        LocalDate fechaAsignacion, LocalDate fechaFinalizacion) {
        this.numero = numero;
        this.numeroTramite = numeroTramite;
        this.prioridad = prioridad;
        this.estado = estado;
        this.responsable = responsable;
        this.tecnicoAsignado = tecnicoAsignado;
        this.recurso = recurso;
        this.problema = problema;
        this.fechaAsignacion = fechaAsignacion;
        this.fechaFinalizacion = fechaFinalizacion;
        
        
    }
     public OrdenTrabajo() {
  
    }

    public String getProblema() {
        return problema;
    }

    public void setProblema(String problema) {
        this.problema = problema;
    }
     
    

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public int getNumeroTramite() {
        return numeroTramite;
    }

    public void setNumeroTramite(int numeroTramite) {
        this.numeroTramite = numeroTramite;
    }

    public String getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(String prioridad) {
        this.prioridad = prioridad;
    }

    public EstadoOrden getEstado() {
        return estado;
    }

    public void setEstado(EstadoOrden estado) {
        this.estado = estado;
    }

    public String getResponsable() {
        return responsable;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }

    public Tecnico getTecnicoAsignado() {
        return tecnicoAsignado;
    }

    public void setTecnicoAsignado(Tecnico tecnicoAsignado) {
        this.tecnicoAsignado = tecnicoAsignado;
    }

    public String getRecurso() {
        return recurso;
    }

    public void setRecurso(String recurso) {
        this.recurso = recurso;
    }

    public LocalDate getFechaAsignacion() {
        return fechaAsignacion;
    }

    public void setFechaAsignacion(LocalDate fechaAsignacion) {
        this.fechaAsignacion = fechaAsignacion;
    }

    public LocalDate getFechaFinalizacion() {
        return fechaFinalizacion;
    }

    public void setFechaFinalizacion(LocalDate fechaFinalizacion) {
        this.fechaFinalizacion = fechaFinalizacion;
    }


}