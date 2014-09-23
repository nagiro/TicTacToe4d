package com.tictactoe.nagiro.tictactoe4d;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.tictactoe.nagiro.tictactoe4d.util.SystemUiHider;
import java.util.HashMap;


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 *
 * @see SystemUiHider
 */
public class main extends Activity {

    static private Joc J;
    static private HashMap<Integer, Casella> Taulell = new HashMap<Integer, Casella>();
    public HashMap<Integer, Integer> Colors = new HashMap<Integer, Integer>();

    View.OnClickListener listener = new View.OnClickListener(){
        public void onClick(View v){
            //Rebem un click a una casella
            String nom = getResources().getResourceEntryName(v.getId());
            int Jugador = main.this.J.getJugador();
            if(main.this.J.getJugador() <= main.this.J.QuantsJugadorsHumans){
                doJugadaHuma(nom);
            } else {
                doJugadaMaquina();
            }

            if(main.this.J.Nivell == main.this.J.QuantsNivells) {
                main.this.FiDelJoc();
            }
        }
    };

    public void doJugadaMaquina(){
        int i;
        int Jugador = J.getJugador();

        i = J.moveIA();
        J.doMoviment( i , false );
        this.ActualitzaComptador(Jugador);
        J.PassaTorn();

    }

    public void doJugadaHuma(String nom){

        int Jugador = J.getJugador();
        if( J.doMoviment( Casella.getNumCasella( nom ) , false ) ){
            this.ActualitzaComptador(Jugador);
            J.PassaTorn();
        } else {
            Toast.makeText( main.this.getApplicationContext() ,"Ja hi ha una fitxa d'un altre jugador", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Colors.put(1, R.drawable.f_blava_contador);
        Colors.put(2, R.drawable.f_vermella_contador);
        Colors.put(3, R.drawable.f_groga_contador);
        Colors.put(4, R.drawable.f_verda_contador);
        Colors.put(11,R.drawable.f_blava_contador_f);
        Colors.put(12,R.drawable.f_vermella_contador_f);
        Colors.put(13,R.drawable.f_groga_contador_f);
        Colors.put(14,R.drawable.f_verda_contador_f);

        for(int i=1; i<17; i++) {
            try {

                ImageView Temp = this.getImageViewFromNom(i);
                Temp.setOnClickListener(this.listener);

                Taulell.put( i , new Casella( i, Temp, this ) );

            } catch (Exception e) {
                Log.v("Exception", e.toString());
            }
        }

        this.J = new Joc(Taulell);

        //Mostrem els selectors

        final CharSequence[] items = {"1","2","3","4"};
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setTitle("Human players?");
        b.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                main.this.J.QuantsJugadorsHumans = item + 1;
                main.this.J.QuantsJugadorsMaquina = 4 - ( item + 1 );
                dialog.dismiss();
            }
        });
        b.create().show();

        final CharSequence[] items2 = {"5","10","15","20"};
        AlertDialog.Builder b2 = new AlertDialog.Builder(this);
        b2.setTitle("How many levels?");
        b2.setSingleChoiceItems(items2, -1, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                main.this.J.QuantsNivells = (item + 1)*5;
                dialog.dismiss();
            }
        });
        b2.create().show();

        //Inicialitzem els comptadors
        if(J.getJugador() != 1) findViewById(getResources().getIdentifier("comptador1" ,"id",main.this.getPackageName())).setBackgroundResource( this.Colors.get(11) );
        if(J.getJugador() != 2) findViewById(getResources().getIdentifier("comptador2" ,"id",main.this.getPackageName())).setBackgroundResource(R.drawable.f_vermella_contador_f);
        if(J.getJugador() != 3) findViewById(getResources().getIdentifier("comptador3" ,"id",main.this.getPackageName())).setBackgroundResource(R.drawable.f_groga_contador_f);
        if(J.getJugador() != 4) findViewById(getResources().getIdentifier("comptador4" ,"id",main.this.getPackageName())).setBackgroundResource(R.drawable.f_verda_contador_f);

    }

    public void FiDelJoc(){
        AlertDialog.Builder b2 = new AlertDialog.Builder(this);
        b2.setTitle("End of game!");
        b2.setCancelable(false);
        b2.setPositiveButton("OK", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int id){
                finish();
            }
        });
        b2.create().show();
    }

    public ImageView getImageViewFromNom(int NumCasella){
        String idB = (String) "i" + String.valueOf(NumCasella);
        int resID = main.this.getResources().getIdentifier(idB, "id", main.this.getPackageName());
        ImageView Temp = (ImageView) findViewById(resID);
        return Temp;
    }

    public void ActualitzaComptador(int Jugador){

        int NextPlayer = this.J.getNextPlayer();
        int Player = getResources().getIdentifier("comptador" + Integer.valueOf(Jugador) ,"id",main.this.getPackageName());
        int NextPlayerR = getResources().getIdentifier("comptador" + Integer.valueOf( NextPlayer ) ,"id",main.this.getPackageName()); //Jugador que ha de fer la jugada

        TextView t=(TextView)findViewById(Player);
        int punts = J.CalculaPuntsJugador(Jugador);
        t.setText( String.valueOf(punts) );
        t.setBackgroundResource(this.Colors.get(Jugador+10));

        TextView t2 = (TextView)findViewById(NextPlayerR);
        t2.setBackgroundResource(this.Colors.get(NextPlayer));

    }

}

