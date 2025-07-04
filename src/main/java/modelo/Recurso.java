package modelo;

public class Recurso {
    private String nombre;
    private int cantidadDisponible;
    private String descripcionRecurso;

    public String getDescripcionRecurso() {
        return descripcionRecurso;
    }

    public void setDescripcionRecurso(String descripcionRecurso) {
        this.descripcionRecurso = descripcionRecurso;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCantidadDisponible() {
        return cantidadDisponible;
    }

    public void setCantidadDisponible(int cantidadDisponible) {
        this.cantidadDisponible = cantidadDisponible;
    }

    public Recurso(String nombre, int cantidadDisponible) {
        this.nombre = nombre;
        this.cantidadDisponible = cantidadDisponible;
    }

    public Recurso(String nombre,  String descripcionRecurso, int cantidadDisponible) {
        this.nombre = nombre;
        this.descripcionRecurso = descripcionRecurso;
        this.cantidadDisponible = cantidadDisponible;

    }

    public Recurso() {
    }

    
}
