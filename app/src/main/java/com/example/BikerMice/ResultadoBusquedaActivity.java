package com.example.BikerMice;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.BikerMice.entidades.Conductor;
import com.example.BikerMice.utilidades.Utilidades;

import java.util.ArrayList;

public class ResultadoBusquedaActivity extends AppCompatActivity {

    ListView ListaConductores;
    ConexionSQLiteHelper conn;
    ArrayList<String> listaInformacion;
    ArrayList<Conductor> listaConductor;


    //bUNDLES PARA USARSE EN LOS RETORNOS
    String edad;
    String genero ;
    String LugarLaboral ;
    String modelo ;
    String cedulaPasajero;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado_busqueda);

        conn = new ConexionSQLiteHelper(getApplicationContext(),"dbBikerMice2",null,1);

        ListaConductores=findViewById(R.id.ListaConductores);


        Bundle MiBundle = this.getIntent().getExtras();

        edad =MiBundle.getString("edad");
        genero =MiBundle.getString("genero");
        LugarLaboral =MiBundle.getString("LugarLaboral");
        modelo =MiBundle.getString("Modelo");
        cedulaPasajero=MiBundle.getString("cedulapasajero");


        ConsultarConductores(edad,genero,LugarLaboral,modelo);



        ListaConductores.setAdapter(new Adaptador(this,listaInformacion));

        ListaConductores.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String cedula = listaConductor.get(i).getCedula();
                String señal = "1";

                Intent MiIntent= new Intent(getApplicationContext(),PerfilConductorActivity.class);

                Bundle MiBundle=new Bundle();
                MiBundle.putString("cedula",cedula);
                MiBundle.putString("señal",señal);

                //BUNDLES PARA EL REGRESO
                MiBundle.putString("edad",edad);
                MiBundle.putString("genero",genero);
                MiBundle.putString("lugarlaboral",LugarLaboral);
                MiBundle.putString("modelo",modelo);
                MiBundle.putString("cedulapasajero",cedulaPasajero);


                MiIntent.putExtras(MiBundle);

                startActivity(MiIntent);
                finish();

            }
        }


        );




    }

    private void ConsultarConductores(String edad,String genero, String LugarLaboral, String Modelo) {


        String prueba2 ="";
        String Genero="Genero";
        String Laboral="Lugar laboral";
        String Consulta = "";


        //AQUI SE ARMA EL STRING CONDICION CUANDO ELIGEN SOLO UN FILTRO

        if (!edad.equals(prueba2) || !genero.equals(Genero) || !LugarLaboral.equals(Laboral)) {

            if ((!edad.equals(prueba2) && genero.equals(Genero) && LugarLaboral.equals(Laboral))) {

                Consulta+= "edad = '"+edad+"'";

            }

            if (edad.equals(prueba2) && !genero.equals(Genero) && LugarLaboral.equals(Laboral)) {

                Consulta+= "genero = '"+genero+"'";

            }

            if (edad.equals(prueba2) && genero.equals(Genero) && !LugarLaboral.equals(Laboral)) {

                Consulta+= "lugarlaboral = '"+LugarLaboral+"'";

            }

            if (!edad.equals(prueba2) && genero.equals(Genero) && !LugarLaboral.equals(Laboral)) {

                Consulta += "edad = '" + edad + "' and ";
                Consulta+= "lugarlaboral = '"+LugarLaboral+"'";

            }

            if (!edad.equals(prueba2) && !genero.equals(Genero) && LugarLaboral.equals(Laboral)) {

                Consulta += "edad = '" + edad + "' and ";
                Consulta += "genero = '" + genero + "' ";

            }

            if (edad.equals(prueba2) && !genero.equals(Genero) && !LugarLaboral.equals(Laboral)) {

                Consulta += "genero = '" + genero + "' and ";
                Consulta += "lugarlaboral = '" + LugarLaboral + "'";

            }

            if (!edad.equals(prueba2) && !genero.equals(Genero) && !LugarLaboral.equals(Laboral)) {

                Consulta += "edad = '" + edad + "' and ";
                Consulta += "genero = '" + genero + "' and ";
                Consulta += "lugarlaboral = '" + LugarLaboral + "'";

            }





        }



            SQLiteDatabase db = conn.getReadableDatabase();

            Conductor conductor = null;
            listaConductor = new ArrayList<Conductor>();
            String where2 = "where";
            where2 += " " + Consulta;

            String ConsultaSQL = "select cedula,nombre from " + Utilidades.TABLA_CONDUCTORES + " " + where2;


            Cursor cursor = db.rawQuery(ConsultaSQL, null);

            while (cursor.moveToNext()) {

                conductor = new Conductor();

                conductor.setCedula(cursor.getString(0));
                conductor.setNombre(cursor.getString(1));

                listaConductor.add(conductor);

            }

            ObtenerLista();


            cursor.close();
            db.close();


    }


    private void ObtenerLista() {

        listaInformacion = new ArrayList<String>();

        for(int i=0; i<listaConductor.size();i++){

            listaInformacion.add(listaConductor.get(i).getCedula()+" - "+listaConductor.get(i).getNombre());



        }


    }

    public void Regresar(View view) {

        Intent MiIntent = new Intent(getApplicationContext(),PerfilPasajeroActivity.class);

        Bundle MiBundle=new Bundle();

        MiBundle.putString("cedula",cedulaPasajero);


        MiIntent.putExtras(MiBundle);



        startActivity(MiIntent);
        finish();

    }
}