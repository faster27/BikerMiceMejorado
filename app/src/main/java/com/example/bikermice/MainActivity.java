package com.example.BikerMice;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    EditText cedula;

    protected void  onCreate(Bundle SavedInstanceStatus){

        super.onCreate(SavedInstanceStatus);
        setContentView(R.layout.activity_main);

        cedula=findViewById(R.id.editTextCedulaLogin);

        ConexionSQLiteHelper conn= new ConexionSQLiteHelper(this,"dbBikerMice2",null,1);
        conn.getWritableDatabase();



    }


    public void onClickLogin(View view) {


       String cedulaconsultar= cedula.getText().toString();

       if (cedulaconsultar.equals("")){

           Toast.makeText(getApplicationContext(),"Ingrese un documento",Toast.LENGTH_SHORT).show();


       }else{

           ConexionSQLiteHelper conn= new ConexionSQLiteHelper(this,"dbBikerMice2",null,1);
           SQLiteDatabase db = conn.getWritableDatabase();


           String stringQueryConductor = "Select cedula from conductores where cedula= "+cedulaconsultar+"";

           String stringQueryPasajero = "Select cedula from pasajeros where cedula= "+cedulaconsultar+"";

           Cursor cursorConductor = db.rawQuery(stringQueryConductor,null);
           Cursor cursorPasajero = db.rawQuery(stringQueryPasajero,null);

           if(cursorConductor.getCount()==0 && cursorPasajero.getCount()==0){

               Toast.makeText(getApplicationContext(),"Usuario no encontrado o no registrado",Toast.LENGTH_SHORT).show();

           }else{

               if(cursorConductor.getCount()>0){
                   Intent MiIntent = new Intent(getApplicationContext(),PerfilConductorActivity.class);


                   cursorConductor.moveToFirst();

                   int columna =cursorConductor.getColumnIndex("cedula");

                   String cedula= cursorConductor.getString(columna);


                   Bundle MiBundle=new Bundle();
                   MiBundle.putString("cedula",cedula);
                   MiBundle.putString("señal","2");

                   MiIntent.putExtras(MiBundle);


                   startActivity(MiIntent);

               }

               if(cursorPasajero.getCount()>0){
                   Intent MiIntent = new Intent(getApplicationContext(),PerfilPasajeroActivity.class);


                   cursorPasajero.moveToFirst();

                   int columna =cursorPasajero.getColumnIndex("cedula");

                   String cedula= cursorPasajero.getString(columna);


                   Bundle MiBundle=new Bundle();
                   MiBundle.putString("cedula",cedula);

                   MiIntent.putExtras(MiBundle);


                   startActivity(MiIntent);





               }





           }

           cursorPasajero.close();
           cursorConductor.close();
           db.close();

       }








    }

    public void onClickRegister(View view) {

        Intent MiIntent = new Intent(getApplicationContext(),SeleccionRegistroActivity.class);
        startActivity(MiIntent);

    }
}
