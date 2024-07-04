
package models;

import java.util.Date;

public class Persona {
    private int id;
    private String nombre;
    private String cedula;
    private Date fechaNac;
    private int edad;

    public Persona() {
    }

    public Persona(int id, String nombre, String cedula, Date fechaNac, int edad) {
        this.id = id;
        this.nombre = nombre;
        this.cedula = cedula;
        this.fechaNac = fechaNac;
        this.edad = edad;
    }

    public Persona(String nombre, String cedula, Date fechaNac, int edad) {
        this.nombre = nombre;
        this.cedula = cedula;
        this.fechaNac = fechaNac;
        this.edad = edad;
    }

    public Persona(String nombre, String cedula, Date fechaNac) {
        this.nombre = nombre;
        this.cedula = cedula;
        this.fechaNac = fechaNac;
        this.edad = 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public Date getFechaNac() {
        return fechaNac;
    }

    public void setFechaNaci(Date fechaNac) {
        this.fechaNac = fechaNac;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    
}