package com.example.bikermice;


import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import androidx.annotation.Nullable;
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

import com.example.bikermice.utilidades.Utilidades;

import java.io.ByteArrayOutputStream;

public class RegistroPasajerosActivity extends AppCompatActivity {

    EditText campoCedula,campoNombre,campoEdad;
    Spinner ComboGenero,ComboLugarResidencia;
    String GeneroPasajero,LugarResidencia;
    ImageView ImagenPasajero;
    Button CrearCuenta;
    TextView TituloVentana;
    String señal;
    Bundle MiBundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registro_pasajero_activity);

        campoCedula= findViewById(R.id.textViewCedulaPasajero);
        campoNombre=findViewById(R.id.TextViewNombrePasajero);
        ComboGenero=findViewById(R.id.spinnerGeneroPasajero);
        campoEdad=findViewById(R.id.TextViewEdadPasajero);
        ComboLugarResidencia= findViewById(R.id.spinnerLugarResidenciaPasajero);
        ImagenPasajero = findViewById(R.id.imageViewFotoPasajero);
        CrearCuenta =findViewById(R.id.BtnCrearCuenta);
        TituloVentana=findViewById(R.id.TituloRegistrarConductor);


         MiBundle = this.getIntent().getExtras();



            //ESTA PARTE ES LA QUE ME HACE FUNCIONAR EL SPINNER DE GENERO

            ArrayAdapter<CharSequence> adapterGenero=ArrayAdapter.createFromResource(this,
                    R.array.combo_genero, R.layout.spinner_item_general);

            ComboGenero.setAdapter(adapterGenero);

            ComboGenero.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                    GeneroPasajero= parent.getItemAtPosition(i).toString();
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });


            //ESTA PARTE ES LA QUE ME HACE FUNCIONAR EL SPINNER DE LUGAR DE RESIDENCIA

            ArrayAdapter<CharSequence> adapterLugarResidenciaPasajero=ArrayAdapter.createFromResource(this,
                    R.array.combo_lugarResidencia, R.layout.spinner_item_general);

            ComboLugarResidencia.setAdapter(adapterLugarResidenciaPasajero);

            ComboLugarResidencia.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                    LugarResidencia= parent.getItemAtPosition(i).toString();
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });






        if(MiBundle!=null) {

            String cedula =MiBundle.getString("cedula");
            señal = MiBundle.getString("bandera").toString();

            cargarDatos(cedula);

        }



    }

    private void cargarDatos(String cedula) {


        ConexionSQLiteHelper conn= new ConexionSQLiteHelper(this,"dbBikerMice2",null,1);
        SQLiteDatabase db = conn.getWritableDatabase();

        String stringQueryPasajero = "Select *  from pasajeros where cedula= "+cedula+"";

        Cursor cursorPasajero = db.rawQuery(stringQueryPasajero,null);

        cursorPasajero.moveToFirst();

        //SE SETEA EL CAMPO CEDULA
        int columna =cursorPasajero.getColumnIndex("cedula");
        String documento= cursorPasajero.getString(columna);
        campoCedula.setText(documento);
        campoCedula.setKeyListener(null);

        //SE SETA EL CAMPO NOMBRE

        int columna2 =cursorPasajero.getColumnIndex("nombre");
        String nombre= cursorPasajero.getString(columna2);
        campoNombre.setText(nombre);

        //SE SETA EL CAMPO EDAD

        int columna3 =cursorPasajero.getColumnIndex("edad");
        String edad= cursorPasajero.getString(columna3);
        campoEdad.setText(edad);

        //SE SETEA EL CAMPO GENERO

        int columna4 =cursorPasajero.getColumnIndex("genero");
        String genero= cursorPasajero.getString(columna4);
        ComboGenero.setSelection(ObtenerPosicionGenero(ComboGenero,genero));

        //SE SETEA EL CAMPO LUGAR DE RESIDENCIA

        int columna5 =cursorPasajero.getColumnIndex("lugarresidencia");
        String residencia= cursorPasajero.getString(columna5);
        ComboLugarResidencia.setSelection(ObtenerPosicionLugarResidencia(ComboLugarResidencia,residencia));

        //SE SETEA EL BOTON CREAR CUENTA

        CrearCuenta.setText("Actualizar perfil");

        //SE SETA EL TITULO DE LA VENTA

        TituloVentana.setText("Editar perfil");

        //SE SETA LA FOTO


        int columna7 =cursorPasajero.getColumnIndex("fotopasajero");

        byte[] Imagen = cursorPasajero.getBlob(columna7);

        Bitmap bitmapImage = BitmapFactory.decodeByteArray(Imagen,0,Imagen.length);

        ImagenPasajero.setImageBitmap(bitmapImage);

        cursorPasajero.close();
        db.close();


    }

    private int ObtenerPosicionLugarResidencia(Spinner comboLugarResidencia, String residencia) {

        //Creamos la variable posicion y lo inicializamos en 0
        int posicion = 0;
        //Recorre el spinner en busca del ítem que coincida con el parametro `String fruta`
        //que lo pasaremos posteriormente
        for (int i = 0; i < comboLugarResidencia.getCount(); i++) {
            //Almacena la posición del ítem que coincida con la búsqueda
            if (comboLugarResidencia.getItemAtPosition(i).toString().equalsIgnoreCase(residencia)) {
                posicion = i;
            }
        }
        //Devuelve un valor entero (si encontro una coincidencia devuelve la
        // posición 0 o N, de lo contrario devuelve 0 = posición inicial)


        return  posicion;

    }

    private int ObtenerPosicionGenero(Spinner comboGenero, String genero) {
        //Creamos la variable posicion y lo inicializamos en 0
        int posicion = 0;
        //Recorre el spinner en busca del ítem que coincida con el parametro `String fruta`
        //que lo pasaremos posteriormente
        for (int i = 0; i < comboGenero.getCount(); i++) {
            //Almacena la posición del ítem que coincida con la búsqueda
            if (comboGenero.getItemAtPosition(i).toString().equalsIgnoreCase(genero)) {
                posicion = i;
            }
        }
        //Devuelve un valor entero (si encontro una coincidencia devuelve la
        // posición 0 o N, de lo contrario devuelve 0 = posición inicial)


        return  posicion;
    }


    public void onClickCrearCuenta(View view) {

        String bandera="1";


        if (bandera.equals(señal)){


            ActualizarPerfil();
            Intent MyIntent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(MyIntent);
            finish();



        }else{

            RegistrarUsuarios();

            Intent MyIntent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(MyIntent);
            finish();

        }







    }

    private void ActualizarPerfil() {

        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(this,"dbBikerMice2",null,1);

        SQLiteDatabase db= conn.getWritableDatabase();

        ContentValues values=new ContentValues();

         byte[] fotoPasajero=CrearBit();
         String[] parametro = {campoCedula.getText().toString()};

        values.put(Utilidades.CAMPO_NOMBRE_PASAJERO,campoNombre.getText().toString());
        values.put(Utilidades.CAMPO_GENERO_PASAJERO,GeneroPasajero);
        values.put(Utilidades.CAMPO_EDAD_PASAJERO,campoEdad.getText().toString());
        values.put(Utilidades.CAMPO_LUGARRESIDENCIA_PASAJERO,LugarResidencia);
        values.put(Utilidades.CAMPO_FOTO,fotoPasajero);

        db.update(Utilidades.TABLA_PASAJEROS,values,Utilidades.CAMPO_CEDULA_PASAJERO+="=?",parametro);
        Toast.makeText(getApplicationContext(),"se actualizo correctamente ",Toast.LENGTH_LONG).show();
        db.close();


    }


    private void RegistrarUsuarios() {

        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(this,"dbBikerMice2",null,1);

        SQLiteDatabase db= conn.getWritableDatabase();

        ContentValues values=new ContentValues();

        byte[] fotoPasajero=CrearBit();

        values.put(Utilidades.CAMPO_CEDULA_PASAJERO,campoCedula.getText().toString());
        values.put(Utilidades.CAMPO_NOMBRE_PASAJERO,campoNombre.getText().toString());
        values.put(Utilidades.CAMPO_GENERO_PASAJERO,GeneroPasajero);
        values.put(Utilidades.CAMPO_EDAD_PASAJERO,campoEdad.getText().toString());
        values.put(Utilidades.CAMPO_LUGARRESIDENCIA_PASAJERO,LugarResidencia);
        values.put(Utilidades.CAMPO_FOTO,fotoPasajero);



        Toast.makeText(getApplicationContext(),"registro exitoso",Toast.LENGTH_LONG).show();

        db.close();



    }

    private byte[] CrearBit() {

        byte[] bytesImage = new byte[0];


            Bitmap bitmap = ((BitmapDrawable) ImagenPasajero.getDrawable()).getBitmap();

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();



            bitmap = redimensionarImagenMaximo(bitmap,640,360);
            bitmap.compress(Bitmap.CompressFormat.PNG, 0, byteArrayOutputStream);

            bytesImage = byteArrayOutputStream.toByteArray();


        return bytesImage;
    }

    public Bitmap redimensionarImagenMaximo(Bitmap mBitmap, float newWidth, float newHeigth){
        //Redimensionamos
        int width = mBitmap.getWidth();
        int height = mBitmap.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeigth) / height;
        // create a matrix for the manipulation
        Matrix matrix = new Matrix();
        // resize the bit map
        matrix.postScale(scaleWidth, scaleHeight);
        // recreate the new Bitmap
        return Bitmap.createBitmap(mBitmap, 0, 0, width, height, matrix, false);
    }


    public void onClickCancelar(View view) {

       finish();

    }

    public void CambiarImagenPasajero(View view) {
        CambiarImagen();

    }

    private void CambiarImagen() {



            Intent MiIntent =new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            MiIntent.setType("image/");

            startActivityForResult(MiIntent.createChooser(MiIntent,"Seleccione la aplicación"),10);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


            Uri path=data.getData();
            ImagenPasajero.setImageURI(path);


    }
}
