package com.example.todeolho.myapplication.classes;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.todeolho.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class CustomList extends ArrayAdapter<String> {

    private Activity context;
    private ArrayList<String> web;
    private ArrayList<String> imageId;
    private ArrayList<Denuncia> denunciasLista;

    public CustomList(Activity context,
                      ArrayList<String> web, ArrayList<String> imageId) {
        super(context, R.layout.lista_denuncias, web);
        this.context = context;
        this.web = web;
        this.imageId = imageId;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.lista_denuncias, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);

        ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
        txtTitle.setText(web.get(position));

        Glide.with(context)
                .load(imageId.get(position))
                .centerCrop()
                .fitCenter()
                .override(300, 100).into(imageView);
        return rowView;
    }
}

