package com.tictactoe.nagiro.tictactoe4d;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Loader;
import android.media.AudioManager;
import android.media.SoundPool;
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
        public void onClick(View v) {
            //Rebem un click de la casella
            String nom = getResources().getResourceEntryName(v.getId());
            int Jugador = main.this.J.getJugador();
            // int Maquina = main.this.J.getMaquina();
            if (main.this.J.getJugador() <= main.this.J.QuantsJugadorsHumans) {
                doJugadaHuma(nom);

            } else {
                doJugadaMaquina();

            }
            if (main.this.J.Nivell == main.this.J.QuantsNivells) {
                main.this.FiDelJoc();
            }
        }
    };

   public void doJugadaMaquina(){
        int i;
        int Jugador = J.getJugador();


           i = J.moveIA();
    J.doMoviment( i , false );
   //     J.PassaTorn(false);

        this.ActualitzaComptador(Jugador);
J.PassaTorn();
    }

    public void doJugadaHuma(String nom) {

        int Jugador = J.getJugador();
        if ( J.doMoviment(Casella.getNumCasella( nom ), false ) ) {
            this.ActualitzaComptador(Jugador);

        J.PassaTorn();
    }else {
            Toast.makeText(main.this.getApplicationContext(), "TRY AGAIN", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Colors.put(1, R.drawable.f_blava_contador_o);
        Colors.put(2, R.drawable.f_vermella_contador);
        Colors.put(3, R.drawable.f_groga_contador);
        Colors.put(4, R.drawable.f_verda_contador);
        Colors.put(11, R.drawable.f_blava_contador_fo);
        Colors.put(12, R.drawable.f_vermella_contador_f);
        Colors.put(13, R.drawable.f_groga_contador_f);
        Colors.put(14, R.drawable.f_verda_contador_f);

        for (int i = 1; i < 17; i++) {
            try {

                ImageView Temp = this.getImageViewFromNom(i);
                Temp.setOnClickListener(this.listener);

                Taulell.put(i, new Casella(i, Temp, this));

            } catch (Exception e) {
                Log.v("Exception", e.toString());
            }
        }

        this.J = new Joc(Taulell,this);
//Mostramos los selectores de jugadores y de niveles++++++++++++++++++++++++++++++++++++++
        final CharSequence[] items = {"1 (CAT)", "2 (CAT+PIG)", "3 (CAT+PIG+DOG)", "4 (CAT+PIG+DOG+OWL)"};
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setTitle("Human players?");
        b.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                main.this.J.QuantsJugadorsHumans = item + 1;
                main.this.J.QuantsJugadorsMaquina = 4 - (item + 1);
                dialog.dismiss();
            }
        });

        b.create().show();


        final CharSequence[] items2 = {"2", "6", "10", "14"};
        AlertDialog.Builder b2 = new AlertDialog.Builder(this);
        b2.setTitle("How many levels?");
        b2.setSingleChoiceItems(items2, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                main.this.J.QuantsNivells = (item + 1) * 4;
                dialog.dismiss();
            }
        });
        b2.create().show();

        //Inicialitzem els contadors

        if (J.getJugador() != 1)
            findViewById(getResources().getIdentifier("comptador1", "id", main.this.getPackageName())).setBackgroundResource( this.Colors.get(11) );
        if (J.getJugador() != 2)
            findViewById(getResources().getIdentifier("comptador2", "id", main.this.getPackageName())).setBackgroundResource(R.drawable.f_vermella_contador_f);
        if (J.getJugador() != 3)
            findViewById(getResources().getIdentifier("comptador3", "id", main.this.getPackageName())).setBackgroundResource(R.drawable.f_groga_contador_f);
        if (J.getJugador() != 4)
            findViewById(getResources().getIdentifier("comptador4", "id", main.this.getPackageName())).setBackgroundResource(R.drawable.f_verda_contador_f);

        this.setVolumeControlStream(AudioManager.STREAM_MUSIC);

    }
    //aqui introduzco variable b3 pues en el anterior era variable b2 era un error+++++++++++++++++++++++++++++++++++++++
    public  void FiDelJoc(){
        AlertDialog.Builder b3 = new AlertDialog.Builder(this);
        b3.setTitle("End of game!");
        b3.setCancelable(false);
        b3.setPositiveButton("OK", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int id){
                finish();
            }

        });
        b3.create().show();
    }
    public ImageView getImageViewFromNom(int NumCasella){
        String idB = (String) "i" + String.valueOf(NumCasella);
        int resID = main.this.getResources().getIdentifier(idB, "id", main.this.getPackageName());
        ImageView Temp = (ImageView) findViewById(resID);
        return Temp;
    }

    public void ActualitzaComptador(int Jugador){

        int NextPlayer = this.J.getNextPlayer();
        int Player = getResources().getIdentifier("comptador" + Integer.valueOf(Jugador) , "id",main.this.getPackageName());
       // int  resID=getResources().getIdentifier("comptador"+(int)Jugador,"id",main.this.getPackageName());
        int NextPlayerR = getResources().getIdentifier("comptador" + Integer.valueOf( NextPlayer ) , "id",main.this.getPackageName());//jugador q ha de fer la jugada

        TextView t=(TextView)findViewById(Player);
        //aqui he bloqueado el contador de los jugadores******************************************************************************************************
        int punts = J.CalculaPuntsJugador(Jugador);
        t.setText( String.valueOf(punts) );
        t.setBackgroundResource(this.Colors.get(Jugador+10));
//**************************************************************************************************************************

        TextView t2 = (TextView)findViewById(NextPlayerR);
        t2.setBackgroundResource(this.Colors.get(NextPlayer));

    }

}
