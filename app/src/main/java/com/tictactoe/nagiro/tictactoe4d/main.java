package com.tictactoe.nagiro.tictactoe4d;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.content.res.Resources;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.Image;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;


import com.tictactoe.nagiro.tictactoe4d.util.SystemUiHider;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

//Ad network-specific imports (AdMob))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))

//import com.google.ads.Ad;
import com.google.android.gms.ads.AdRequest;
//import com.google.android.gms.ads.a;
//import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdView;
//import com.google.ads.AdRequest;
//import com.google.ads.AdRequest.ErrorCode;
import com.google.android.gms.ads.AdSize;


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 *
 * @see SystemUiHider
 */
public class main extends Activity {

 //ATRIBUTOS para e≤l ADMOB)))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))
    private TextView mAdStatus;
private AdView adView;
    private static final String AD_UNIT_ID = "ca-app-pub-9472252394349337/7895002601";
  //)))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))
    static private Joc J;
    Animal[] _animals;
    static private HashMap<Integer, Casella> Taulell = new HashMap<Integer, Casella>();
    public HashMap<Integer, Integer> Colors = new HashMap<Integer, Integer>();

//public HashMap<Integer, Integer> SeleccioJugador = new HashMap<Integer, Integer>();
    View.OnClickListener listener = new View.OnClickListener(){
        public void onClick(View v) {
            //Rebem un click de la casella
            String nom = getResources().getResourceEntryName(v.getId());
            int Jugador = main.this.J.getJugador();
            // int Maquina = main.this.J.getMaquina();

            if (main.this.J.getJugador() <= main.this.J.QuantsJugadorsHumans) {

                doJugadaHuma(nom);

                if( main.this.J.getJugador() == main.this.J.QuantsJugadorsHumans + 1 ){

                    final View taulell = findViewById(R.id.i1);
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() { public void run() { taulell.performClick(); }} , 1000 );

                }

            } else {

                doJugadaMaquina();

                if( main.this.J.getJugador() != 1 ) {

                    final View taulell = findViewById(R.id.i1);
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() { public void run() { taulell.performClick(); }} , 1000 );

                }
            }

            if (main.this.J.Nivell == main.this.J.QuantsNivells) {


                main.this.FiDelJoc();

            }
            TextView niveles = (TextView)findViewById(R.id.niveles);
            if (main.this.J.Nivell == 1)
                niveles.setText("  LEVEL 1");
                //Toast.makeText(main.this.getApplicationContext(), "LEVEL 1", Toast.LENGTH_SHORT).show();
            if (main.this.J.Nivell == 2)
                niveles.setText("  LEVEL 2");
               // Toast.makeText(main.this.getApplicationContext(), "LEVEL 2", Toast.LENGTH_SHORT).show();
            if (main.this.J.Nivell == 3)
                niveles.setText("  LEVEL 3");
                //Toast.makeText(main.this.getApplicationContext(), "LEVEL 3", Toast.LENGTH_SHORT).show();
            if (main.this.J.Nivell == 4)
                niveles.setText("  LEVEL 4, LAST LEVEL");
               // Toast.makeText(main.this.getApplicationContext(), "LAST LEVEL", Toast.LENGTH_SHORT).show();



        }
    };

   public void doJugadaMaquina(){
        int i;
        int Jugador = J.getJugador();


           i = J.moveIA();
           J.doMoviment( i , false );
      // J.PassaTorn(false);

        this.ActualitzaComptador(Jugador);
        J.PassaTorn();
    }

    public void doJugadaHuma(String nom) {

        int Jugador = J.getJugador();
        if ( J.doMoviment(Casella.getNumCasella( nom ), false ) ) {
            this.ActualitzaComptador(Jugador);

        J.PassaTorn();

    }else {
          //  Toast.makeText(main.this.getApplicationContext(), "TRY AGAIN", Toast.LENGTH_SHORT).show();
            //Avisamos con el oso de que no se puede colocar una ficha)))))))))))))))))))))))))))))))))))))))))
            Toast toastFicha = new Toast(getApplicationContext());
            LayoutInflater inflater = getLayoutInflater();
            View layout = inflater.inflate(R.layout.fitxa_colocada, (ViewGroup)findViewById(R.id.oso) );
            TextView textOso = (TextView)findViewById(R.id.textOso);
            toastFicha.setDuration(Toast.LENGTH_SHORT);
            toastFicha.setView(layout);
            toastFicha.setGravity(Gravity.CENTER_HORIZONTAL,0,0);
            toastFicha.show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //ADMOB crear adView))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))
        adView = new AdView(this);
        adView.setAdUnitId(AD_UNIT_ID);
        adView.setAdSize(AdSize.BANNER);
//Buscar LinearLayout Suponiendo que se le ha asignado
        //el atributo android:id=@+id/mainLayout".
        LinearLayout publicidad = (LinearLayout)findViewById(R.id.publicidad);
        //añadirle adView;
        publicidad.addView(adView);
        //iniciar una solicitud generica. ESTE CREO QUE ES EL QUE FUNCIONA CUANDO ESTA COLGADO????????????????????????????
       AdRequest adRequest = new AdRequest.Builder().build();
        //ESTE ES EL QUE SE CARGA DE PRUEBA QUE LUEGO HAY QUE BORRAR???????????????????????????????????????????
       // AdRequest adRequest = new AdRequest.Builder()
            //    .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
             //   .addTestDevice("359276056151684")//pon aqui la id d tu telefono es solo para pruebas
             //   .build();
        // cargar adView con la solicitud de anuncio.
        adView.loadAd(adRequest);

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



        this.loadAnimals();

        ListAdapter adapter = new AnimalAdapter( this, _animals );

        final AlertDialog.Builder ad = new AlertDialog.Builder(this);
        ad.setIcon(R.drawable.animacio_blava_fi);
        ad.setTitle("Players?");
        // define the alert dialog with the choices and the action to take
        // when one of the choices is selected
        ad.setSingleChoiceItems( adapter, -1, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // a choice has been made!
                Integer selectedVal = _animals[which].getVal();

                main.this.J.QuantsJugadorsHumans =  selectedVal.intValue();
                main.this.J.QuantsJugadorsMaquina = 4 - (selectedVal.intValue());
                dialog.dismiss();
            }
        });
        ad.show();

       // ))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))

//27/10/14 bloqueado elección de niveles solo funciona con 2 niveles

      /*  final CharSequence[] items2 = {"2", "6", "10", "14"};
        AlertDialog.Builder b2 = new AlertDialog.Builder(this);
        b2.setTitle("How many levels?");
        b2.setSingleChoiceItems(items2, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                main.this.J.QuantsNivells = (item + 1) * 4;
                dialog.dismiss();
            }
        });
        b2.create().show();*/
//)))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))
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

        Intent i = getIntent();
        main.J.HiHaSo = (Boolean)i.getBooleanExtra("HiHaSo",true);

    }

    /**
     * Create all the choices for the list
     */
    private void loadAnimals() {
        _animals = new Animal[4];

        _animals[0]  = new Animal( getImg( R.drawable.player_1 ), 1 );
        _animals[1]  = new Animal( getImg( R.drawable.player_2 ), 2 );
        _animals[2]  = new Animal( getImg( R.drawable.player_3 ), 3 );
        _animals[3]  = new Animal( getImg( R.drawable.player_4 ), 4 );
    }

    private Drawable getImg( int res )
    {
        Drawable img = getResources().getDrawable( res );
        img.setBounds( 0, 0, 48, 48 );
        return img;
    }


    //aqui introduzco variable b3 pues en el anterior era variable b2 era un error+++++++++++++++++++++++++++++++++++++++
    public  void FiDelJoc() {
        int J1, J2, J3, J4;

        LinearLayout layout = new LinearLayout(this);
        ImageView i1 = new ImageView(this);
        i1.setImageResource(R.drawable.animacio_blaves);
        ImageView i2 = new ImageView(this);
        i2.setImageResource(R.drawable.animacio_vermelles);
        ImageView i4 = new ImageView(this);
        i4.setImageResource(R.drawable.animacio_verdes);
        ImageView i3 = new ImageView(this);
        i3.setImageResource(R.drawable.animacio_grogues);


        AlertDialog.Builder b3 = new AlertDialog.Builder(this);

        J1 = J.CalculaPuntsJugador(1);
        J2 = J.CalculaPuntsJugador(2);
        J3 = J.CalculaPuntsJugador(3);
        J4 = J.CalculaPuntsJugador(4);

        int max = 0;

        //Busquem el màxim
        for(int i=1; i < 5; i++){

            if(max < J.CalculaPuntsJugador(i)){
                max = J.CalculaPuntsJugador(i);
            }

        }

        ImageView im = null;
        for(int i=1; i < 5; i++){

            if(max == J.CalculaPuntsJugador(i)){
                switch(i){
                    case 1:
                        im = new ImageView(this);
                        im.setImageResource(R.drawable.animacio_blaves);
                        break;
                    case 2:
                        im = new ImageView(this);
                        im.setImageResource(R.drawable.animacio_vermelles);
                        break;
                    case 3:
                        im = new ImageView(this);
                        im.setImageResource(R.drawable.animacio_grogues);
                        break;
                    case 4:
                        im = new ImageView(this);
                        im.setImageResource(R.drawable.animacio_verdes);
                        break;
                }

                layout.addView(im, 150, 150);
            }

        }

        b3.setView(layout);

        b3.setTitle("End of game!");

        b3.setCancelable(false);
        b3.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
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



    @Override

    public void onPause(){
        if(adView != null) {
            adView.pause();
        }
        super.onPause();
    }
@Override

    public void  onResume(){
    super.onResume();
    if(adView != null){
        adView.resume();
    }

}
    //Called before the activity is destroyed.
  @Override

    public void onDestroy(){
      //Destroy the AdView
      if(adView != null) {
          adView.destroy();
      }
      super.onDestroy();
  }


}
