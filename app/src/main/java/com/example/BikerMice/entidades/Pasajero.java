package com.example.BikerMice.entidades;

public class Pasajero {

    private String cedula;
    private String nombre;
    private String genero;
    private String edad;
    private String lugarresidencia;
    private int id_pasajero;
    private Byte[] foto;




    public Pasajero(int id_pasajero, String cedula, String nombre, String genero, String edad, String lugarresidencia, Byte[] foto) {
        this.id_pasajero=id_pasajero;
        this.cedula = cedula;
        this.nombre = nombre;
        this.genero = genero;
        this.edad = edad;
        this.lugarresidencia = lugarresidencia;
        this.foto = foto;
    }

    public void setId_pasajero(int id_pasajero) {
        this.id_pasajero = id_pasajero;
    }

    public Byte[] getFoto() {
        return foto;
    }

    public void setFoto(Byte[] foto) {
        this.foto = foto;
    }

    public int getId_pasajero() {
        return id_pasajero;
    }

    public void setId(int id) {
        this.id_pasajero = id_pasajero;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public String getLugarresidencia() {
        return lugarresidencia;
    }

    public void setLugarresidencia(String lugarresidencia) {
        this.lugarresidencia = lugarresidencia;
    }
}
