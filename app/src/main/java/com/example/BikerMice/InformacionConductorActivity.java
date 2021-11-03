package com.example.BikerMice;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.example.BikerMice.utilidades.Utilidades;

import java.util.ArrayList;

public class InformacionConductorActivity extends AppCompatActivity {


    ListView ListaInformacion;
    ArrayList<String> InfoConductor;
    ConexionSQLiteHelper conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacion_conductor);


        ListaInformacion = findViewById(R.id.ListaInformacionConductor);

        Bundle MiBundle = this.getIntent().getExtras();

        String cedulaConductor =MiBundle.getString("cedulaConductor");

        LlenarLista(cedulaConductor);
        ListaInformacion.setAdapter(new Adaptador(this,InfoConductor));


    }

    private void LlenarLista(String cedulaConductor) {

        Bundle MiBundle = this.getIntent().getExtras();

        int id_conductor =MiBundle.getInt("id_conductor");
        InfoConductor = new ArrayList<String>();

        conn = new ConexionSQLiteHelper(getApplicationContext(),"dbBikerMice2",null,1);

        SQLiteDatabase db = conn.getReadableDatabase();

        String ConsultaSQL = "select * from " + Utilidades.TABLA_CONDUCTORES + " where cedula = '"+cedulaConductor+"'";
        String ConsultaSQL2 = "select * from " + Utilidades.TABLA_MOTOS + " where id_moto = '"+id_conductor+"'";

        Cursor cursor = db.rawQuery(ConsultaSQL, null);
        Cursor cursor2 = db.rawQuery(ConsultaSQL2,null);


        while (cursor.moveToNext() && cursor2.moveToNext()) {

            String cedula = cursor.getString(1);
            InfoConductor.add("Cedula: "+cedula);

            String nombre = cursor.getString(2);
            InfoConductor.add("Nombre: "+nombre);

            String genero = cursor.getString(3);
            InfoConductor.add("Genero: "+genero);

            String edad = cursor.getString(4);
            InfoConductor.add("Edad: "+edad);

            String telefono = cursor.getString(5);
            InfoConductor.add("Telefono: "+telefono);

            String lugarresidencia = cursor.getString(6);
            InfoConductor.add("Vivo en: "+lugarresidencia);

            String lugarlaboral = cursor.getString(7);
            InfoConductor.add("Trabajo en: "+lugarlaboral);

            String estadocivil = cursor.getString(8);
            InfoConductor.add("Estoy: "+estadocivil);

            String implementos = cursor.getString(9);
            InfoConductor.add("Te ofrezco: "+implementos);

            String diaslaborales = cursor.getString(10);
            InfoConductor.add("Trabajo:\n"+diaslaborales);

            String modelo = cursor2.getString(2);
            InfoConductor.add("Modelo moto: "+modelo);

            String placa = cursor2.getString(1);
            InfoConductor.add("Placa: "+placa);

            String marca = cursor2.getString(3);
            InfoConductor.add("Marca: "+marca);

        }

        cursor.close();
        cursor2.close();
        db.close();






    }

    public void onClickRegresarAConductor(View view) {

        Bundle MiBundle = this.getIntent().getExtras();

        String cedulaConductor =MiBundle.getString("cedulaConductor");
        String señal =MiBundle.getString("señal");
        String edad = MiBundle.getString("edad");
        String genero = MiBundle.getString("genero");
        String LugarLaboral = MiBundle.getString("LugarLaboral");
        String Modelo = MiBundle.getString("Modelo");
        String cedulapasajero = MiBundle.getString("cedulapasajero");




        Intent MiIntent = new Intent(getApplicationContext(),PerfilConductorActivity.class);

        Bundle MiBundleRegreso = new Bundle();

        MiBundleRegreso.putString("cedula",cedulaConductor);
        MiBundleRegreso.putString("señal",señal);
        MiBundleRegreso.putString("edad",edad);
        MiBundleRegreso.putString("genero",genero);
        MiBundleRegreso.putString("lugarlaboral",LugarLaboral);
        MiBundleRegreso.putString("modelo",Modelo);
        MiBundleRegreso.putString("cedulapasajero",cedulapasajero);

        MiIntent.putExtras(MiBundleRegreso);
        startActivity(MiIntent);
        finish();








    }
}