package com.example.maanas.aemet;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class MiAdaptador extends BaseAdapter {
    ArrayList<Clima> clima;
    Context contexto;

    public MiAdaptador(ArrayList<Clima> clima, Context contexto) {
        this.clima = clima;
        this.contexto = contexto;
    }

    public void setClima(ArrayList<Clima> clima) {
        this.clima = clima;
    }

    public ArrayList<Clima> getClima() {
        return clima;
    }

    @Override
    public int getCount() {
        return clima.size();
    }

    @Override
    public Object getItem(int position) {
        return clima.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater=LayoutInflater.from(contexto);
        View v=inflater.inflate(R.layout.mi_vista,parent,false);
        TextView tv_fecha=v.findViewById(R.id.tv_fecha);
        TextView tv_tmaxima=v.findViewById(R.id.tv_tmaxima);
        TextView tv_tminima=v.findViewById(R.id.tv_tminima);
        Clima c=clima.get(position);
        tv_fecha.setText(c.getFecha());
        tv_tminima.setText(c.getT_minima());
        tv_tmaxima.setText(c.getT_maxima());
        return v;
    }
}
