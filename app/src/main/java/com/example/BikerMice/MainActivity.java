package com.example.BikerMice;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {


    EditText correo,contrasena;
    FirebaseAuth Auth;

    protected void  onCreate(Bundle SavedInstanceStatus){

        super.onCreate(SavedInstanceStatus);
        setContentView(R.layout.activity_main);

        correo=findViewById(R.id.editTextEmail);
        contrasena=findViewById(R.id.editTextTextPassword);
        Auth=FirebaseAuth.getInstance();
        ConexionSQLiteHelper conn= new ConexionSQLiteHelper(this,"dbBikerMice2",null,1);
        conn.getWritableDatabase();



    }


    public void onClickLogin(View view) {


       String CorreoUsuario= correo.getText().toString();
       String ContrasenaUsuario=contrasena.getText().toString();



       if (CorreoUsuario.equals("") || ContrasenaUsuario.equals("")){

           Toast.makeText(getApplicationContext(),"Ingrese todos los datos",Toast.LENGTH_SHORT).show();


       }else{

           Auth.signInWithEmailAndPassword(CorreoUsuario,ContrasenaUsuario).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
               @Override
               public void onComplete(@NonNull Task<AuthResult> task) {
                   if(task.isSuccessful()){

                       Toast.makeText(getApplicationContext(),"Login exitoso",Toast.LENGTH_SHORT).show();


                   }else{

                       Toast.makeText(getApplicationContext(),"Login fallido",Toast.LENGTH_SHORT).show();



                   }
               }
           });





       }








    }

    public void onClickRegister(View view) {

        Intent MiIntent = new Intent(getApplicationContext(),SeleccionRegistroActivity.class);
        startActivity(MiIntent);

    }
}
