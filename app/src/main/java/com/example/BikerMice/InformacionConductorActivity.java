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




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacion_conductor);


      //  ListaInformacion = findViewById(R.id.ListaInformacionConductor);

        Auth=FirebaseAuth.getInstance();
        db=FirebaseDatabase.getInstance().getReference();
        LlenarLista(Auth.getCurrentUser().getUid().toString());

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





    }

    private void LlenarLista(String Uid) {

        final String[] nombreConductor = new String[1];
        final String[] cedulaConductor = new String[1];


        db.child("Conductores").child(Uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists()){


                    CampoNombre.setText(snapshot.child("nombre").getValue().toString());
                    CampoCedula.setText(snapshot.child("cedula").getValue().toString());
                    CampoEdad.setText(snapshot.child("edad").getValue().toString());
                    CampoGenero.setText(snapshot.child("genero").getValue().toString());
                    CampoEstadocivil.setText("Estado civil: "+ snapshot.child("estadocivil").getValue().toString());
                    CampoLugarLaboral.setText(snapshot.child("laboral").getValue().toString());
                    CampoResidencia.setText(snapshot.child("residencia").getValue().toString());
                    CampoDiasLaborales.setText(snapshot.child("diaslaborales").getValue().toString());

                    CampoModelo.setText(snapshot.child("modelo").getValue().toString());
                    CampoMarca.setText(snapshot.child("marca").getValue().toString());
                    CampoPlaca.setText(snapshot.child("placa").getValue().toString());
                    CampoImplementos.setText(snapshot.child("implementos").getValue().toString());











                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });






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