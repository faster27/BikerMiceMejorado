package com.example.BikerMice.entidades;

public class Conductor {

    private int id_conductor;
    private String cedula;
    private String nombre;
    private  String genero;
    private String edad;
    private String telefono;
    private String residencia;
    private String laboral;
    private String estadocivil;
    private String implementos;
    private String diaslaborales;
    private Byte[] foto;
    private String marca;
    private String modelo;
    private String placa;
    private String email;
    private String contrasena;




    public Conductor(int id_conductor, String cedula, String nombre, String genero,
                     String edad, String telefono, String lugarresidencia, String lugarlaboral,
                     String estadocivil, String implementos, String diasLaborales, Byte[] foto,
                     String marca, String modelo, String placa, String email, String contrasena) {
        this.id_conductor=id_conductor;
        this.cedula = cedula;
        this.nombre = nombre;
        this.genero = genero;
        this.edad = edad;
        this.telefono = telefono;
        this.residencia = lugarresidencia;
        this.laboral = lugarlaboral;
        this.estadocivil = estadocivil;
        this.implementos = implementos;
        this.diaslaborales =diasLaborales;
        this.foto=foto;

        this.marca= marca;
        this.modelo=modelo;
        this.placa=placa;
        this.email=email;
        this.contrasena=contrasena;
    }

    public Conductor() {

    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }


    public Byte[] getFoto() {
        return foto;
    }

    public void setFoto(Byte[] foto) {
        this.foto = foto;
    }
    public String getDiaslaborales() {
        return diaslaborales;
    }

    public void setDiaslaborales(String diaslaborales) {
        this.diaslaborales = diaslaborales;
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

    public String getResidencia() {
        return residencia;
    }

    public void setResidencia(String residencia) {
        this.residencia = residencia;
    }

    public String getLaboral() {
        return laboral;
    }

    public void setLaboral(String laboral) {
        this.laboral = laboral;
    }

    public String getEstadocivil() {
        return estadocivil;
    }

    public void setEstadocivil(String estadocivil) {
        this.estadocivil = estadocivil;
    }
}
