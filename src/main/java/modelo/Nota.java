package modelo;
import java.time.LocalDateTime;

public class Nota {
    private int id;
    private String descripcion;
    private String area;
    private String firmante;
    private String mail;
    private LocalDateTime fecha;
    private boolean justificada;

    public Nota(int id, String descripcion, String area, String firmante, String mail, LocalDateTime fecha, boolean justificada) {
        this.id = id;
        this.descripcion = descripcion;
        this.area = area;
        this.firmante = firmante;
        this.mail = mail;
        this.fecha = fecha;
        this.justificada = justificada;
        
        
    }

    public Nota() {
        
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getFirmante() {
        return firmante;
    }

    public void setFirmante(String firmante) {
        this.firmante = firmante;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public boolean isJustificada() {
        return justificada;
    }

    public void setJustificada(boolean justificada) {
        this.justificada = justificada;
    }

    @Override
    public String toString() {
        return "Nota (" + firmante + ")";
    }
}

