package com.example.BikerMice;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class PerfilPasajeroActivity extends AppCompatActivity {

    TextView NombreBienvenida;
    ImageView FotoPasajero;
    Spinner Genero,LugarLaboral;
    EditText Edad,Modelo;

    String Uid,GeneroBusqueda,LaboralBusqueda;
    DatabaseReference bd;
    FirebaseAuth Auth;

    protected void  onCreate(Bundle SavedInstanceStatus) {

        super.onCreate(SavedInstanceStatus);
        setContentView(R.layout.perfil_pasajero);

        NombreBienvenida = findViewById(R.id.textViewNombreBienvenida);
        FotoPasajero =findViewById(R.id.imageViewFotoPasajeroRegistro);
        Genero = findViewById(R.id.spinnerGeneroBusqueda);
        LugarLaboral = findViewById(R.id.spinnerResidenciaBusqueda);
        Edad=findViewById(R.id.EdadBusquedadConductor);
        Modelo=findViewById(R.id.BusquedaModeloMoto);
        bd= FirebaseDatabase.getInstance().getReference();
        Auth=FirebaseAuth.getInstance();

        Bundle MiBundle = this.getIntent().getExtras();

        if (MiBundle != null) {

            Uid = MiBundle.getString("Uid");

        //    Toast.makeText(getApplicationContext(),"soy cedula pasajero"+cedula,Toast.LENGTH_LONG);

            CargarDatos(Uid);



        }


        //ESTA PARTE ES LA QUE ME HACE FUNCIONAR EL SPINNER DE GENERO

        ArrayAdapter<CharSequence> adapterGenero=ArrayAdapter.createFromResource(this,
                R.array.combo_genero, R.layout.spinner_item_general);

        Genero.setAdapter(adapterGenero);

        Genero.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                GeneroBusqueda= parent.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        //ESTA PARTE ES LA QUE ME HACE FUNCIONAR EL SPINNER DE LUGAR LABORAL

        ArrayAdapter<CharSequence> adapterLugarResidenciaPasajero=ArrayAdapter.createFromResource(this,
                R.array.combo_lugarLaboral, R.layout.spinner_item_general);

        LugarLaboral.setAdapter(adapterLugarResidenciaPasajero);

        LugarLaboral.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                LaboralBusqueda= parent.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



    }

    private void CargarDatos(String Uid) {


        bd.child("Users").child(Uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists()){



                    String nombre=snapshot.child("nombre").getValue().toString();
                    String LinkFoto = snapshot.child("linkfoto").getValue().toString();
                    Glide.with(PerfilPasajeroActivity.this).load(LinkFoto).into(FotoPasajero);
                    NombreBienvenida.setText(nombre);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });






    }

    public void onClickmapSitio(View view){

      Intent MiIntent = new Intent(getApplicationContext(),MapsActivity.class);
      startActivity(MiIntent);
    }


    public void onClickEditarPasajero(View view){

        Intent MiIntent = new Intent(getApplicationContext(),RegistroPasajerosActivity.class);

        Bundle MiBundle=new Bundle();
        MiBundle.putString("bandera","1");
        MiBundle.putString("Uid",Auth.getCurrentUser().getUid());

        MiIntent.putExtras(MiBundle);

        startActivity(MiIntent);

    }

    public void onClickCerrarSesion(View view) {

        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setMessage("¿Desea cerrar sesion?").setPositiveButton(
                "Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                       Auth.signOut();

                       Intent MiIntent = new Intent(getApplicationContext(),MainActivity.class);
                       startActivity(MiIntent);
                       finish();


                    }
                }
        ).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.dismiss();
            }
        });

        builder.show();




    }

    public void BuscarConductor(View view) {

        String comparacion="";
        String GeneroComparacion="Genero";
        String LaboralComparacion="Lugar laboral";

        String edad =Edad.getText().toString();
        String Genero = GeneroBusqueda;
        String Laboral=LaboralBusqueda;
        String modelo=Modelo.getText().toString();

        Bundle MiBundle=new Bundle();


        if (!edad.equals(comparacion)){

            MiBundle.putString("edad", Edad.getText().toString());

        }

        if (!Genero.equals(comparacion)){

            MiBundle.putString("genero",GeneroBusqueda);

        }

        if (!LugarLaboral.equals(comparacion)){

            MiBundle.putString("LugarLaboral",LaboralBusqueda);

        }

        if (!modelo.equals(comparacion)){

            MiBundle.putString("modelo",modelo);

        }


        Intent MiIntent = new Intent(getApplicationContext(),ResultadoBusquedaActivity.class);


        MiIntent.putExtras(MiBundle);

        startActivity(MiIntent);
        finish();






    }


}