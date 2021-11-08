package com.example.BikerMice;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.arch.core.executor.DefaultTaskExecutor;

import android.hardware.camera2.TotalCaptureResult;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.BikerMice.entidades.Conductor;
import com.example.BikerMice.utilidades.Utilidades;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

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

    DatabaseReference database;
    FirebaseAuth Auth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado_busqueda);



        ListaConductores=findViewById(R.id.ListaConductores);
        Auth=FirebaseAuth.getInstance();


        Bundle MiBundle = this.getIntent().getExtras();

        if (MiBundle != null){
            edad =MiBundle.getString("edad");
            genero =MiBundle.getString("genero");
            LugarLaboral =MiBundle.getString("LugarLaboral");
            modelo =MiBundle.getString("modelo");


        }



        database = FirebaseDatabase.getInstance().getReference();
        listaConductor = new ArrayList<Conductor>();
        listaInformacion = new ArrayList<String>();



        ConsultarConductores(edad,genero,LugarLaboral,modelo);






        ListaConductores.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String cedula = listaConductor.get(i).getCedula();
                String señal = "1";


                Bundle MiBundle = new Bundle();

                MiBundle.putString("cedula", cedula);
                MiBundle.putString("señal",señal);
                MiBundle.putString("edad",edad);
                MiBundle.putString("genero",genero);
                MiBundle.putString("LugarLaboral",LugarLaboral);
                MiBundle.putString("modelo",modelo);

                Intent MiIntent = new Intent(getApplicationContext(),PerfilConductorActivity.class);

                MiIntent.putExtras(MiBundle);


                startActivity(MiIntent);
                finish();




            }
        }


        );




    }

    private void ConsultarConductores(String edad,String genero, String LugarLaboral, String Modelo) {



        String comparacion =null;
        String comparacion2 = "Genero";
        String comparacion3 = "Lugar laboral";

        listaInformacion = new ArrayList<String>();

        //AQUI SE CONSULTA POR EDAD

        if(!(edad==null) && genero.equals(comparacion2) && LugarLaboral.equals(comparacion3) && Modelo==null ) {


            database.child("Conductores").orderByChild("edad").equalTo(edad).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    if (snapshot.exists()) {


                        for (DataSnapshot ds : snapshot.getChildren()) {

                            Conductor conductor = new Conductor();

                            conductor = ds.getValue(Conductor.class);

                            listaConductor.add(conductor);


                        }

                        ObtenerLista();

                        ListaConductores.setAdapter(new Adaptador(getApplicationContext(), listaInformacion));


                    }


                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });


        }

        if(edad==null && !genero.equals(comparacion2) && LugarLaboral.equals(comparacion3) && Modelo==null ) {


            database.child("Conductores").orderByChild("genero").equalTo(genero).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    if (snapshot.exists()) {


                        for (DataSnapshot ds : snapshot.getChildren()) {

                            Conductor conductor = new Conductor();

                            conductor = ds.getValue(Conductor.class);

                            listaConductor.add(conductor);


                        }

                        ObtenerLista();

                        ListaConductores.setAdapter(new Adaptador(getApplicationContext(), listaInformacion));


                    }


                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });


        }

        //AQUI SE CONSULTA POR LUGAR LABORAL

        if(edad==null && genero.equals(comparacion2) && !LugarLaboral.equals(comparacion3) && Modelo==null ) {


            database.child("Conductores").orderByChild("laboral").equalTo(LugarLaboral).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    if (snapshot.exists()) {


                        for (DataSnapshot ds : snapshot.getChildren()) {

                            Conductor conductor = new Conductor();

                            conductor = ds.getValue(Conductor.class);

                            listaConductor.add(conductor);


                        }

                        ObtenerLista();

                        ListaConductores.setAdapter(new Adaptador(getApplicationContext(), listaInformacion));


                    }


                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });


        }


        //AQUI SE CONSULTA POR Modelo

        if(edad==null && genero.equals(comparacion2) && LugarLaboral.equals(comparacion3) && !(Modelo==null) ) {


            database.child("Conductores").orderByChild("modelo").equalTo(Modelo).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    if (snapshot.exists()) {


                        for (DataSnapshot ds : snapshot.getChildren()) {

                            Conductor conductor = new Conductor();

                            conductor = ds.getValue(Conductor.class);

                            listaConductor.add(conductor);


                        }

                        ObtenerLista();

                        ListaConductores.setAdapter(new Adaptador(getApplicationContext(), listaInformacion));


                    }


                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });


        }




















    }


    private void ObtenerLista() {





        for(int i=0; i<listaConductor.size();i++){

            listaInformacion.add(listaConductor.get(i).getCedula()+" - "+listaConductor.get(i).getNombre());



        }


    }

    public void Regresar(View view) {

        Intent MiIntent = new Intent(getApplicationContext(),PerfilPasajeroActivity.class);

        Bundle MiBundle=new Bundle();



        MiBundle.putString("Uid",Auth.getCurrentUser().getUid().toString());


        MiIntent.putExtras(MiBundle);



        startActivity(MiIntent);
        finish();

    }
}