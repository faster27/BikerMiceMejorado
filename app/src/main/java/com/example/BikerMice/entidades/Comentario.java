package com.example.BikerMice.entidades;

public class Comentario {

    private String fecha;
    private String comentario;
    private int cedulaconductor;
    private int cedulpasajero;

    public Comentario(String fecha, String comentario, int cedulaconductor, int cedulpasajero) {
        this.fecha = fecha;
        this.comentario = comentario;
        this.cedulaconductor = cedulaconductor;
        this.cedulpasajero = cedulpasajero;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public int getCedulaconductor() {
        return cedulaconductor;
    }

    public void setCedulaconductor(int cedulaconductor) {
        this.cedulaconductor = cedulaconductor;
    }

    public int getCedulpasajero() {
        return cedulpasajero;
    }

    public void setCedulpasajero(int cedulpasajero) {
        this.cedulpasajero = cedulpasajero;
    }
}
