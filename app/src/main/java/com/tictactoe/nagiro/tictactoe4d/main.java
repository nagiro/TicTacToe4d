package com.tictactoe.nagiro.tictactoe4d;

import android.app.Activity;
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

    private Joc J;
    private HashMap<Integer, Casella> Taulell = new HashMap<Integer, Casella>();

    View.OnClickListener listener = new View.OnClickListener(){
        public void onClick(View v){
            String nom = getResources().getResourceEntryName(v.getId());
            doJugadaHuma(nom);
            //if(doJugadaHuma( nom )) doJugadaMaquina();
        }
    };

/*    public void doJugadaMaquina(){
        int i;
        int Jugador = J.getJugador();

        do{
           i = J.moveIA();
        }while( !J.DoMoviment( i ));
        J.PassaTorn(false);

        this.ActualitzaComptador(Jugador);

    }
*/
    public boolean doJugadaHuma(String nom){

        int Jugador = J.getJugador();
        Boolean OK = J.DoMoviment( Casella.getNumCasella( nom ) );
        J.PassaTorn(false);

        if(!OK) Toast.makeText( main.this.getApplicationContext() ,"Ja hi ha una fitxa d'un altre jugador", Toast.LENGTH_SHORT).show();
        else this.ActualitzaComptador(Jugador);

        return OK;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        for(int i=1; i<17; i++) {
            try {

                ImageView Temp = this.getImageViewFromNom(i);
                Temp.setOnClickListener(this.listener);

                Taulell.put( i , new Casella( i , Temp ) );

            } catch (Exception e) {
                Log.v("Exception", e.toString());
            }
        }

        this.J = new Joc(Taulell);

    }

    public ImageView getImageViewFromNom(int NumCasella){
        String idB = (String) "i" + String.valueOf(NumCasella);
        int resID = main.this.getResources().getIdentifier(idB, "id", main.this.getPackageName());
        ImageView Temp = (ImageView) findViewById(resID);
        return Temp;
    }

    public void ActualitzaComptador(int Jugador){
        int resID=getResources().getIdentifier("comptador"+(int)Jugador,"id",main.this.getPackageName());
        TextView t=(TextView)findViewById(resID);
        int punts = J.CalculaPuntsJugador(Jugador);
        t.setText( String.valueOf(punts) );

    }

}