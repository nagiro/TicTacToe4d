package com.tictactoe.nagiro.tictactoe4d;

import android.graphics.drawable.AnimationDrawable;
import android.util.Log;
import android.view.animation.Animation;
import android.widget.ImageView;
import java.util.HashMap;
import java.util.Vector;

/**
 * Created by nagiro on 30/07/14.
 */
public class Casella {

    public String Nom = "";
    //HashMap<Nivell,Jugador>
    public HashMap<Integer,Integer> JugadorPerNivell = new HashMap<Integer,Integer>();
    public ImageView i = null;
    public int Numero = 0;
    //Colors de les fitxes
    public HashMap<Integer, Integer> Colors;


    public Casella(int id, ImageView i){

        this.Colors = new HashMap<Integer,Integer>();
        Colors.put(0, R.drawable.casella);
        Colors.put(1, R.drawable.animacio_blaves);
        Colors.put(2, R.drawable.animacio_vermelles);
        Colors.put(3, R.drawable.animacio_grogues);
        Colors.put(4, R.drawable.animacio_verdes);

        this.Numero = id;
        this.Nom = "i" + String.valueOf(id);
        this.JugadorPerNivell.put(1,0);
        this.i = i;
        this.i.setBackgroundResource( this.Colors.get(0) );

    }

    //Ens diu el color del jugador que és visible
    public int getJugadorVisible(int Nivell){
        Integer Jugador = this.JugadorPerNivell.get(Nivell);
        if(Jugador != null && Jugador > 0){
            if(Nivell > 1) return this.JugadorPerNivell.get(Nivell-1);
            else return 0;
        }
        return 0;
    }

    public int getJugador(int Nivell){
        try {
            return this.JugadorPerNivell.get(Nivell);
        } catch(Exception e){ return 0; }
    }


    public int getNumero(){
        return this.Numero;
    }

    public String toString(){
        return this.Nom + " " + this.JugadorPerNivell.toString();
    }

    public void showAnimation(int Nivell){

        Integer Jugador = this.JugadorPerNivell.get(Nivell);
        if( Jugador == null || Jugador == 0 ){ Jugador = this.JugadorPerNivell.get(Nivell - 1); }
        this.i.setBackgroundResource( this.Colors.get( Jugador ) );

        final AnimationDrawable a = (AnimationDrawable)this.i.getBackground();

        new Thread(new Runnable() {
            public void run() {
                a.start();

                try { Thread.sleep(1000);
                } catch (InterruptedException e) { e.printStackTrace(); }

                a.stop();

            }

        }).start();

    }

    public boolean putFitxa(int Jugador, int Nivell){

        //miro si al mateix nivell ja hi ha una fitxa
        if( this.getJugador(Nivell) > 0 ) return false;

        this.i.setBackgroundResource( this.Colors.get( Jugador ) );
        AnimationDrawable a = (AnimationDrawable)this.i.getBackground();
        a.setAlpha(255);
//        a.start();

        this.JugadorPerNivell.put( Nivell , Jugador );

        return true;
    }

    //Només per IA
    public void removeFitxa(int Jugador, int Nivell) {

        if(Nivell > 1) this.JugadorPerNivell.remove(Nivell);
        else this.JugadorPerNivell.put(Nivell, this.getJugador(Nivell - 1 ));

        this.i.setBackgroundResource( this.Colors.get( this.getJugador(Nivell - 1) ) );
        if(Nivell == 1) this.i.getBackground().setAlpha(255);
        else this.i.getBackground().setAlpha(100);
        if(this.i.getBackground() instanceof AnimationDrawable) {
            AnimationDrawable a = (AnimationDrawable) this.i.getBackground();
            a.start();
        }

    }

    public void setImage(ImageView i){
        this.i = i;
    }

    public void doNewLevel(){
        this.i.getBackground().setAlpha(100);
    }

    static public String getNomCasella(int NumeroCasella){
        return (String) "i" + String.valueOf(NumeroCasella);
    }

    static public int getNumCasella(String Nom){
        return Integer.parseInt((String)Nom.substring(1));
    }

    //Retorna el número de fila i columna que es fa si cliques una casella
    public Vector<String> getFiles(){

        //Agafem el número de casella
        String val = this.Nom.substring(1);
        int NumCasella = Integer.parseInt(val);
        Vector<String> Ret = new Vector<String>();

        if( NumCasella >=1 && NumCasella <=4) Ret.add("F1");
        else if( NumCasella >= 5 && NumCasella <= 8 ) Ret.add("F2");
        else if( NumCasella >= 9 && NumCasella <= 12 ) Ret.add("F3");
        else if( NumCasella >= 13 && NumCasella <= 16 ) Ret.add("F4");

        if( NumCasella == 1 || NumCasella == 6 || NumCasella == 11 || NumCasella == 16 ) Ret.add("D1");
        if( NumCasella == 4 || NumCasella == 7 || NumCasella == 10 || NumCasella == 13 ) Ret.add("D2");

        if( NumCasella == 1 || NumCasella == 5 || NumCasella == 9 || NumCasella == 13 ) Ret.add("C1");
        if( NumCasella == 2 || NumCasella == 6 || NumCasella == 10 || NumCasella == 14 ) Ret.add("C2");
        if( NumCasella == 3 || NumCasella == 7 || NumCasella == 11 || NumCasella == 15 ) Ret.add("C3");
        if( NumCasella == 4 || NumCasella == 8 || NumCasella == 12 || NumCasella == 16 ) Ret.add("C4");

        return Ret;
    }

    static public Vector<Integer> getCasellesFromFila(String fila){

        Vector<Integer> c = new Vector();

        if(fila == "F1"){ c.add(1); c.add(2); c.add(3); c.add(4); }
        if(fila == "F2") { c.add(5); c.add(6); c.add(7); c.add(8); }
        if(fila == "F3") { c.add(9); c.add(10); c.add(11); c.add(12); }
        if(fila == "F4") { c.add(13); c.add(14); c.add(15); c.add(16); }
        if(fila == "C1") { c.add(1); c.add(5); c.add(9); c.add(13); }
        if(fila == "C2") { c.add(2); c.add(6); c.add(10); c.add(14); }
        if(fila == "C3") { c.add(3); c.add(7); c.add(11); c.add(15); }
        if(fila == "C4") { c.add(4); c.add(8); c.add(12); c.add(16); }
        if(fila == "D1") { c.add(1); c.add(6); c.add(11); c.add(16); }
        if(fila == "D2") { c.add(4); c.add(7); c.add(10); c.add(13); }

        return c;
    }
}
