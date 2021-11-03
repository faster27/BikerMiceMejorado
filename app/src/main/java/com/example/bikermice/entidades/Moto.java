package com.example.BikerMice.entidades;

public class Moto {

    private int id_moto;
    private String placa;
    private String modelo;
    private String marca;
    private Byte[] foto;




    public Moto(int id_moto, String placa, String modelo, String marca, Byte[] foto) {
        this.id_moto=id_moto;
        this.placa = placa;
        this.modelo = modelo;
        this.marca = marca;
        this.foto=foto;
    }

    public Byte[] getFoto() {
        return foto;
    }

    public void setFoto(Byte[] foto) {
        this.foto = foto;
    }

    public int getId_moto() {
        return id_moto;
    }

    public void setId_moto(int id_moto) {
        this.id_moto = id_moto;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }
}
