package com.example.BikerMice;

import android.content.ContentValues;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.BikerMice.utilidades.Utilidades;
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
import java.util.Date;

public class PerfilConductorActivity extends AppCompatActivity {

    String cedulaConductor,NumeroTelefono;
    ConexionSQLiteHelper conn;
    TextView NombreConductor,Calificacion;
    TextView TituloBienvenida;
    ImageView EditarPerfil,Regresar,CerrarSesion,FotoConductor,FotoMoto,Llamar;
    Bundle BundleRegreso;
    Button Comentar;
    EditText Comentario;
    String señal;
    ListView ListaComentario;
    RatingBar BarraCalificacion;
    ArrayList<String> listaInformacion;
    float Rating;

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


        BundleRegreso=this.getIntent().getExtras();
        Bundle MiBundle = this.getIntent().getExtras();


        señal =MiBundle.getString("señal");



        BarraCalificacion.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean b) {

              //  Calificar(cedulaConductor,rating);
               // SetearPuntaje(cedulaConductor);

            }
        });




       CargarDatos(Auth.getCurrentUser().getUid(),señal);


       // ListaComentario.setAdapter(new Adaptador(this,listaInformacion));


    }

    private void Calificar(String cedulaConductor, float rating) {


        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(this,"dbBikerMice2",null,1);

        SQLiteDatabase db = conn.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(Utilidades.CAMPO_CEDULA_CONDUCTOR,cedulaConductor);
        values.put(Utilidades.CAMPO_PUNTAJE,rating);

        db.insert(Utilidades.TABLA_PUNTAJE,Utilidades.CAMPO_CEDULA_CONDUCTOR,values);


        db.close();


        Toast.makeText(getApplicationContext(),"Calificacion guardada",Toast.LENGTH_SHORT).show();




    }

    private void SetearPuntaje(String cedulaConductor) {



        conn = new ConexionSQLiteHelper(getApplicationContext(),"dbBikerMice2",null,1);

        SQLiteDatabase db = conn.getReadableDatabase();

        String ConsultaSQL = "select AVG(puntaje) from " + Utilidades.TABLA_PUNTAJE + " where cedula_conductor = '"+cedulaConductor+"'";


        Cursor cursor = db.rawQuery(ConsultaSQL, null);

        cursor.moveToFirst();

        float CantidadPuntaje=cursor.getFloat(0);
        String casteo = String.valueOf(CantidadPuntaje);
        String cadena2=casteo.substring(0,3);





        Calificacion.setText(cadena2);




       cursor.close();
       db.close();



    }

    private void CargarDatos(String Uid,String señal) {

        String comparacion="1";
        String comparacion2="2";




        //AQUI SE ARMA LA VISTA DESDE PASAJERO
        if(señal.equals(comparacion)){




            TituloBienvenida.setText("Conductor");
          //  NombreConductor.setText(nombre);
            EditarPerfil.setVisibility(View.INVISIBLE);
            CerrarSesion.setVisibility(View.INVISIBLE);




        }


        //AQUI SE ARMA LA VISTA DESDE CONDUCTOR
        if(señal.equals(comparacion2)){


            db.child("Conductores").child(Uid).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    if(snapshot.exists()){

                        String nombre=snapshot.child("nombre").getValue().toString();
                        TituloBienvenida.setText("Welcome");
                        NombreConductor.setText(nombre);
                        EditarPerfil.setVisibility(View.VISIBLE);
                        CerrarSesion.setVisibility(View.VISIBLE);
                        Regresar.setVisibility(View.INVISIBLE);
                        Comentario.setVisibility(View.INVISIBLE);
                        Comentar.setVisibility(View.INVISIBLE);
                        Llamar.setVisibility(View.INVISIBLE);
                        BarraCalificacion.setVisibility(View.INVISIBLE);

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

        //BUNDLES PARA EL REGRESO
        Bundle MiBundleRegreso=new Bundle();
        MiBundleRegreso.putString("edad",BundleRegreso.getString("edad"));
        MiBundleRegreso.putString("genero",BundleRegreso.getString("genero"));
        MiBundleRegreso.putString("LugarLaboral",BundleRegreso.getString("lugarlaboral"));
        MiBundleRegreso.putString("Modelo",BundleRegreso.getString("modelo"));
        MiBundleRegreso.putString("cedulapasajero",BundleRegreso.getString("cedulapasajero"));

        MiIntent.putExtras(MiBundleRegreso);

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


        SQLiteDatabase db = conn.getReadableDatabase();
        String ConsultaSQL = "select id_conductor from " + Utilidades.TABLA_CONDUCTORES +" where cedula ="+cedulaConductor;


        Cursor cursor = db.rawQuery(ConsultaSQL, null);

        int columna = cursor.getColumnIndex("id_conductor");

        cursor.moveToFirst();

        int id_conductor=cursor.getInt(columna);



        Intent MiIntent = new Intent(getApplicationContext(),InformacionConductorActivity.class);

        Bundle MiBundleRegreso=new Bundle();
        MiBundleRegreso.putString("cedulaConductor",cedulaConductor);
        MiBundleRegreso.putString("señal",señal);
        MiBundleRegreso.putInt("id_conductor",id_conductor);

        MiBundleRegreso.putString("edad",BundleRegreso.getString("edad"));
        MiBundleRegreso.putString("genero",BundleRegreso.getString("genero"));
        MiBundleRegreso.putString("LugarLaboral",BundleRegreso.getString("lugarlaboral"));
        MiBundleRegreso.putString("Modelo",BundleRegreso.getString("modelo"));
        MiBundleRegreso.putString("cedulapasajero",BundleRegreso.getString("cedulapasajero"));

        MiIntent.putExtras(MiBundleRegreso);
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

            ConexionSQLiteHelper conn = new ConexionSQLiteHelper(this,"dbBikerMice2",null,1);

            SQLiteDatabase db = conn.getWritableDatabase();

            ContentValues values = new ContentValues();





            //ESTO VA PARA LA TABLA COMETARIOS

            values.put(Utilidades.CAMPO_FECHA_COMENTARIO,Fecha);
            values.put(Utilidades.CAMPO_CEDULA_CONDUCTOR_COMENTARIO,cedulaConductor);
            values.put(Utilidades.CAMPO_COMENTARIO,ComentarioSubir);



            //ME INSERTA EN LA DB
            db.insert(Utilidades.TABLA_COMENTARIOS,Utilidades.CAMPO_CEDULA_CONDUCTOR_COMENTARIO,values);



            Comentario.setText("");

            db.close();

            MostrarComentario(cedulaConductor);

        }

    }

    private void MostrarComentario(String cedulaConductor) {

        listaInformacion = new ArrayList<String>();

        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(this,"dbBikerMice2",null,1);

        SQLiteDatabase db = conn.getReadableDatabase();

        String ConsultaSQL = "select * from " + Utilidades.TABLA_COMENTARIOS + " where cedulaconductor = "+cedulaConductor;

        Cursor cursor = db.rawQuery(ConsultaSQL, null);

        while (cursor.moveToNext()) {

            String fecha = cursor.getString(1);
            String comentario =cursor.getString(2);

            String Comment="";

            Comment+= fecha+"\n"+comentario;



            listaInformacion.add(Comment);

        }

        SetearPuntaje(cedulaConductor);
        ListaComentario.setAdapter(new Adaptador(this,listaInformacion));


        cursor.close();
        db.close();


    }

    public void onClickLlamar(View view) {

        String tel ="tel:"+NumeroTelefono;

        Intent MiIntent = new Intent(Intent.ACTION_DIAL);
        MiIntent.setData(Uri.parse(tel));
        startActivity(MiIntent);



    }
}
