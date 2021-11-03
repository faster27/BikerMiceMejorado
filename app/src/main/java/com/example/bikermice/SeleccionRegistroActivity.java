package com.example.bikermice;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;

public class SeleccionRegistroActivity extends AppCompatActivity  {

    @Override
    protected  void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seleccion_activity);

    }


    public void onClickConductor(View view){

        Intent MiIntent = new Intent(getApplicationContext(),RegistroConductorActivity.class);
        startActivity(MiIntent);

    }


    public void onClickPasajero(View view) {
        Intent MiIntent= new Intent(getApplicationContext(),RegistroPasajerosActivity.class);
        startActivity(MiIntent);
    }
}

