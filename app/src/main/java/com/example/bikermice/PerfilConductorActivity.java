package com.example.bikermice;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
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

import com.example.bikermice.utilidades.Utilidades;

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

    protected void  onCreate(Bundle SavedInstanceStatus){

        super.onCreate(SavedInstanceStatus);
        setContentView(R.layout.perfil_conductor);

        conn = new ConexionSQLiteHelper(getApplicationContext(),"dbBikerMice2",null,1);

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


        BundleRegreso=this.getIntent().getExtras();
        Bundle MiBundle = this.getIntent().getExtras();
        String seña ="1";
        cedulaConductor =MiBundle.getString("cedula");
        señal =MiBundle.getString("señal");



        BarraCalificacion.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean b) {

                Calificar(cedulaConductor,rating);
                SetearPuntaje(cedulaConductor);

            }
        });




        CargarDatos(cedulaConductor,señal);


        ListaComentario.setAdapter(new Adaptador(this,listaInformacion));


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

    private void CargarDatos(String cedula,String señal) {

        String comparacion="1";
        String comparacion2="2";

        SQLiteDatabase db = conn.getReadableDatabase();

        String ConsultaSQL = "select * from " + Utilidades.TABLA_CONDUCTORES + " where cedula = '"+cedula+"'";


        Cursor cursor = db.rawQuery(ConsultaSQL, null);

        cursor.moveToFirst();

        int columnatelefono= cursor.getColumnIndex("telefono");

        NumeroTelefono =cursor.getString(columnatelefono);


        //AQUI SE ARMA LA VISTA DESDE PASAJERO
        if(señal.equals(comparacion)){

            int columna =cursor.getColumnIndex("nombre");

            String nombre= cursor.getString(columna);


            TituloBienvenida.setText("Conductor");
            NombreConductor.setText(nombre);
            EditarPerfil.setVisibility(View.INVISIBLE);
            CerrarSesion.setVisibility(View.INVISIBLE);




        }


        //AQUI SE ARMA LA VISTA DESE CONDUCTOR
        if(señal.equals(comparacion2)){

            int columna =cursor.getColumnIndex("nombre");

            String nombre= cursor.getString(columna);


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


        //AQUI SE CARGAN LAS FOTOS DEL CONDUCTOR

        int columna2 =cursor.getColumnIndex("fotoconductor");

        byte[] Imagen = cursor.getBlob(columna2);



        Bitmap bitmapImage = BitmapFactory.decodeByteArray(Imagen,0,Imagen.length);

        FotoConductor.setImageBitmap(bitmapImage);

        //AQUI SE CARGA LA FOTO DE LA MOTO

        int id_conductor;
        int columnaId=cursor.getColumnIndex("id_conductor");
        id_conductor= cursor.getInt(columnaId);

        String ConsultaSQL2 = "select fotomoto from " + Utilidades.TABLA_MOTOS + " where id_moto = '"+id_conductor+"'";

        Cursor cursor2 = db.rawQuery(ConsultaSQL2, null);

        cursor2.moveToFirst();

        byte[] ImagenMoto = cursor2.getBlob(columnaId);



        Bitmap bitmapImage2 = BitmapFactory.decodeByteArray(ImagenMoto,0,ImagenMoto.length);

        FotoMoto.setImageBitmap(bitmapImage2);


        cursor.close();
        cursor2.close();
        db.close();

        MostrarComentario(cedulaConductor);


    }

    public void onClickEditarConductor(View view){

        Intent MiIntent = new Intent(getApplicationContext(),RegistroConductorActivity.class);
        startActivity(MiIntent);

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
        MiBundle.putString("cedulaConductor",cedulaConductor);

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
