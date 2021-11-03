package com.example.BikerMice.entidades;

public class Conductor {

    private int id_conductor;
    private String cedula;
    private String nombre;
    private  String genero;
    private String edad;
    private String telefono;
    private String lugarresidencia;
    private String lugarlaboral;
    private String estadocivil;
    private String implementos;
    private String diasLaborales;
    private Byte[] foto;




    public Conductor(int id_conductor, String cedula, String nombre, String genero, String edad, String telefono, String lugarresidencia, String lugarlaboral, String estadocivil, String implementos, String diasLaborales, Byte[] foto) {
        this.id_conductor=id_conductor;
        this.cedula = cedula;
        this.nombre = nombre;
        this.genero = genero;
        this.edad = edad;
        this.telefono = telefono;
        this.lugarresidencia = lugarresidencia;
        this.lugarlaboral = lugarlaboral;
        this.estadocivil = estadocivil;
        this.implementos = implementos;
        this.diasLaborales=diasLaborales;
        this.foto=foto;
    }

    public Conductor() {

    }


    public Byte[] getFoto() {
        return foto;
    }

    public void setFoto(Byte[] foto) {
        this.foto = foto;
    }
    public String getDiasLaborales() {
        return diasLaborales;
    }

    public void setDiasLaborales(String diasLaborales) {
        this.diasLaborales = diasLaborales;
    }

    public int getId_conductor() {
        return id_conductor;
    }

    public void setId_conductor(int id) {
        this.id_conductor = id;
    }

    public String getImplementos() {
        return implementos;
    }

    public void setImplementos(String implementos) {
        this.implementos = implementos;
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

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getLugarresidencia() {
        return lugarresidencia;
    }

    public void setLugarresidencia(String lugarresidencia) {
        this.lugarresidencia = lugarresidencia;
    }

    public String getLugarlaboral() {
        return lugarlaboral;
    }

    public void setLugarlaboral(String lugarlaboral) {
        this.lugarlaboral = lugarlaboral;
    }

    public String getEstadocivil() {
        return estadocivil;
    }

    public void setEstadocivil(String estadocivil) {
        this.estadocivil = estadocivil;
    }
}
