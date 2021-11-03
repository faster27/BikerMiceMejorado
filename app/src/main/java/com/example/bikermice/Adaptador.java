package com.example.bikermice;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.bikermice.R;
import com.example.bikermice.entidades.Conductor;

import java.util.ArrayList;

public class Adaptador extends BaseAdapter {


    private static LayoutInflater inflater = null;
    Context contexto;
    ArrayList<String> listaInformacion;


    public Adaptador(Context contexto, ArrayList<String> listaInformacion) {
        this.contexto = contexto;
        this.listaInformacion = listaInformacion;

        inflater = (LayoutInflater) contexto.getSystemService(contexto.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        final View vista=inflater.inflate(R.layout.list_view_item,null);
        TextView conductor = (TextView) vista.findViewById(R.id.textViewConductor);
        conductor.setText(listaInformacion.get(i));

        return vista;
    }

    @Override
    public int getCount() {
        return listaInformacion.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }


}
