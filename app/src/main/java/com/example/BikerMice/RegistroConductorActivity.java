package com.example.BikerMice;

import android.app.ProgressDialog;
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

import android.util.Log;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.Date;
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


    Uri pathFotoConductor;
    Uri pathFotoMoto;
    StorageReference storageReference;



    //AQUI VAN LOS SPINNERS  Y LOS CHECKBOXS

    CheckBox Lunes,Martes,Miercoles,Jueves,Viernes,Sabado,Domingo;
    Spinner HoraInicialLunes, HoraFinalLunes,HoraInicialMartes,HoraFinalMartes,HoraInicialMiercoles,HoraFinalMiercoles;
    Spinner HoraInicialJueves,HoraFinalJueves,HoraInicialViernes,HoraFinalViernes,HoraInicialSabado,HoraFinalSabado;
    Spinner HoraInicialDomingo,HoraFinalDomingo;

    ImageView imagenConductor,imagenMoto;
    Bundle BundleEditarPerfil;

    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceStatus){
        super.onCreate(savedInstanceStatus);
        setContentView(R.layout.registro_conductor_activity);


        //Variables para la base de datos

        Auth=FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference();
        storageReference= FirebaseStorage.getInstance().getReference();



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

        progressDialog = new ProgressDialog(this);



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


            String Uid =BundleEditarPerfil.getString("Uid");
            señal =BundleEditarPerfil.getString("bandera");


            cargarDatos(Uid);



        }


    }

    private void cargarDatos(String Uid) {

        //PRIMERO SE PREPARA LA INTERFAZ GRAFICA SETEANDO TITULOS Y BOTONES



        database.child("Conductores").child(Uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

               if(snapshot.exists()){

                   String email=snapshot.child("email").getValue().toString();
                   String contrasena=snapshot.child("contrasena").getValue().toString();
                   String nombre=snapshot.child("nombre").getValue().toString();
                   String cedula=snapshot.child("cedula").getValue().toString();
                   String edad=snapshot.child("edad").getValue().toString();
                   String genero=snapshot.child("genero").getValue().toString();
                   String residencia=snapshot.child("residencia").getValue().toString();
                   String telefono=snapshot.child("telefono").getValue().toString();
                   String estadocivil=snapshot.child("estadocivil").getValue().toString();
                   String laboral=snapshot.child("laboral").getValue().toString();
                   String modelo=snapshot.child("modelo").getValue().toString();
                   String marca=snapshot.child("marca").getValue().toString();
                   String placa=snapshot.child("placa").getValue().toString();
                   String diaslaborales=snapshot.child("diaslaborales").getValue().toString();
                   String implementos=snapshot.child("implementos").getValue().toString();


                   //SE SETEA EL CAMPO EMAIL
                   campoEmail.setText(email);
                   campoEmail.setKeyListener(null);

                   //SE SETEA EL CAMPO CONTRASEÑA
                   campoPassword.setText(contrasena);


                   //SE SETEA EL CAMPO CEDULA
                   campoCedula.setText(cedula);
                   campoCedula.setKeyListener(null);

                   //SE SETA EL CAMPO NOMBRE
                   campoNombre.setText(nombre);

                   //SE SETA EL CAMPO EDAD
                   campoEdad.setText(edad);

                   //SE SETEA EL CAMPO GENERO
                   ComboGenero.setSelection(ObtenerPosicionSpinner(ComboGenero,genero));

                   //SE SETEA EL CAMPO LUGAR DE RESIDENCIA
                   ComboLugarResidencia.setSelection(ObtenerPosicionSpinner(ComboLugarResidencia,residencia));

                   //SE SETEA EL CAMPO telefono
                   campotelefono.setText(telefono);

                   //SE SETEA EL CAMPO Estado civil
                   ComboEstadoCivil.setSelection(ObtenerPosicionSpinner(ComboEstadoCivil,estadocivil));


                   //SE SETEA EL CAMPO LugarLaboral
                   ComboLugarLaboral.setSelection(ObtenerPosicionSpinner(ComboLugarLaboral,laboral));

                   //SE SETEA EL CAMPO Modelo
                   campoModelo.setText(modelo);

                   //SE SETEA EL CAMPO MARCA
                   campoMarca.setText(marca);

                   //SE SETEA EL CAMPO PLACA
                   campoPlaca.setText(placa);

                   //SE SETEA EL CAMPO Dias Laborales
                   campoDiasLaborales.setText(diaslaborales);

                   //SE SETEA EL CAMPO Dias Implemetos
                   campoImplementos.setText(implementos);

                   //SE SETEA EL BOTON CREAR CUENTA

                   BtnActualizarPerfil.setText("Actualizar perfil");

                   //SE SETA EL TITULO DE LA VENTA

                   titulo.setText("Editar perfil");







               }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




//Aqui esta la parte de las fotos
/*
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


*/


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

        }else{

            RegistrarConductor();



        }









    }

    private void ActualizarPerfil() {

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

                Map<String,Object> map =new HashMap<>();

                map.put("email",campoEmail.getText().toString());
                map.put("cedula",campoCedula.getText().toString());
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
                map.put("implementos",campoImplementos.getText().toString());

                database.child("Conductores").child(Auth.getCurrentUser().getUid().toString()).updateChildren(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                        Toast.makeText(getApplicationContext(), "Usuario Actualizado con exito", Toast.LENGTH_SHORT).show();

                        Intent MiIntent = new Intent(getApplicationContext(),PerfilConductorActivity.class);

                        Bundle MiBundle=new Bundle();
                        MiBundle.putString("Uid",Auth.getCurrentUser().getUid());
                        MiBundle.putString("señal","2");

                        MiIntent.putExtras(MiBundle);

                        startActivity(MiIntent);
                        finish();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Toast.makeText(getApplicationContext(), "Actualizacion fallida", Toast.LENGTH_SHORT).show();


                    }
                });




            }else{

                Toast.makeText(getApplicationContext(), "La contraseña debe tener al menos 6 caracteres", Toast.LENGTH_SHORT).show();

            }


        }else {


            Toast.makeText(getApplicationContext(), "rellene todos los campos", Toast.LENGTH_SHORT).show();


        }





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

            pathFotoConductor=data.getData();
            imagenConductor.setImageURI(pathFotoConductor);


        }

        if(seleccionBoton==2 &&   resultCode==RESULT_OK){

            pathFotoMoto=data.getData();
            imagenMoto.setImageURI(pathFotoMoto);


        }




    }



    private void RegistrarConductor() {

        progressDialog.setTitle("Registrando...");
        progressDialog.setMessage("Registrando Perfil");
        progressDialog.setCancelable(false);

        progressDialog.show();

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

            if ((pathFotoConductor==null && pathFotoMoto==null) || (pathFotoConductor==null || pathFotoMoto==null) ){

                Toast.makeText(getApplicationContext(), "Por favor suba las fotos", Toast.LENGTH_SHORT).show();

            }else{

                if(campoPassword.getText().toString().length()>=6) {


                    FirebaseAuth.getInstance().fetchSignInMethodsForEmail(campoEmail.getText().toString()).addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                        @Override
                        public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {

                            if(task.isSuccessful()){

                                boolean check =!task.getResult().getSignInMethods().isEmpty();

                                if(check){

                                    StorageReference folderRef = storageReference.child("fotosConductores");
                                    StorageReference fotoRef = folderRef.child(new Date().toString());


                                    fotoRef.putFile(pathFotoConductor).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {

                                        Uri downloadUriConductor;

                                        //rimero se gurda la foto del condcutor
                                        @Override
                                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                            Task<Uri> uriTask =taskSnapshot.getStorage().getDownloadUrl();
                                            while(!uriTask.isSuccessful());
                                            downloadUriConductor =uriTask.getResult();


                                            StorageReference folderRef = storageReference.child("fotosMotos");
                                            StorageReference fotoRef = folderRef.child(new Date().toString());



                                            //aqui se guarda la foto de la moto
                                            fotoRef.putFile(pathFotoMoto).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {

                                                Uri downloadUriMoto;
                                                @Override
                                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                    Task<Uri> uriTask =taskSnapshot.getStorage().getDownloadUrl();
                                                    while(!uriTask.isSuccessful());
                                                    downloadUriMoto =uriTask.getResult();


                                                   //AQUI ES DONDE YA SE GUARDA EL CONDUCTOR EN EL REAL TIME con fotos y todo

                                                    Map<String,Object> map =new HashMap<>();

                                                    map.put("email",campoEmail.getText().toString());
                                                    map.put("contrasena",campoPassword.getText().toString());
                                                    map.put("cedula",campoCedula.getText().toString());
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
                                                    map.put("implementos",campoImplementos.getText().toString()) ;
                                                    map.put("linkfotoconductor",downloadUriConductor.toString());
                                                    map.put("linkfotomodo",downloadUriMoto.toString());

                                                    String id = Auth.getCurrentUser().getUid();

                                                    database.child("Conductores").child(id).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task2) {

                                                            if(task2.isSuccessful()){



                                                                Toast.makeText(getApplicationContext(), "Usuario registrado con exito", Toast.LENGTH_SHORT).show();

                                                                Intent MyIntent = new Intent(getApplicationContext(),MainActivity.class);
                                                                progressDialog.dismiss();
                                                                startActivity(MyIntent);
                                                                finish();


                                                            }else{

                                                                Toast.makeText(getApplicationContext(), "Registro fallido", Toast.LENGTH_SHORT).show();


                                                            }



                                                        }
                                                    });

                                                }
                                            });


                                        }
                                    });






                                }else{
                                    Auth.createUserWithEmailAndPassword(campoEmail.getText().toString(),campoPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {

                                            if(task.isSuccessful()){

                                                Map<String,Object> map =new HashMap<>();

                                                map.put("email",campoEmail.getText().toString());
                                                map.put("contrasena",campoPassword.getText().toString());
                                                map.put("cedula",campoCedula.getText().toString());
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
                                                map.put("implementos",campoImplementos.getText().toString()) ;

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


                                }


                            }

                        }
                    });





                }else{

                    Toast.makeText(getApplicationContext(), "La contraseña debe tener al menos 6 caracteres", Toast.LENGTH_SHORT).show();

                }

            }



        }else {


            Toast.makeText(getApplicationContext(), "rellene todos los campos", Toast.LENGTH_SHORT).show();


        }




}



    private String GuardarFoto(Uri foto) {

        final Uri[] downloadUri = new Uri[1];


        StorageReference folderRef = storageReference.child("fotosConductores");
        StorageReference fotoRef = folderRef.child(new Date().toString());

        fotoRef.putFile(foto).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {

            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uriTask =taskSnapshot.getStorage().getDownloadUrl();
                while(!uriTask.isSuccessful());
                downloadUri[0] =uriTask.getResult();


            }
        });


        return downloadUri[0].toString();
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
