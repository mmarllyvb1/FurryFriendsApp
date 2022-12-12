package com.mmarllyvb1.furryfriendsapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mmarllyvb1.furryfriendsapp.Modelo.Mascota;
import com.mmarllyvb1.furryfriendsapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Adaptador extends BaseAdapter {

    private Context context;
    private ArrayList<Mascota> data;

    public Adaptador(Context context, ArrayList<Mascota> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (convertView == null) {
            v = LayoutInflater.from(context)
                    .inflate(R.layout.elemento_lista, null);
        }

        Mascota mascota = data.get(position);
        TextView txtNombre = v.findViewById(R.id.txtNombre);
        TextView txtCualidad = v.findViewById(R.id.txtCualidad);
        TextView txtEdad = v.findViewById(R.id.txtEdad);
        ImageView imagen = v.findViewById(R.id.imagenMascota);

        txtNombre.setText(mascota.getNombre());
        txtCualidad.setText(mascota.getCualidad());
        txtEdad.setText(Integer.toString(mascota.getEdad()));
        if (mascota.getImagen() != null && mascota.getImagen().length() > 0) {
            Picasso.get()
                    .load(mascota.getImagen())
                    .resize(100, 100)
                    .centerCrop()
                    .into(imagen);
        }
        return v;

    }
}
