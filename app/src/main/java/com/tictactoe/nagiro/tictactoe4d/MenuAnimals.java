package com.tictactoe.nagiro.tictactoe4d;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;

/**
 * Definition of the list adapter...uses the View Holder pattern to
 * optimize performance.
 */
class AnimalAdapter extends ArrayAdapter {

    private static final int RESOURCE = R.layout.layout_players_llista;
    private LayoutInflater inflater;
    private Context cont;

    public AnimalAdapter(Context context, Animal[] objects)
    {
        super(context, RESOURCE, objects);
        inflater = LayoutInflater.from(context);
        this.cont = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {

        Animal cat = (Animal)this.getItem( position );

        ImageView iv;
        if(convertView == null){
            iv = new ImageView(this.cont);
        } else {
            iv = (ImageView)convertView;
        }

        iv.setImageDrawable(cat.getImg());

        return iv;
    }
}

/**
 * POJO for holding each list choice
 *
 */
class Animal {
    private Drawable _img;
    private Integer   _val;

    public Animal( Drawable img, Integer val ) {
        _img = img;
        _val = val;
    }


    public Drawable getImg() {
        return _img;
    }

    public Integer getVal() {
        return _val;
    }
}