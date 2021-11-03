package com.example.bikermice.utilidades;

public class Utilidades {


    //CONSTANTES CAMPOS TABLA CONDUCTORES

    public static final String TABLA_CONDUCTORES="conductores";
    public static final String CAMPO_ID_CONDUCTOR="id_conductor";
    public static String CAMPO_CEDULA="cedula";
    public static final String CAMPO_NOMBRE="nombre";
    public static final String CAMPO_GENERO="genero";
    public static final String CAMPO_EDAD="edad";
    public static final String CAMPO_TELEFONO="telefono";
    public static final String CAMPO_LUGARRESIDENCIA="lugarresidencia";
    public static final String CAMPO_LUGARLABORAL="lugarlaboral";
    public static final String CAMPO_ESTADOCIVIL="estadocivil";
    public static final String CAMPO_IMPLEMENTOS="implementos";
    public static final String CAMPO_DIASLABORALES="diaslaborales";
    public static final String CAMPO_FOTO_CONDUCTOR="fotoconductor";

    //CONSTANTES CAMPOS TABLA MOTOS


    public static final String TABLA_MOTOS="motos";
    public static String CAMPO_ID_MOTO ="id_moto";
    public static final String CAMPO_PLACA="placa";
    public static final String CAMPO_MODELO="modelo";
    public static final String CAMPO_MARCAMOTO="marcamoto";
    public static final String CAMPO_FOTOMOTO="fotomoto";


    //CONSTANTES CAMPOS TABLA PASAJEROS

    public static final String TABLA_PASAJEROS="pasajeros";
    public  static  final String CAMPO_ID_PASAJERO="id_pasajero";
    public static String CAMPO_CEDULA_PASAJERO="cedula";
    public static final String CAMPO_NOMBRE_PASAJERO="nombre";
    public static final String CAMPO_GENERO_PASAJERO="genero";
    public static final String CAMPO_EDAD_PASAJERO="edad";
    public static final String CAMPO_LUGARRESIDENCIA_PASAJERO="lugarresidencia";
    public static final String CAMPO_FOTO="fotopasajero";


    //CONSTANTES CAMPOS TABLA COMENTARIOS
    public static final String TABLA_COMENTARIOS="comentarios";
    public static final String CAMPO_COMENTARIO="comentario";
    public static final String CAMPO_FECHA_COMENTARIO="fecha";
    public static final String CAMPO_CEDULA_CONDUCTOR_COMENTARIO=" cedulaconductor";

    //CONSTANTES CAMPOS TABLA PUNTAJE


    public static final String TABLA_PUNTAJE="puntaje";
    public static String CAMPO_CEDULA_CONDUCTOR ="cedula_conductor";
    public static final String CAMPO_PUNTAJE="puntaje";




    public static final String CREAR_TABLA_CONDUCTORES="CREATE TABLE "+TABLA_CONDUCTORES+" (" +
            ""+CAMPO_ID_CONDUCTOR+" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
            ""+CAMPO_CEDULA+" TEXT, " +
            ""+CAMPO_NOMBRE+" TEXT," +
            ""+CAMPO_GENERO+" TEXT," +
            ""+CAMPO_EDAD+" TEXT, " +
            ""+CAMPO_TELEFONO+" TEXT," +
            ""+CAMPO_LUGARRESIDENCIA+" TEXT," +
            ""+CAMPO_LUGARLABORAL+" TEXT," +
            ""+CAMPO_ESTADOCIVIL+" TEXT," +
            ""+CAMPO_IMPLEMENTOS+" TEXT," +
            ""+CAMPO_DIASLABORALES+" TEXT," +
            ""+CAMPO_FOTO_CONDUCTOR+" BLOB)";

    public static final String CREAR_TABLA_MOTOS="CREATE TABLE "+TABLA_MOTOS+" (" +
            ""+CAMPO_ID_MOTO+" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
            ""+CAMPO_PLACA+" TEXT , " +
            ""+CAMPO_MODELO+" TEXT," +
            ""+CAMPO_MARCAMOTO+" TEXT," +
            ""+CAMPO_FOTOMOTO+" BLOB)" ;

    public static final String CREAR_TABLA_USUARIOS="CREATE TABLE "+TABLA_PASAJEROS+" (" +
            ""+CAMPO_ID_PASAJERO+" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
            ""+CAMPO_CEDULA_PASAJERO+" TEXT," +
            ""+CAMPO_NOMBRE_PASAJERO+" TEXT," +
            ""+CAMPO_GENERO_PASAJERO+" TEXT," +
            ""+CAMPO_EDAD_PASAJERO+" TEXT," +
            ""+CAMPO_LUGARRESIDENCIA_PASAJERO+" TEXT," +
            ""+CAMPO_FOTO+" BLOB)" ;

    public static final String CREAR_TABLA_COMENTARIOS="CREATE TABLE "+TABLA_COMENTARIOS+" (" +
            ""+ CAMPO_CEDULA_CONDUCTOR_COMENTARIO  +" TEXT," +
            ""+CAMPO_FECHA_COMENTARIO +" TEXT," +
            ""+CAMPO_COMENTARIO+" TEXT)";

    public static final String CREAR_TABLA_PUNTAJE="CREATE TABLE "+TABLA_PUNTAJE+" (" +
            ""+CAMPO_CEDULA_CONDUCTOR+" TEXT," +
            ""+CAMPO_PUNTAJE+" FLOAT)";


}
