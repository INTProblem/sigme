package modelo;

public class Tecnico {
    private String nombre;
    private String mail;

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public Tecnico(String nombre, String mail) {
        this.nombre = nombre;
        this.mail = mail;
    }

    public Tecnico() {
    }
    
    

    public Tecnico(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    @Override
    public String toString() {
        return nombre;
    }
}