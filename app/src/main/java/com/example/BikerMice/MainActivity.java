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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {


    EditText correo,contrasena;
    FirebaseAuth Auth;
    DatabaseReference db;

    protected void  onCreate(Bundle SavedInstanceStatus){

        super.onCreate(SavedInstanceStatus);
        setContentView(R.layout.activity_main);

        correo=findViewById(R.id.editTextEmail);
        contrasena=findViewById(R.id.editTextTextPassword);
        Auth=FirebaseAuth.getInstance();
        db=FirebaseDatabase.getInstance().getReference();



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


                       db.child("Users").child(Auth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                           @Override
                           public void onDataChange(@NonNull DataSnapshot snapshot) {

                               if(snapshot.exists()){

                                   Intent MiIntent = new Intent(getApplicationContext(),PerfilPasajeroActivity.class);

                                   Bundle MiBundle=new Bundle();
                                   MiBundle.putString("Uid",Auth.getCurrentUser().getUid());


                                   MiIntent.putExtras(MiBundle);

                                   startActivity(MiIntent);
                                   finish();

                               }else{

                                   Intent MiIntent = new Intent(getApplicationContext(),PerfilConductorActivity.class);

                                   Bundle MiBundle=new Bundle();
                                   MiBundle.putString("Uid",Auth.getCurrentUser().getUid());
                                   MiBundle.putString("señal","2");


                                   MiIntent.putExtras(MiBundle);

                                   startActivity(MiIntent);
                                   finish();


                               }


                           }

                           @Override
                           public void onCancelled(@NonNull DatabaseError error) {

                           }
                       });



                       //Toast.makeText(getApplicationContext(),"Login exitoso",Toast.LENGTH_SHORT).show();


                   }else{

                       Toast.makeText(getApplicationContext(),"Usuario o contraseña incorrectos",Toast.LENGTH_SHORT).show();



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
