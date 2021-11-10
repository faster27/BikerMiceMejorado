package com.example.BikerMice;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {


    EditText correo,contrasena;
    ImageView Google;
    FirebaseAuth Auth;
    DatabaseReference db;


    private GoogleSignInClient googleSignInCliente;
    private static final int GOOGLE_SIGN_IN=1111;

    protected void  onCreate(Bundle SavedInstanceStatus){

        super.onCreate(SavedInstanceStatus);
        setContentView(R.layout.activity_main);

        correo=findViewById(R.id.editTextEmail);
        contrasena=findViewById(R.id.editTextTextPassword);
        Google=findViewById(R.id.imageViewGoogle);
        Auth=FirebaseAuth.getInstance();
        db=FirebaseDatabase.getInstance().getReference();
        
        initGoogleClient();



    }

    private void initGoogleClient() {

        GoogleSignInOptions gso= new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleSignInCliente= GoogleSignIn.getClient(this,gso);

    }

    public void loginGoogle(View view) {

        googleSignInCliente.signOut();

        Intent signInIntent = googleSignInCliente.getSignInIntent();

        startActivityForResult(signInIntent,GOOGLE_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode== GOOGLE_SIGN_IN){

            Task<GoogleSignInAccount> task= GoogleSignIn.getSignedInAccountFromIntent(data);


        try {
            GoogleSignInAccount account=task.getResult(ApiException.class);
            LoginFirebaseWithGoogle(account.getIdToken());
        }catch (ApiException e){

            Log.w(TAG,"login con google fallo",e);

        }



        }


    }

    private void LoginFirebaseWithGoogle(String idToken) {



        AuthCredential credential= GoogleAuthProvider.getCredential(idToken,null);
        Auth.signInWithCredential(credential).addOnCompleteListener(this,task -> {

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

                            db.child("Conductores").child(Auth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {


                                    if(snapshot.exists()){

                                        Intent MiIntent = new Intent(getApplicationContext(),PerfilConductorActivity.class);

                                        Bundle MiBundle=new Bundle();
                                        MiBundle.putString("Uid",Auth.getCurrentUser().getUid());

                                        MiBundle.putString("señal","2");


                                        MiIntent.putExtras(MiBundle);

                                        startActivity(MiIntent);
                                        finish();




                                    }else{


                                        Intent MiIntent = new Intent(getApplicationContext(),SeleccionRegistroActivity.class);
                                        startActivity(MiIntent);
                                        finish();



                                    }




                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });







                        }




                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


            }




        });

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
