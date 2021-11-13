package com.example.BikerMice;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.BikerMice.entidades.Conductor;
import com.example.BikerMice.utilidades.Utilidades;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class PerfilConductorActivity extends AppCompatActivity {

    String cedulaConductor,NumeroTelefono;
    ConexionSQLiteHelper conn;
    TextView NombreConductor,Calificacion;
    TextView TituloBienvenida;
    ImageView EditarPerfil,Regresar,CerrarSesion,FotoConductor,FotoMoto,Llamar;
    Bundle BundleRegreso;
    Bundle MiBundle;
    Button Comentar;
    EditText Comentario;
    String señal;
    ListView ListaComentario;
    RatingBar BarraCalificacion;
    ArrayList<String> listaInformacion;
    float Rating;
    String cedula="";

    FirebaseAuth Auth;
    DatabaseReference db;

    protected void  onCreate(Bundle SavedInstanceStatus){

        super.onCreate(SavedInstanceStatus);
        setContentView(R.layout.perfil_conductor);



        NombreConductor=findViewById(R.id.textViewNombreConductor);
        TituloBienvenida=findViewById(R.id.textViewBienvenida);
        EditarPerfil=findViewById(R.id.imageViewEditarPerfilConductor);
        CerrarSesion=findViewById(R.id.imageViewCerrarSesionConductor);
        Regresar=findViewById(R.id.imageViewRegresar);
        Comentar=findViewById(R.id.BtnComentar);
        Comentario=findViewById(R.id.editTextComentario);
        FotoConductor=findViewById(R.id.imageViewImagenConductor);
        FotoMoto=findViewById(R.id.imageViewImagenMoto);
        Llamar=findViewById(R.id.imageViewLlamar);
        ListaComentario=findViewById(R.id.ListaComentarios);
        BarraCalificacion=findViewById(R.id.ratingBarCalificacion);
        Calificacion=findViewById(R.id.textViewPuntuacion);

        Auth = FirebaseAuth.getInstance();
        db=FirebaseDatabase.getInstance().getReference();



        MiBundle = this.getIntent().getExtras();


        señal =MiBundle.getString("señal");
        cedula=MiBundle.getString("cedula");



        BarraCalificacion.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean b) {

              Calificar(cedula,rating);


            }
        });




       CargarDatos(Auth.getCurrentUser().getUid(),señal,cedula);





    }

    private void Calificar(String cedulaConductor, float rating) {



        Map<String,Object> map =new HashMap<>();


        map.put("cedula",cedulaConductor);
        map.put("rating",rating);



        db.child("Calificacion").push().setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful()){

                    SetearPuntaje(cedulaConductor);


                }else{

                    //  Toast.makeText(getApplicationContext(), "Registro fallido", Toast.LENGTH_SHORT).show();

                }

            }
        });










        Toast.makeText(getApplicationContext(),"Calificacion guardada",Toast.LENGTH_SHORT).show();




    }

    private void SetearPuntaje(String cedulaConductor) {


        db.child("Calificacion").orderByChild("cedula").equalTo(cedulaConductor).addListenerForSingleValueEvent(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {

                    double TotalCalificacion= (double) snapshot.getChildrenCount();

                    int SumaCalificacion=0;

                    for (DataSnapshot ds : snapshot.getChildren()){

                       Long valor = (Long) ds.child("rating").getValue();

                       SumaCalificacion= (int) (SumaCalificacion + valor);

                    }

                    double promedio= SumaCalificacion/TotalCalificacion;

                    String casteo = String.valueOf(promedio);
                    String calificacionFinal=casteo.substring(0,3);


                    Calificacion.setText(calificacionFinal);







                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });











    }

    private void CargarDatos(String Uid,String señal,String cedula) {


        final String[] cedulaComentarios = {""};
        String comparacion="1";
        String comparacion2="2";
        final String[] nombre = new String[1];


        //AQUI SE ARMA LA VISTA DESDE PASAJERO
        if(señal.equals(comparacion)){

            db.child("Conductores").orderByChild("cedula").equalTo(cedula).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    if(snapshot.exists()){

                        for (DataSnapshot ds : snapshot.getChildren()){

                            nombre[0] =    ds.child("nombre").getValue().toString();
                            NumeroTelefono =ds.child("telefono").getValue().toString();

                            //SE SETEA LAS IMAGENES

                            String LinkFotoConductor = ds.child("linkfotoconductor").getValue().toString();
                            Glide.with(PerfilConductorActivity.this).load(LinkFotoConductor).into(FotoConductor);


                            String LinkFotoMoto = ds.child("linkfotomoto").getValue().toString();
                            Glide.with(PerfilConductorActivity.this).load(LinkFotoMoto).into(FotoMoto);







                        }



                        TituloBienvenida.setText("Conductor");
                        NombreConductor.setText(nombre[0]);
                        EditarPerfil.setVisibility(View.INVISIBLE);
                        CerrarSesion.setVisibility(View.INVISIBLE);

                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            MostrarComentario(cedula);
            SetearPuntaje(cedula);

        }


        //AQUI SE ARMA LA VISTA DESDE CONDUCTOR
        if(señal.equals(comparacion2)){

            Uid= Auth.getCurrentUser().getUid().toString();

            db.child("Conductores").child(Uid).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    if(snapshot.exists()){

                        String nombre=snapshot.child("nombre").getValue().toString();
                        cedulaComentarios[0] =snapshot.child("cedula").getValue().toString();
                        TituloBienvenida.setText("Welcome");
                        NombreConductor.setText(nombre);
                        EditarPerfil.setVisibility(View.VISIBLE);
                        CerrarSesion.setVisibility(View.VISIBLE);
                        Regresar.setVisibility(View.INVISIBLE);
                        Comentario.setVisibility(View.INVISIBLE);
                        Comentar.setVisibility(View.INVISIBLE);
                        Llamar.setVisibility(View.INVISIBLE);
                        BarraCalificacion.setVisibility(View.INVISIBLE);
                        MostrarComentario(cedulaComentarios[0]);
                        SetearPuntaje(cedulaComentarios[0]);


                        String LinkFotoConductor = snapshot.child("linkfotoconductor").getValue().toString();
                        String LinkFotoMoto = snapshot.child("linkfotomoto").getValue().toString();



                        //SE SETEA LAS IMAGENES
                        Glide.with(PerfilConductorActivity.this).load(LinkFotoConductor).into(FotoConductor);
                        Glide.with(PerfilConductorActivity.this).load(LinkFotoMoto).into(FotoMoto);

                    }



                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }


            });











        }





    }


    public void onClickRegresar(View view) {

        Intent MiIntent = new Intent(getApplicationContext(),ResultadoBusquedaActivity.class);

        Bundle MiBundle2=new Bundle();

        MiBundle2.putString("edad",MiBundle.getString("edad"));
        MiBundle2.putString("genero",MiBundle.getString("genero"));
        MiBundle2.putString("LugarLaboral",MiBundle.getString("LugarLaboral"));
        MiBundle2.putString("modelo",MiBundle.getString("modelo"));

        MiIntent.putExtras(MiBundle2);

        startActivity(MiIntent);
        finish();


    }

    public void OnclickCerrarSesionConductor(View view) {

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

    public void onClickMasInformacion(View view) {

        Bundle Informacion = new Bundle();


        Informacion.putString("cedula",cedula);
        Informacion.putString("señaldevolver",señal);
        Informacion.putString("edad",MiBundle.getString("edad"));
        Informacion.putString("genero",MiBundle.getString("genero"));
        Informacion.putString("LugarLaboral",MiBundle.getString("LugarLaboral"));
        Informacion.putString("modelo",MiBundle.getString("modelo"));



        Intent MiIntent = new Intent(getApplicationContext(),InformacionConductorActivity.class);
        MiIntent.putExtras(Informacion);
        startActivity(MiIntent);
        finish();

    }

    public void onClickEditarPerfilConductor(View view) {

        Intent MiIntent =new Intent(getApplicationContext(),RegistroConductorActivity.class);

        Bundle MiBundle=new Bundle();
        MiBundle.putString("bandera","1");
        MiBundle.putString("Uid",Auth.getCurrentUser().getUid().toString());

        MiIntent.putExtras(MiBundle);

        startActivity(MiIntent);
        finish();



    }

    public void onClickComentar(View view) {

        GuardarComentario();

    }

    private void GuardarComentario() {

        String ComentarioSubir = Comentario.getText().toString();

        if (ComentarioSubir.equals("")){


            Toast.makeText(getApplicationContext(),"Escribe un comentario primero",Toast.LENGTH_SHORT).show();


        }else{

            Date c = Calendar.getInstance().getTime();

            SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
            String Fecha = df.format(c);

            Map<String,Object> map =new HashMap<>();

            map.put("fecha",Fecha);
            map.put("comentario",Comentario.getText().toString());
            map.put("cedula",cedula);



            db.child("Comentarios").push().setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                    if(task.isSuccessful()){



                      MostrarComentario(cedula);


                    }else{

                      //  Toast.makeText(getApplicationContext(), "Registro fallido", Toast.LENGTH_SHORT).show();

                    }

                }
            });







            Comentario.setText("");





        }

    }

    private void MostrarComentario(String cedulaConductor) {



        listaInformacion = new ArrayList<String>();

        db.child("Comentarios").orderByChild("cedula").equalTo(cedulaConductor).limitToLast(5).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {


                    for (DataSnapshot ds : snapshot.getChildren()) {


                        String fecha = ds.child("fecha").getValue().toString();
                        String comentario =ds.child("comentario").getValue().toString();

                        String Comment="";

                        Comment+= fecha+"\n"+comentario;



                        listaInformacion.add(Comment);



                    }
                    Collections.reverse(listaInformacion);
                    ListaComentario.setAdapter(new Adaptador(getApplicationContext(),listaInformacion));




                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });





       // SetearPuntaje(cedulaConductor);






    }

    public void onClickLlamar(View view) {

        String tel ="tel:"+NumeroTelefono;

        Intent MiIntent = new Intent(Intent.ACTION_DIAL);
        MiIntent.setData(Uri.parse(tel));
        startActivity(MiIntent);



    }
}
