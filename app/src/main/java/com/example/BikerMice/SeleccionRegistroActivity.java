package com.example.BikerMice;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class SeleccionRegistroActivity extends AppCompatActivity  {
    private AdView mAdView;
    @Override
    protected  void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seleccion_activity);




        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });


        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

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

