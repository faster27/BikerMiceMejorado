package com.example.BikerMice;


import android.app.Activity;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.BikerMice.utilidades.Utilidades;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import id.zelory.compressor.Compressor;

public class RegistroPasajerosActivity extends AppCompatActivity {

    EditText campoCedula,campoNombre,campoEdad,campoEmail,campoPassword;
    Spinner ComboGenero,ComboLugarResidencia;
    String GeneroPasajero,LugarResidencia;
    ImageView ImagenPasajero;
    Button CrearCuenta;
    TextView TituloVentana;
    String se??al;
    Bundle MiBundle;
    FirebaseAuth Auth;
    DatabaseReference database;
    StorageReference storageReference;
    Uri path;
    private ProgressDialog progressDialog;

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
        campoEmail=findViewById(R.id.textViewCorreoPasajero);
        campoPassword=findViewById(R.id.textViewPasswordPasajero);
        Auth=FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference();
        storageReference= FirebaseStorage.getInstance().getReference();
        progressDialog = new ProgressDialog(this);


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

            String Uid =MiBundle.getString("Uid");
            se??al = MiBundle.getString("bandera").toString();

            cargarDatos(Uid);

        }



    }

    private void cargarDatos(String Uid) {

        database.child("Users").child(Uid).addListenerForSingleValueEvent(new ValueEventListener() {
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
                    String LinkFoto = snapshot.child("linkfoto").getValue().toString();
                    Glide.with(RegistroPasajerosActivity.this).load(LinkFoto).into(ImagenPasajero);


                    //SE SETEA EL CAMPO EMAIL
                    campoEmail.setText(email);
                    campoEmail.setKeyListener(null);

                    //SE SETEA EL CAMPO CONTRASE??A
                    campoPassword.setText(contrasena);


                    //SE SETEA EL CAMPO CEDULA
                    campoCedula.setText(cedula);
                    campoCedula.setKeyListener(null);

                    //SE SETA EL CAMPO NOMBRE
                    campoNombre.setText(nombre);

                    //SE SETA EL CAMPO EDAD
                    campoEdad.setText(edad);

                    //SE SETEA EL CAMPO GENERO
                    ComboGenero.setSelection(ObtenerPosicionGenero(ComboGenero,genero));

                    //SE SETEA EL CAMPO LUGAR DE RESIDENCIA
                    ComboLugarResidencia.setSelection(ObtenerPosicionLugarResidencia(ComboLugarResidencia,residencia));

                    //SE SETEA EL BOTON CREAR CUENTA

                    CrearCuenta.setText("Actualizar perfil");

                    //SE SETA EL TITULO DE LA VENTA

                    TituloVentana.setText("Editar perfil");





                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




    }

    private int ObtenerPosicionLugarResidencia(Spinner comboLugarResidencia, String residencia) {

        //Creamos la variable posicion y lo inicializamos en 0
        int posicion = 0;
        //Recorre el spinner en busca del ??tem que coincida con el parametro `String fruta`
        //que lo pasaremos posteriormente
        for (int i = 0; i < comboLugarResidencia.getCount(); i++) {
            //Almacena la posici??n del ??tem que coincida con la b??squeda
            if (comboLugarResidencia.getItemAtPosition(i).toString().equalsIgnoreCase(residencia)) {
                posicion = i;
            }
        }
        //Devuelve un valor entero (si encontro una coincidencia devuelve la
        // posici??n 0 o N, de lo contrario devuelve 0 = posici??n inicial)


        return  posicion;

    }

    private int ObtenerPosicionGenero(Spinner comboGenero, String genero) {
        //Creamos la variable posicion y lo inicializamos en 0
        int posicion = 0;
        //Recorre el spinner en busca del ??tem que coincida con el parametro `String fruta`
        //que lo pasaremos posteriormente
        for (int i = 0; i < comboGenero.getCount(); i++) {
            //Almacena la posici??n del ??tem que coincida con la b??squeda
            if (comboGenero.getItemAtPosition(i).toString().equalsIgnoreCase(genero)) {
                posicion = i;
            }
        }
        //Devuelve un valor entero (si encontro una coincidencia devuelve la
        // posici??n 0 o N, de lo contrario devuelve 0 = posici??n inicial)


        return  posicion;
    }


    public void onClickCrearCuenta(View view) {

        String bandera="1";

        if (bandera.equals(se??al)){
            ActualizarPerfil();
        }else{
            RegistrarUsuarios();
        }

    }

    private void ActualizarPerfil() {

        progressDialog.setTitle("Actualizando...");
        progressDialog.setMessage("Actualizando Perfil");
        progressDialog.setCancelable(false);

        progressDialog.show();

        String genero = "Genero";
        String residencia="Lugar de residencia";



        if(
                !campoNombre.getText().toString().isEmpty() &&
                !campoEdad.getText().toString().isEmpty() &&
                !campoPassword.getText().toString().isEmpty() &&
                !GeneroPasajero.equals(genero) &&
                !LugarResidencia.equals(residencia)

        ){

            if(campoPassword.getText().toString().length()>=6){

                if(path ==null){

                    Map<String,Object> UsuarioMap = new HashMap<>();

                    UsuarioMap.put("email",campoEmail.getText().toString());
                    UsuarioMap.put("contrasena",campoPassword.getText().toString());
                    UsuarioMap.put("nombre",campoNombre.getText().toString());
                    UsuarioMap.put("cedula",campoCedula.getText().toString());
                    UsuarioMap.put("genero",GeneroPasajero);
                    UsuarioMap.put("residencia",LugarResidencia);

                    database.child("Users").child(Auth.getCurrentUser().getUid().toString()).updateChildren(UsuarioMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {

                            Toast.makeText(getApplicationContext(), "Usuario Actualizado con exito", Toast.LENGTH_SHORT).show();

                            Intent MiIntent = new Intent(getApplicationContext(),PerfilPasajeroActivity.class);

                            Bundle MiBundle=new Bundle();
                            MiBundle.putString("Uid",Auth.getCurrentUser().getUid());

                            MiIntent.putExtras(MiBundle);

                            progressDialog.dismiss();

                            startActivity(MiIntent);
                            finish();

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            Toast.makeText(getApplicationContext(), "Actualizacion fallida", Toast.LENGTH_SHORT).show();


                        }
                    });

                }


                else{


                    StorageReference folderRef = storageReference.child("fotosPasajeros");
                    StorageReference fotoRef = folderRef.child(new Date().toString());

                    database.child("Users").child(Auth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            if(snapshot.exists()){


                                String linkfoto=snapshot.child("linkfoto").getValue().toString();

                                StorageReference photoRef=folderRef.getStorage().getReferenceFromUrl(linkfoto);

                                photoRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {

                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception exception) {
                                        // Uh-oh, an error occurred!

                                    }
                                });



                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });



                    fotoRef.putFile(path).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        Uri downloadUri;
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Task<Uri> uriTask =taskSnapshot.getStorage().getDownloadUrl();
                            while(!uriTask.isSuccessful());
                            downloadUri=uriTask.getResult();

                            Map<String,Object> UsuarioMap = new HashMap<>();

                            UsuarioMap.put("email",campoEmail.getText().toString());
                            UsuarioMap.put("contrasena",campoPassword.getText().toString());
                            UsuarioMap.put("nombre",campoNombre.getText().toString());
                            UsuarioMap.put("cedula",campoCedula.getText().toString());
                            UsuarioMap.put("genero",GeneroPasajero);
                            UsuarioMap.put("residencia",LugarResidencia);
                            UsuarioMap.put("linkfoto",downloadUri.toString());

                            database.child("Users").child(Auth.getCurrentUser().getUid().toString()).updateChildren(UsuarioMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {

                                    Toast.makeText(getApplicationContext(), "Usuario Actualizado con exito", Toast.LENGTH_SHORT).show();

                                    Intent MiIntent = new Intent(getApplicationContext(),PerfilPasajeroActivity.class);

                                    Bundle MiBundle=new Bundle();
                                    MiBundle.putString("Uid",Auth.getCurrentUser().getUid());

                                    MiIntent.putExtras(MiBundle);
                                    progressDialog.dismiss();

                                    startActivity(MiIntent);
                                    finish();

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                    Toast.makeText(getApplicationContext(), "Actualizacion fallida", Toast.LENGTH_SHORT).show();


                                }
                            });


                        }
                    });


                }














            }else {

                Toast.makeText(getApplicationContext(), "La contrase??a debe tener al menos 6 caracteres", Toast.LENGTH_SHORT).show();

            }




        }


    }


    private void RegistrarUsuarios() {

        progressDialog.setTitle("Registrando...");
        progressDialog.setMessage("Registrando Perfil");
        progressDialog.setCancelable(false);

        progressDialog.show();


        String genero = "Genero";
        String residencia="Lugar de residencia";

        if(!campoCedula.getText().toString().isEmpty() &&
            !campoNombre.getText().toString().isEmpty() &&
            !campoEdad.getText().toString().isEmpty() &&
            !campoEmail.getText().toString().isEmpty() &&
            !campoPassword.getText().toString().isEmpty() &&
                !GeneroPasajero.equals(genero) &&
                !LugarResidencia.equals(residencia)

            )  {

            if(campoPassword.getText().toString().length()>=6){




                ///AQUI YA SE GUARDA EL PASAJERO Y SE LE PASA EL LINK DE LA FOTO
                FirebaseAuth.getInstance().fetchSignInMethodsForEmail(campoEmail.getText().toString()).addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                    @Override
                    public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {


                        /* TODO DEBERIA QUEDAR DETO DESPUES DE GUARDR LA IMAGEN PARA QUE FUNCIONES */

                        if(task.isSuccessful()){

                            boolean check =!task.getResult().getSignInMethods().isEmpty();

                            if (check){

                                StorageReference folderRef = storageReference.child("fotosPasajeros");
                                StorageReference fotoRef = folderRef.child(new Date().toString());

                                fotoRef.putFile(path).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    Uri downloadUri;
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                        Task<Uri> uriTask =taskSnapshot.getStorage().getDownloadUrl();
                                        while(!uriTask.isSuccessful());
                                        downloadUri =uriTask.getResult();

                                        Log.e("TAG",downloadUri.toString());

                                        Map<String,Object> map =new HashMap<>();

                                        map.put("email",campoEmail.getText().toString());
                                        map.put("contrasena",campoPassword.getText().toString());
                                        map.put("nombre",campoNombre.getText().toString());
                                        map.put("cedula",campoCedula.getText().toString());
                                        map.put("edad",campoEdad.getText().toString());
                                        map.put("genero",GeneroPasajero);
                                        map.put("residencia",LugarResidencia);
                                        map.put("linkfoto",downloadUri.toString());

                                        String id = Auth.getCurrentUser().getUid();

                                        database.child("Users").child(id).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
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



                            else{


                                        Auth.createUserWithEmailAndPassword(campoEmail.getText().toString(),campoPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                            @Override
                                            public void onComplete(@NonNull Task<AuthResult> task) {

                                                if(task.isSuccessful()){

                                                    StorageReference folderRef = storageReference.child("fotosPasajeros");
                                                    StorageReference fotoRef = folderRef.child(new Date().toString());

                                                    fotoRef.putFile(path).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                        Uri downloadUri;
                                                        @Override
                                                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                            Task<Uri> uriTask =taskSnapshot.getStorage().getDownloadUrl();
                                                            while(!uriTask.isSuccessful());
                                                            downloadUri=uriTask.getResult();

                                                            Map<String,Object> map =new HashMap<>();

                                                            map.put("email",campoEmail.getText().toString());
                                                            map.put("contrasena",campoPassword.getText().toString());
                                                            map.put("nombre",campoNombre.getText().toString());
                                                            map.put("cedula",campoCedula.getText().toString());
                                                            map.put("edad",campoEdad.getText().toString());
                                                            map.put("genero",GeneroPasajero);
                                                            map.put("residencia",LugarResidencia);
                                                            map.put("linkfoto",downloadUri.toString());





                                                            String id = FirebaseAuth.getInstance().getCurrentUser().getUid();

                                                            database.child("Users").child(id).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
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

                                            }
                                        });










                            }


                        }








                    }
                });

















                        }else {

                Toast.makeText(getApplicationContext(), "La contrase??a debe tener al menos 6 caracteres", Toast.LENGTH_SHORT).show();

            }

    }else{

            Toast.makeText(getApplicationContext(), "rellene todos los campos", Toast.LENGTH_SHORT).show();



        }




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

         startActivityForResult(MiIntent.createChooser(MiIntent,"Seleccione la aplicaci??n"),10);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        path=data.getData();

        ImagenPasajero.setImageURI(path);



    }

    private String GuardarFoto(Uri foto) {

        final Uri[] downloadUri = new Uri[1];
        StorageReference folderRef = storageReference.child("fotosPasajeros");
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
}
