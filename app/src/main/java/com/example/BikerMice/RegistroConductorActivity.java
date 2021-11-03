package com.example.BikerMice;

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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.BikerMice.utilidades.Utilidades;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

public class RegistroConductorActivity extends AppCompatActivity {

    EditText campoCedula,campoNombre,campoEdad,campotelefono;
    EditText campoImplementos;
    String DiasLaborales="",señal;

    EditText campoEmail;
    EditText campoPassword;

    EditText campoMarca,campoPlaca,campoModelo;

    String generoConductor;
    Spinner ComboGenero;

    String EstadoCivil;
    Spinner ComboEstadoCivil;

    String LugarResidencia;
    Spinner ComboLugarResidencia;

    String LugarLaboral;
    Spinner ComboLugarLaboral;

    TextView titulo,campoDiasLaborales;
    Button BtnActualizarPerfil;

    FirebaseAuth Auth;
    DatabaseReference database;



    int seleccionBoton;



    //AQUI VAN LOS SPINNERS  Y LOS CHECKBOXS

    CheckBox Lunes,Martes,Miercoles,Jueves,Viernes,Sabado,Domingo;
    Spinner HoraInicialLunes, HoraFinalLunes,HoraInicialMartes,HoraFinalMartes,HoraInicialMiercoles,HoraFinalMiercoles;
    Spinner HoraInicialJueves,HoraFinalJueves,HoraInicialViernes,HoraFinalViernes,HoraInicialSabado,HoraFinalSabado;
    Spinner HoraInicialDomingo,HoraFinalDomingo;

    ImageView imagenConductor,imagenMoto;
    Bundle BundleEditarPerfil;


    @Override
    protected void onCreate(Bundle savedInstanceStatus){
        super.onCreate(savedInstanceStatus);
        setContentView(R.layout.registro_conductor_activity);


        //Variables para la base de datos

        Auth=FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference();



        imagenConductor= (ImageView) findViewById(R.id.imageViewFotoPasajero);
        imagenMoto= (ImageView) findViewById(R.id.imageViewFotoMotoConductor);


        //AQUI SE RECIBE EL BUNDLE
        BundleEditarPerfil=this.getIntent().getExtras();

        //OTROS COMPONENTES

        titulo=findViewById(R.id.TituloRegistrarConductor);
        BtnActualizarPerfil=findViewById(R.id.BtnCrearCuenta);
        campoDiasLaborales=findViewById(R.id.textViewHorarioLaboral);



        //PERTENECE A LA INFORMACION PERSONAL DEL CONDUCTOR

        campoCedula= findViewById(R.id.editTextCedulaConductor);
        campoNombre =findViewById(R.id.editTextNombreConductor);
        campoEdad =findViewById(R.id.editTextEdadConductor);
        campotelefono = findViewById(R.id.editTextNumeroTelefonoConductor);
        ComboLugarResidencia = findViewById(R.id.spinnerLugarResidencia);
        ComboLugarLaboral = findViewById(R.id.spinnerLugarLaboral);
        ComboEstadoCivil = findViewById(R.id.spinnerEstadoCivil);
        campoImplementos = findViewById(R.id.editTextImplementos);
        ComboGenero=findViewById(R.id.spinnerGeneroConductor);
        imagenConductor =findViewById(R.id.imageViewFotoPasajero);
        campoEmail=findViewById(R.id.editTextEmailConductor);
        campoPassword=findViewById(R.id.editTextPasswordConductor);



        Lunes = (CheckBox) findViewById(R.id.checkBoxLunes);
        HoraInicialLunes=findViewById(R.id.spinnerHoraInicialLunes);
        HoraFinalLunes=findViewById(R.id.spinnerHoraFinalLunes);

        Martes = (CheckBox) findViewById(R.id.checkBoxMartes);
        HoraInicialMartes=findViewById(R.id.spinnerHoraInicialMartes);
        HoraFinalMartes=findViewById(R.id.spinnerHoraFinalMartes);

        Miercoles = (CheckBox) findViewById(R.id.checkBoxMiercoles);
        HoraInicialMiercoles=findViewById(R.id.spinnerHoraInicialMiercoles);
        HoraFinalMiercoles=findViewById(R.id.spinnerHoraFinalMiercoles);

        Jueves = (CheckBox) findViewById(R.id.checkBoxJueves);
        HoraInicialJueves=findViewById(R.id.spinnerHoraInicialJueves);
        HoraFinalJueves=findViewById(R.id.spinnerHoraFinalJueves);

        Viernes = (CheckBox) findViewById(R.id.checkBoxViernes);
        HoraInicialViernes=findViewById(R.id.spinnerHoraInicialViernes);
        HoraFinalViernes=findViewById(R.id.spinnerHoraFinalViernes);

        Sabado = (CheckBox) findViewById(R.id.checkBoxSabado);
        HoraInicialSabado=findViewById(R.id.spinnerHoraInicialSabado);
        HoraFinalSabado=findViewById(R.id.spinnerHoraFinalSabado);

        Domingo = (CheckBox) findViewById(R.id.checkBoxDomingo);
        HoraInicialDomingo=findViewById(R.id.spinnerHoraInicialDomingo);
        HoraFinalDomingo=findViewById(R.id.spinnerHoraFinalDomingo);



        //PERTENECE A LA INFORMACION DE LA MOTO

        campoMarca = findViewById(R.id.editTextMarcaMoto);
        campoModelo = findViewById(R.id.editTextModeloMoto);
        campoPlaca = findViewById(R.id.editTextPlacaMoto);

        //ESTA PARTE ES LA QUE ME HACE FUNCIONAR EL SPINNER DE GENERO

        ArrayAdapter<CharSequence> adapterGenero=ArrayAdapter.createFromResource(this,
                R.array.combo_genero, R.layout.spinner_item_general);

        ComboGenero.setAdapter(adapterGenero);

        ComboGenero.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                generoConductor = parent.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        //ESTA PARTE HACE FUNCIONAR EL SPINNER DE ESTADO CIVIL
        ArrayAdapter<CharSequence> adapterEstadoCivil=ArrayAdapter.createFromResource(this,
                R.array.combo_estadoCivil, R.layout.spinner_item_general);

        ComboEstadoCivil.setAdapter(adapterEstadoCivil);

        ComboEstadoCivil.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                EstadoCivil = parent.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //ESTA PARTE HACE FUNCIONAR EL SPINNER LUGAR RESIDENCIA
        ArrayAdapter<CharSequence> adapterLugarResidencia=ArrayAdapter.createFromResource(this,
                R.array.combo_lugarResidencia, R.layout.spinner_item_general);

        ComboLugarResidencia.setAdapter(adapterLugarResidencia);

        ComboLugarResidencia.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                LugarResidencia = parent.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //ESTA PARTE HACE FUNCIONAR EL SPINNER DE LUGAR LABORAL
        ArrayAdapter<CharSequence> adapterLugarLaboral=ArrayAdapter.createFromResource(this,
                R.array.combo_lugarLaboral, R.layout.spinner_item_general);

        ComboLugarLaboral.setAdapter(adapterLugarLaboral);

        ComboLugarLaboral.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                LugarLaboral = parent.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //ADAPTADORES PARA LOS SPINNERS

        ArrayAdapter<CharSequence> adapterHoraInicio=ArrayAdapter.createFromResource(this,
                R.array.combo_HoraInicio, R.layout.spinner_item_general);

        ArrayAdapter<CharSequence> adapterHoraFinal=ArrayAdapter.createFromResource(this,
                R.array.combo_HoraFinal, R.layout.spinner_item_general);

        HoraInicialLunes.setAdapter(adapterHoraInicio);
        HoraFinalLunes.setAdapter(adapterHoraFinal);

        HoraInicialMartes.setAdapter(adapterHoraInicio);
        HoraFinalMartes.setAdapter(adapterHoraFinal);

        HoraInicialMiercoles.setAdapter(adapterHoraInicio);
        HoraFinalMiercoles.setAdapter(adapterHoraFinal);

        HoraInicialJueves.setAdapter(adapterHoraInicio);
        HoraFinalJueves.setAdapter(adapterHoraFinal);

        HoraInicialViernes.setAdapter(adapterHoraInicio);
        HoraFinalViernes.setAdapter(adapterHoraFinal);

        HoraInicialSabado.setAdapter(adapterHoraInicio);
        HoraFinalSabado.setAdapter(adapterHoraFinal);

        HoraInicialDomingo.setAdapter(adapterHoraInicio);
        HoraFinalDomingo.setAdapter(adapterHoraFinal);


            HoraInicialLunes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {

                    if(Lunes.isChecked()) {
                        DiasLaborales += "Lunes: " + parent.getItemAtPosition(i).toString();


                    }

                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });





        HoraFinalLunes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                if (Lunes.isChecked()){

                    DiasLaborales+=" a "+parent.getItemAtPosition(i).toString()+"\n";





                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        //ESTA PARTE HACE FUNCIONAR LOS SPINNERS DEL DIA MARTES




        HoraInicialMartes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                if (Martes.isChecked()){

                    DiasLaborales+="Martes: "+parent.getItemAtPosition(i).toString();



                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });




        HoraFinalMartes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                if (Martes.isChecked()){
                    DiasLaborales+=" a "+parent.getItemAtPosition(i).toString()+"\n";


                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        //ESTA PARTE HACE FUNCIONAR LOS SPINNERS DEL DIA MIERCOLES




        HoraInicialMiercoles.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                if (Miercoles.isChecked()){
                    DiasLaborales+="Miercoles: "+parent.getItemAtPosition(i).toString();


                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });




        HoraFinalMiercoles.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                if (Miercoles.isChecked()){

                    DiasLaborales+=" a "+parent.getItemAtPosition(i).toString()+"\n";


                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //ESTA PARTE HACE FUNCIONAR LOS SPINNERS DEL DIA JUEVES




        HoraInicialJueves.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                if (Jueves.isChecked()){
                    DiasLaborales+="Jueves: "+parent.getItemAtPosition(i).toString();



                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });




        HoraFinalJueves.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                if (Jueves.isChecked()){

                    DiasLaborales+=" a "+parent.getItemAtPosition(i).toString()+"\n";


                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //ESTA PARTE HACE FUNCIONAR LOS SPINNERS DEL DIA VIERNES




        HoraInicialViernes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                if (Viernes.isChecked()){
                    DiasLaborales+="Viernes: "+parent.getItemAtPosition(i).toString();



                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });




        HoraFinalViernes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                if (Viernes.isChecked()){

                    DiasLaborales+=" a "+parent.getItemAtPosition(i).toString()+"\n";


                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //ESTA PARTE HACE FUNCIONAR LOS SPINNERS DEL DIA SABADO




        HoraInicialSabado.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                if (Sabado.isChecked()){
                    DiasLaborales+="Sabado: "+parent.getItemAtPosition(i).toString();



                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });




        HoraFinalSabado.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                if (Sabado.isChecked()){

                    DiasLaborales+=" a "+parent.getItemAtPosition(i).toString()+"\n";


                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //ESTA PARTE HACE FUNCIONAR LOS SPINNERS DEL DIA DOMINGO




        HoraInicialDomingo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                if (Domingo.isChecked()){
                    DiasLaborales+="Domingo: "+parent.getItemAtPosition(i).toString();



                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        HoraFinalDomingo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                if (Domingo.isChecked()){


                    DiasLaborales+=" a "+parent.getItemAtPosition(i).toString();


                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        //ESTA SECCION ES PARA CUANDO SE PIDA EDITAR EL PERFIL

        if(BundleEditarPerfil!=null){


            String cedula =BundleEditarPerfil.getString("cedulaConductor");
            señal =BundleEditarPerfil.getString("bandera");


            cargarDatos(cedula);



        }


    }

    private void cargarDatos(String cedula) {

        //PRIMERO SE PREARA LA INTERFAZ GRAFICA SETEANDO TITULOS Y BOTONES

        BtnActualizarPerfil.setText("ACTUALIZAR PERFIL");
        titulo.setText("Editar perfil");




        ConexionSQLiteHelper conn= new ConexionSQLiteHelper(this,"dbBikerMice2",null,1);
        SQLiteDatabase db = conn.getWritableDatabase();

        String stringQueryConductor = "Select *  from "+Utilidades.TABLA_CONDUCTORES+" where cedula= "+cedula+"";

        Cursor cursorConductor = db.rawQuery(stringQueryConductor,null);

        cursorConductor.moveToFirst();


        //AQUI SE TRAE LA INFORMACION DE LA MOTO

        int id_conductor;
        int columnaId=cursorConductor.getColumnIndex("id_conductor");
        id_conductor= cursorConductor.getInt(columnaId);

        String ConsultaSQLMoto = "select * from " + Utilidades.TABLA_MOTOS + " where id_moto = '"+id_conductor+"'";
        Cursor cursorMoto = db.rawQuery(ConsultaSQLMoto,null);

        cursorMoto.moveToFirst();


        //SE SETA EL CAMPO NOMBRE

        int columna =cursorConductor.getColumnIndex("nombre");
        String nombre= cursorConductor.getString(columna);
        campoNombre.setText(nombre);

        //SE SETA EL CAMPO CEDULA

        int columna2 =cursorConductor.getColumnIndex("cedula");
        String cedulaConductor= cursorConductor.getString(columna2);
        campoCedula.setText(cedulaConductor);
        campoCedula.setKeyListener(null);

        //SE SETA EL CAMPO GENERO

        int columna3 =cursorConductor.getColumnIndex("genero");
        String genero= cursorConductor.getString(columna3);
        ComboGenero.setSelection(ObtenerPosicionSpinner(ComboGenero,genero));

        //SE SETA EL CAMPO EDAD

        int columna4 =cursorConductor.getColumnIndex("edad");
        String edad= cursorConductor.getString(columna4);
        campoEdad.setText(edad);

        //SE SETA EL CAMPO TELEFONO

        int columna5 =cursorConductor.getColumnIndex("telefono");
        String telefono= cursorConductor.getString(columna5);
        campotelefono.setText(telefono);


        //SE SETA EL CAMPO ESTADO CIVIL

        int columna6 =cursorConductor.getColumnIndex("estadocivil");
        String estadocivil= cursorConductor.getString(columna6);
        ComboEstadoCivil.setSelection(ObtenerPosicionSpinner(ComboEstadoCivil,estadocivil));

        //SE SETA EL CAMPO LUGAR RESIDENCIA

        int columna7 =cursorConductor.getColumnIndex("lugarresidencia");
        String lugarresidencia= cursorConductor.getString(columna7);
        ComboLugarResidencia.setSelection(ObtenerPosicionSpinner(ComboLugarResidencia,lugarresidencia));

        //SE SETA EL CAMPO LUGAR LABORAL

        int columna8 =cursorConductor.getColumnIndex("lugarlaboral");
        String LugarLaboral= cursorConductor.getString(columna8);
        ComboLugarLaboral.setSelection(ObtenerPosicionSpinner(ComboLugarLaboral,LugarLaboral));

        //SE SETA EL CAMPO MODELO

        int columna9 =cursorMoto.getColumnIndex("modelo");
        String modelo= cursorMoto.getString(columna9);
        campoModelo.setText(modelo);

        //SE SETA EL CAMPO MARCA

        int columna10 =cursorMoto.getColumnIndex("marcamoto");
        String marcaMoto= cursorMoto.getString(columna10);
        campoMarca.setText(marcaMoto);

        //SE SETA EL CAMPO PLACA

        int columna11 =cursorMoto.getColumnIndex("placa");
        String placa= cursorMoto.getString(columna11);
        campoPlaca.setText(placa);


        //SE SETA EL CAMPO IMPLEMENTOS

        int columna12 =cursorConductor.getColumnIndex("implementos");
        String implementos= cursorConductor.getString(columna12);
        campoImplementos.setText(implementos);

        //SE SETA EL CAMPO HORARIOLABORAL

        int columna15 =cursorConductor.getColumnIndex("diaslaborales");
        String diaslaborales= cursorConductor.getString(columna15);
        campoDiasLaborales.setText(diaslaborales);



        //SE SETA LA FOTO DEL CONDUCTOR

        int columna13 =cursorConductor.getColumnIndex("fotoconductor");

        byte[] Imagen = cursorConductor.getBlob(columna13);

       

        Bitmap bitmapImage = BitmapFactory.decodeByteArray(Imagen,0,Imagen.length);

        imagenConductor.setImageBitmap(bitmapImage);


        //SE SETA LA FOTO DE LA MOTO

        int columna14 =cursorMoto.getColumnIndex("fotomoto");

        byte[] Imagen2 = cursorMoto.getBlob(columna14);



        Bitmap bitmapImage2 = BitmapFactory.decodeByteArray(Imagen2,0,Imagen2.length);

        imagenMoto.setImageBitmap(bitmapImage2);


        cursorConductor.close();

        cursorMoto.close();

        db.close();





    }

    private int ObtenerPosicionSpinner(Spinner Spinner, String Buscar) {

        //Creamos la variable posicion y lo inicializamos en 0
        int posicion = 0;
        //Recorre el spinner en busca del ítem que coincida con el parametro `String fruta`
        //que lo pasaremos posteriormente
        for (int i = 0; i < Spinner.getCount(); i++) {
            //Almacena la posición del ítem que coincida con la búsqueda
            if (Spinner.getItemAtPosition(i).toString().equalsIgnoreCase(Buscar)) {
                posicion = i;
            }
        }
        //Devuelve un valor entero (si encontro una coincidencia devuelve la
        // posición 0 o N, de lo contrario devuelve 0 = posición inicial)


        return  posicion;

    }

    public void onClickCrearCuentaConductor(View view){

        String bandera="1";

        if(bandera.equals(señal)){



            ActualizarPerfil();
            Intent MyIntent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(MyIntent);
            finish();


        }else{

            RegistrarConductor();



        }









    }

    private void ActualizarPerfil() {

        ConexionSQLiteHelper conn=new ConexionSQLiteHelper(this,"dbBikerMice2",null,1);

        SQLiteDatabase db= conn.getWritableDatabase();

        ContentValues values = new ContentValues();
        ContentValues values2 = new ContentValues();

        //CONSULTA PARA SABER EL ID DEL CONDUCTOR
        String stringQuery = "Select id_conductor from conductores where cedula= "+campoCedula.getText().toString()+"";

        Cursor cursorConductor = db.rawQuery(stringQuery,null);

        cursorConductor.moveToFirst();
        int columna =cursorConductor.getColumnIndex("id_conductor");

        String id_conductor= cursorConductor.getString(columna);
        ////////////////////////////////////////////////////////////////////////////


        //AQUI EMPIEZA EL PROCESO DE ACTUALIZACION
        byte[] fotoConductor=CrearBit(1);
        byte[] fotoMoto=CrearBit(2);

        String DiasLaborales2 = SacarDias();


        String[] parametro = {campoCedula.getText().toString()};
        String[] parametro2 = {id_conductor};

        //ESTO VA PARA LA TABLA CONDUCTOR

        values.put(Utilidades.CAMPO_CEDULA,campoCedula.getText().toString());
        values.put(Utilidades.CAMPO_NOMBRE,campoNombre.getText().toString());
        values.put(Utilidades.CAMPO_GENERO,generoConductor);
        values.put(Utilidades.CAMPO_EDAD,campoEdad.getText().toString());
        values.put(Utilidades.CAMPO_TELEFONO,campotelefono.getText().toString());
        values.put(Utilidades.CAMPO_LUGARRESIDENCIA,LugarResidencia);
        values.put(Utilidades.CAMPO_LUGARLABORAL,LugarLaboral);
        values.put(Utilidades.CAMPO_ESTADOCIVIL,EstadoCivil);
        values.put(Utilidades.CAMPO_IMPLEMENTOS,campoImplementos.getText().toString());
        values.put(Utilidades.CAMPO_DIASLABORALES,DiasLaborales2);
        values.put(Utilidades.CAMPO_FOTO_CONDUCTOR,fotoConductor);

        //ESTO VA PARA LA TABLA MOTO

        values2.put(Utilidades.CAMPO_MARCAMOTO,campoMarca.getText().toString());
        values2.put(Utilidades.CAMPO_MODELO,campoModelo.getText().toString());
        values2.put(Utilidades.CAMPO_PLACA,campoPlaca.getText().toString());
        values2.put(Utilidades.CAMPO_FOTOMOTO,fotoMoto);

        db.update(Utilidades.TABLA_CONDUCTORES,values,Utilidades.CAMPO_CEDULA+="=?",parametro);
        db.update(Utilidades.TABLA_MOTOS,values2,Utilidades.CAMPO_ID_MOTO+="=?",parametro2);
        Toast.makeText(getApplicationContext(),"se actualizo correctamente ",Toast.LENGTH_LONG).show();

        cursorConductor.close();
        db.close();


    }


    public void onClickCancelarConductor(View view){

        finish();

    }

    public void onclickCambiarImagenConductor(View view){
        seleccionBoton=1;
        cargarImagen();

    }

    public void onclickCambiarImagenMotoConductor(View view){
        seleccionBoton=2;
        cargarImagen();

    }



    private void cargarImagen() {

        Intent MiIntent =new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        MiIntent.setType("image/");

        startActivityForResult(MiIntent.createChooser(MiIntent,"Seleccione la aplicación"),10);


    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);



        if(seleccionBoton==1 &&   resultCode==RESULT_OK){

            Uri path=data.getData();
            imagenConductor.setImageURI(path);


        }

        if(seleccionBoton==2 &&   resultCode==RESULT_OK){

            Uri path=data.getData();
            imagenMoto.setImageURI(path);


        }




    }



    private void RegistrarConductor() {

        String genero = "Genero";
        String residencia="Lugar de residencia";
        String civil="Estado civil";
        String laboral="Lugar laboral";
        String DiasLaborales2 = SacarDias();

        if(!campoCedula.getText().toString().isEmpty() &&
                !campoNombre.getText().toString().isEmpty() &&
                !campoEdad.getText().toString().isEmpty() &&
                !campoEmail.getText().toString().isEmpty() &&
                !campoPassword.getText().toString().isEmpty() &&
                !generoConductor.equals(genero) &&
                !LugarResidencia.equals(residencia) &&
                !EstadoCivil.equals(civil) &&
                !campotelefono.getText().toString().isEmpty() &&
                !LugarLaboral.equals(laboral) &&
                !campoMarca.getText().toString().isEmpty() &&
                !campoModelo.getText().toString().isEmpty() &&
                !campoPlaca.getText().toString().isEmpty() &&
                !DiasLaborales2.equals("")


        ){
            if(campoPassword.getText().toString().length()>=6) {

                Auth.createUserWithEmailAndPassword(campoEmail.getText().toString(),campoPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){

                            Map<String,Object> map =new HashMap<>();

                            map.put("email",campoEmail.getText().toString());
                            map.put("contrasena",campoPassword.getText().toString());
                            map.put("nombre",campoNombre.getText().toString());
                            map.put("genero",generoConductor);
                            map.put("edad",campoEdad.getText().toString());
                            map.put("telefono",campotelefono.getText().toString());
                            map.put("estadocivil",EstadoCivil);
                            map.put("residencia",LugarResidencia);
                            map.put("laboral",LugarLaboral);
                            map.put("modelo",campoModelo.getText().toString());
                            map.put("marca",campoMarca.getText().toString());
                            map.put("placa",campoPlaca.getText().toString());
                            map.put("diaslaborales",DiasLaborales2) ;

                            String id = Auth.getCurrentUser().getUid();

                            database.child("Conductores").child(id).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task2) {

                                    if(task2.isSuccessful()){

                                        Toast.makeText(getApplicationContext(), "Usuario registrado con exito", Toast.LENGTH_SHORT).show();

                                        Intent MyIntent = new Intent(getApplicationContext(),MainActivity.class);
                                        startActivity(MyIntent);
                                        finish();


                                    }else{

                                        Toast.makeText(getApplicationContext(), "Registro fallido", Toast.LENGTH_SHORT).show();


                                    }



                                }
                            });

                        }else{

                            Toast.makeText(getApplicationContext(), "No se pudo registar el usuario", Toast.LENGTH_SHORT).show();

                        }

                    }
                });


            }else{

                Toast.makeText(getApplicationContext(), "La contraseña debe tener al menos 6 caracteres", Toast.LENGTH_SHORT).show();

            }





        }else {


            Toast.makeText(getApplicationContext(), "rellene todos los campos", Toast.LENGTH_SHORT).show();


        }




}








    private byte[] CrearBit(int señal) {

        byte[] bytesImage = new byte[0];
        
        if (señal==1){

            Bitmap bitmap = ((BitmapDrawable) imagenConductor.getDrawable()).getBitmap();

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();



            bitmap = redimensionarImagenMaximo(bitmap,640,360);
            bitmap.compress(Bitmap.CompressFormat.PNG, 0, byteArrayOutputStream);

            bytesImage = byteArrayOutputStream.toByteArray();
            
            
        }

        if (señal==2){

            Bitmap bitmap = ((BitmapDrawable) imagenMoto.getDrawable()).getBitmap();

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();



            bitmap = redimensionarImagenMaximo(bitmap,640,360);
            bitmap.compress(Bitmap.CompressFormat.PNG, 0, byteArrayOutputStream);

            bytesImage = byteArrayOutputStream.toByteArray();


        }

        



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

    private String SacarDias() {

        //ESTA PARTE HACE FUNCIONAR LOS SPINNERS DEL DIA LUNES




    HoraInicialLunes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {

            if (Lunes.isChecked()) {
                DiasLaborales += "Lunes: " + parent.getItemAtPosition(i).toString();

            }


        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    });





        HoraFinalLunes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                if (Lunes.isChecked()){

                    DiasLaborales+=" a "+parent.getItemAtPosition(i).toString()+"\n";





                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        //ESTA PARTE HACE FUNCIONAR LOS SPINNERS DEL DIA MARTES




        HoraInicialMartes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                if (Martes.isChecked()){

                    DiasLaborales+="Martes: "+parent.getItemAtPosition(i).toString();



                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });




        HoraFinalMartes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                if (Martes.isChecked()){
                    DiasLaborales+=" a "+parent.getItemAtPosition(i).toString()+"\n";


                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        //ESTA PARTE HACE FUNCIONAR LOS SPINNERS DEL DIA MIERCOLES




        HoraInicialMiercoles.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                if (Miercoles.isChecked()){
                    DiasLaborales+="Miercoles: "+parent.getItemAtPosition(i).toString();


                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });




        HoraFinalMiercoles.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                if (Miercoles.isChecked()){

                    DiasLaborales+=" a "+parent.getItemAtPosition(i).toString()+"\n";


                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //ESTA PARTE HACE FUNCIONAR LOS SPINNERS DEL DIA JUEVES




        HoraInicialJueves.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                if (Jueves.isChecked()){
                    DiasLaborales+="Jueves: "+parent.getItemAtPosition(i).toString();



                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });




        HoraFinalJueves.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                if (Jueves.isChecked()){

                    DiasLaborales+=" a "+parent.getItemAtPosition(i).toString()+"\n";


                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //ESTA PARTE HACE FUNCIONAR LOS SPINNERS DEL DIA VIERNES




        HoraInicialViernes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                if (Viernes.isChecked()){
                    DiasLaborales+="Viernes: "+parent.getItemAtPosition(i).toString();



                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });




        HoraFinalViernes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                if (Viernes.isChecked()){

                    DiasLaborales+=" a "+parent.getItemAtPosition(i).toString()+"\n";


                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //ESTA PARTE HACE FUNCIONAR LOS SPINNERS DEL DIA SABADO




        HoraInicialSabado.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                if (Sabado.isChecked()){
                    DiasLaborales+="Sabado: "+parent.getItemAtPosition(i).toString();



                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });




        HoraFinalSabado.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                if (Sabado.isChecked()){

                    DiasLaborales+=" a "+parent.getItemAtPosition(i).toString()+"\n";


                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //ESTA PARTE HACE FUNCIONAR LOS SPINNERS DEL DIA DOMINGO




        HoraInicialDomingo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                if (Domingo.isChecked()){
                    DiasLaborales+="Domingo: "+parent.getItemAtPosition(i).toString();



                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        HoraFinalDomingo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                if (Domingo.isChecked()){


                    DiasLaborales+=" a "+parent.getItemAtPosition(i).toString();


                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });







        return DiasLaborales;
    }


}
