package com.example.BikerMice;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.BikerMice.utilidades.Utilidades;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class InformacionConductorActivity extends AppCompatActivity {


    ListView ListaInformacion;
    ArrayList<String> InfoConductor =new ArrayList<String>();
    ConexionSQLiteHelper conn;
    FirebaseAuth Auth;
    DatabaseReference db;
    TextView CampoNombre,CampoCedula,CampoEstadocivil,CampoGenero,CampoEdad,CampoResidencia,CampoDiasLaborales,CampoLugarLaboral;
    TextView CampoMarca,CampoPlaca,CampoModelo,CampoImplementos;
    Bundle Informacion;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacion_conductor);


      //  ListaInformacion = findViewById(R.id.ListaInformacionConductor);

        Auth=FirebaseAuth.getInstance();
        db=FirebaseDatabase.getInstance().getReference();


    //    ListaInformacion.setAdapter(new Adaptador(this,InfoConductor));



        CampoNombre=findViewById(R.id.textViewNombre);
        CampoCedula=findViewById(R.id.textViewCedula);
        CampoEdad=findViewById(R.id.textViewEdad);
        CampoGenero=findViewById(R.id.textViewGenero);
        CampoEstadocivil=findViewById(R.id.textViewEstadoCivil);
        CampoLugarLaboral=findViewById(R.id.textViewLugarLaboral);
        CampoResidencia=findViewById(R.id.textViewLugarResidencia);
        CampoDiasLaborales=findViewById(R.id.textViewDiasLaborales);

        CampoModelo=findViewById(R.id.textViewModelo);
        CampoMarca=findViewById(R.id.textViewMarca);
        CampoPlaca=findViewById(R.id.textViewPlaca);
        CampoImplementos=findViewById(R.id.textViewImplementosVehiculo);

        Informacion = this.getIntent().getExtras();



        if(Informacion==null){

            String vacio ="N";

            LlenarLista(Auth.getCurrentUser().getUid().toString(),vacio);


        }else{

            String cedulaConductor = Informacion.getString("cedula");


            LlenarLista(Auth.getCurrentUser().getUid().toString(),cedulaConductor);

        }




    }

    private void LlenarLista(String Uid,String señal) {

        if(señal==null){
            final String[] nombreConductor = new String[1];
            final String[] cedulaConductor = new String[1];


            db.child("Conductores").child(Uid).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    if(snapshot.exists()){


                        CampoNombre.setText("Nombre: "+ snapshot.child("nombre").getValue().toString());
                        CampoCedula.setText("Cedula: "+ snapshot.child("cedula").getValue().toString());
                        CampoEdad.setText("Edad: "+ snapshot.child("edad").getValue().toString());
                        CampoGenero.setText("Genero: "+ snapshot.child("genero").getValue().toString());
                        CampoEstadocivil.setText("Estoy: "+ snapshot.child("estadocivil").getValue().toString());
                        CampoLugarLaboral.setText("Trabajo en: "+ snapshot.child("laboral").getValue().toString());
                        CampoResidencia.setText("Vivo en: "+ snapshot.child("residencia").getValue().toString());
                        CampoDiasLaborales.setText("Trabajo:\n"+ snapshot.child("diaslaborales").getValue().toString());

                        CampoModelo.setText("Modelo: "+ snapshot.child("modelo").getValue().toString());
                        CampoMarca.setText("Marca: "+ snapshot.child("marca").getValue().toString());
                        CampoPlaca.setText("Placa: "+ snapshot.child("placa").getValue().toString());
                        CampoImplementos.setText("Le ofrezco: "+ snapshot.child("implementos").getValue().toString());











                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }else{

            final String[] nombreConductor = new String[1];
            final String[] cedulaConductor = new String[1];


            db.child("Conductores").orderByChild("cedula").equalTo(señal).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    if(snapshot.exists()){

                        for (DataSnapshot ds : snapshot.getChildren()){

                            CampoNombre.setText("Nombre: "+ ds.child("nombre").getValue().toString());
                            CampoCedula.setText("Cedula: "+ ds.child("cedula").getValue().toString());
                            CampoEdad.setText("Edad: "+ ds.child("edad").getValue().toString());
                            CampoGenero.setText("Genero: "+ ds.child("genero").getValue().toString());
                            CampoEstadocivil.setText("Estoy: "+ ds.child("estadocivil").getValue().toString());
                            CampoLugarLaboral.setText("Trabajo en: "+ ds.child("laboral").getValue().toString());
                            CampoResidencia.setText("Vivo en: "+ ds.child("residencia").getValue().toString());
                            CampoDiasLaborales.setText("Trabajo:\n"+ ds.child("diaslaborales").getValue().toString());

                            CampoModelo.setText("Modelo: "+ ds.child("modelo").getValue().toString());
                            CampoMarca.setText("Marca: "+ ds.child("marca").getValue().toString());
                            CampoPlaca.setText("Placa: "+ ds.child("placa").getValue().toString());
                            CampoImplementos.setText("Le ofrezco: "+ ds.child("implementos").getValue().toString());




                        }














                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });






        }








     /*   db.child("Conductores").child(Uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists()){


                    for(DataSnapshot snapshot2: ){

                        InfoConductor.add("Cedula: " + snapshot2.getValue());



                    }










            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
*/

    }

    public void onClickRegresarAConductor(View view) {


        String cedulaConductor =Informacion.getString("cedula");
        String señaldevolver=Informacion.getString("señaldevolver");






        Intent MiIntent = new Intent(getApplicationContext(),PerfilConductorActivity.class);

        Bundle MiBundleRegreso = new Bundle();

        MiBundleRegreso.putString("cedula",cedulaConductor);
        MiBundleRegreso.putString("señal",señaldevolver);
        MiBundleRegreso.putString("edad",Informacion.getString("edad"));
        MiBundleRegreso.putString("genero",Informacion.getString("genero"));
        MiBundleRegreso.putString("LugarLaboral",Informacion.getString("LugarLaboral"));
        MiBundleRegreso.putString("modelo",Informacion.getString("modelo"));

        MiIntent.putExtras(MiBundleRegreso);
        startActivity(MiIntent);
        finish();








    }
}