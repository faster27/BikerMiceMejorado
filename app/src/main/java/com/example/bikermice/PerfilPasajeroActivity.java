package com.example.bikermice;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import androidx.core.view.MotionEventCompat;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class PerfilPasajeroActivity extends AppCompatActivity {

    TextView NombreBienvenida;
    ImageView FotoPasajero;
    Spinner Genero,LugarLaboral;
    EditText Edad,Modelo;

    String cedula,GeneroBusqueda,LaboralBusqueda;

    protected void  onCreate(Bundle SavedInstanceStatus) {

        super.onCreate(SavedInstanceStatus);
        setContentView(R.layout.perfil_pasajero);

        NombreBienvenida = findViewById(R.id.textViewNombreBienvenida);
        FotoPasajero =findViewById(R.id.imageViewFotoPasajeroRegistro);
        Genero = findViewById(R.id.spinnerGeneroBusqueda);
        LugarLaboral = findViewById(R.id.spinnerResidenciaBusqueda);
        Edad=findViewById(R.id.EdadBusquedadConductor);
        Modelo=findViewById(R.id.BusquedaModeloMoto);

        Bundle MiBundle = this.getIntent().getExtras();

        if (MiBundle != null) {

            cedula = MiBundle.getString("cedula");

            Toast.makeText(getApplicationContext(),"soy cedula pasajero"+cedula,Toast.LENGTH_LONG);

            CargarDatos(cedula);



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

    private void CargarDatos(String cedula) {

        ConexionSQLiteHelper conn= new ConexionSQLiteHelper(this,"dbBikerMice2",null,1);
        SQLiteDatabase db = conn.getWritableDatabase();

        String stringQueryPasajero = "Select nombre, fotopasajero from pasajeros where cedula= "+cedula+"";

        Cursor cursorPasajero = db.rawQuery(stringQueryPasajero,null);

        if (cursorPasajero!=null && cursorPasajero.moveToFirst()){

            cursorPasajero.moveToFirst();

            int columna =cursorPasajero.getColumnIndex("nombre");

            String nombre= cursorPasajero.getString(columna);

            NombreBienvenida.setText(nombre);

            int columna2 =cursorPasajero.getColumnIndex("fotopasajero");

            byte[] Imagen = cursorPasajero.getBlob(columna2);


            Bitmap bitmapImage = BitmapFactory.decodeByteArray(Imagen,0,Imagen.length);

            FotoPasajero.setImageBitmap(bitmapImage);

        }


        cursorPasajero.close();
        db.close();


    }


    public void onClickEditarPasajero(View view){

        Intent MiIntent = new Intent(getApplicationContext(),RegistroPasajerosActivity.class);


        Bundle MiBundle=new Bundle();
        MiBundle.putString("bandera","1");
        MiBundle.putString("cedula",cedula);

        MiIntent.putExtras(MiBundle);

        startActivity(MiIntent);

    }

    public void onClickCerrarSesion(View view) {

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

    public void BuscarConductor(View view) {

        String comparacion="";
        String GeneroComparacion="Genero";
        String LaboralComparacion="Lugar laboral";

        String edad =Edad.getText().toString();
        String Genero = GeneroBusqueda;
        String Laboral=LaboralBusqueda;

        if (edad.equals(comparacion) && Genero.equals(GeneroComparacion) && Laboral.equals(LaboralComparacion)){


            Toast.makeText(getApplicationContext(),"Seleccione al menos un filtro",Toast.LENGTH_LONG).show();


        }else{
            Intent MiIntent = new Intent(getApplicationContext(),ResultadoBusquedaActivity.class);

            Bundle MiBundle=new Bundle();

            MiBundle.putString("cedulapasajero",cedula);
            MiBundle.putString("edad", Edad.getText().toString());
            MiBundle.putString("genero",GeneroBusqueda);
            MiBundle.putString("LugarLaboral",LaboralBusqueda);
            MiBundle.putString("Modelo",Modelo.getText().toString());


            MiIntent.putExtras(MiBundle);



            startActivity(MiIntent);
            finish();

        }







    }


}