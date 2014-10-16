package com.tictactoe.nagiro.tictactoe4d;

import android.app.Activity;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;
import  android.view.animation.Animation;
import android.view.animation.AnimationSet;

import org.javatuples.KeyValue;
import org.javatuples.Triplet;
import org.javatuples.Tuple;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Vector;
import static  java.lang.Math.random;

public class Joc {

    public int QuantsJugadorsHumans;
    public int QuantsJugadorsMaquina;
    public int QuantsNivells;
    public int TotalJugadors;
    public int Nivell;
    public HashMap<Integer, Casella> Taulell;
    //public HashMap<Integer, HashMap<String, Integer>> Linies = new HashMap<Integer, HashMap<String, Integer>>();
    public HashMap< KeyValue<Integer,Integer>, Integer> Punts = new HashMap< KeyValue<Integer,Integer>, Integer>();              //Nivell, Jugador, Punts
    public HashMap< KeyValue<Integer,String>, Integer> Linies = new HashMap< KeyValue<Integer,String>, Integer>();              //Jugador, Fila, fitxes
    public Vector<Integer> OrdreJugadors = new Vector<Integer>();
    public int idOrdreJugadors = 0;
    public SoundPool sp;
    public Activity a;
    public HashMap<Integer,Integer> soundid = new HashMap<Integer,Integer>();

    public Joc(HashMap<Integer, Casella> Taulell, Activity a){

        this.a = a;

        this.QuantsJugadorsHumans = 4;
        this.QuantsJugadorsMaquina = 0;
        this.TotalJugadors = this.QuantsJugadorsHumans + this.QuantsJugadorsMaquina;
        this.Nivell = 1;
        this.QuantsNivells = 5;
        this.Taulell = Taulell;
        for(int i = 1; i < 99; i++){
            for(int j = 1; j <= this.TotalJugadors ; j++){
                this.Punts.put( new KeyValue(i,j) , 0 );
            }
        }

        for( int i = 1; i < 17; i++){
            Casella c = Taulell.get(i);
            for(String fila : c.getFiles() ){
                for(int j = 0 ; j <= this.TotalJugadors; j++){
                    this.Linies.put( new KeyValue(j,fila), 0);
                }
            }
        }
//Repartim els jugadors en un aleatori
        this.doBarrejaJugadors();

        //Fem el repartiment inicial del taulell, perque no estigui buit
        Vector<Integer> tempc = new Vector<Integer>();
        Random r = new Random();
        tempc.clear();
        for( int i = 1; i < 17; i++){ tempc.add(i); } //carrego un vecotr amb totes les caselles disponibles
        while ( tempc.size() > 0){
            int indexCas = r.nextInt( tempc.size());
            int idCasella = tempc.get(indexCas);
            this.doMoviment(idCasella, false);
            this.PassaTorn();
            tempc.remove(indexCas);
        }
    }
//Agafa tots els jugadors i els ordena aleatòriament.
    public void doBarrejaJugadors(){
        Vector<Integer> tempc = new Vector<Integer>();
        Random r = new Random();
        for( int i = 1; i < 5; i++){ tempc.add(i); } //Carrego un vector amb totes les caselles disponibles
        while ( tempc.size() > 0){
            int indexJug = r.nextInt(tempc.size());
            this.OrdreJugadors.add( tempc.get(indexJug) );
            tempc.remove(indexJug);
        }


        this.sp = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
        this.soundid.put(1,  sp.load(this.a, R.raw.cat_growl, 1));
        this.soundid.put(2 , sp.load(this.a, R.raw.pig4, 1));
        this.soundid.put(3 , sp.load(this.a, R.raw.dog_bark2, 1));
        this.soundid.put(4, sp.load(this.a, R.raw.owl, 1));
    }

    public Vector<String> EsLinia(Casella c){

        Vector<String> LiniesNoves = new Vector();

        for(String linia : c.getFiles()){
            Integer fitxes = this.Linies.get( new KeyValue(this.getJugador(), linia));
            if(fitxes != null){
                int JugadorAnterior = c.getJugador(this.Nivell-1);
                if( JugadorAnterior != this.getJugador() ){
                    fitxes = fitxes + 1; //ok revisado))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))))
                    this.Linies.put( new KeyValue(this.getJugador(), linia), fitxes );
                    Integer FitxesJugadorAnterior = this.Linies.get( new KeyValue(JugadorAnterior, linia) );
                    this.Linies.put( new KeyValue(JugadorAnterior,linia), FitxesJugadorAnterior - 1 ); //ok revisado
                }
            }
            if(fitxes == 4){
                Integer PuntsActuals = this.Punts.get( new KeyValue(this.Nivell, this.getJugador() ) );
                if(PuntsActuals != null) this.Punts.put( new KeyValue(this.Nivell, this.getJugador() ), PuntsActuals + 1);//aqui suma un punto por linea (revisado)
                LiniesNoves.add(linia);
            }
        }

        return LiniesNoves;

    }
public void UndoEsLinia(Casella c){

    Vector<String> LiniesNoves = new Vector();
    int FitxesOriginal = 0;
    Integer fitxes = new Integer(0);
    int JugadorNivellAnterior = 0;

    for(String linia : c.getFiles()){
        fitxes = this.Linies.get( new KeyValue(this.getJugador(), linia));
        FitxesOriginal = fitxes;

        if(fitxes != null){
            JugadorNivellAnterior = c.getJugador(this.Nivell-1);
            if ( JugadorNivellAnterior != this.getJugador() ){
                fitxes = fitxes - 1;
                this.Linies.put( new KeyValue(this.getJugador(), linia), fitxes);
                Integer FitxesJugadorNivellAnterior = this.Linies.get( new KeyValue(JugadorNivellAnterior, linia) );
                this.Linies.put( new KeyValue(JugadorNivellAnterior,linia), FitxesJugadorNivellAnterior + 1 );
            }
        }

        if(FitxesOriginal == 4 && (fitxes == 3 || JugadorNivellAnterior == this.getJugador() )){
            Integer PuntsActuals = this.Punts.get( new KeyValue(this.Nivell, this.getJugador() ) );
            if(PuntsActuals != null) this.Punts.put( new KeyValue(this.Nivell, this.getJugador() ),PuntsActuals - 1 );
        }

    }
}




    //aqui he bloqueado el contador de los jugadores**********************************************************************************************************
    public int CalculaPuntsJugador(int Jugador){

        int punts = 0;
        for( int j = this.Nivell ; j > 0; j -- ){
            punts += this.Punts.get( new KeyValue(j,Jugador) );
        }
        return punts;
    }

//*********************************************************************************************************************************************************************
    public int getCasellesLliures(){
        int Total = 0;

        for( Map.Entry<Integer, Casella> c : this.Taulell.entrySet()){
             Casella cas = c.getValue();
             int Jugador = cas.getJugador(this.Nivell);
             if(Jugador == 0) Total++;
        }

        return Total;
    }

    public ArrayList<Integer> getCasellesLliuresArray(){
        ArrayList<Integer> llistat = new ArrayList<Integer>();

        for( Map.Entry<Integer, Casella> c : this.Taulell.entrySet()){
            Casella cas = c.getValue();
            int Jugador = cas.getJugador(this.Nivell);
            if(Jugador == 0) llistat.add(c.getValue().getNumero());
        }

        return llistat;
    }


    //Funció que marca un moviment
    public Boolean doMoviment(int NumeroCasella, boolean ia){

        Casella c = this.Taulell.get(NumeroCasella);

        boolean JugadaOK = c.putFitxa( this.getJugador(), this.Nivell, ia);
        if( JugadaOK ){

            Vector<String> LiniesNoves = this.EsLinia(c); //Mirem quines linies fem amb aquesta casella
            if(LiniesNoves != null && !ia) { //aqui sumamos la inteligencia artificial
                for (String linia : LiniesNoves) {
                    for (Integer i : Casella.getCasellesFromFila(linia)) {
                        if (i instanceof Integer) {
                            Taulell.get(i).showAnimation(this.Nivell);
                            sp.play(this.soundid.get(this.getJugador()), 255, 255, 0, 0, 1);
                        }
                    }
                    //Fem l'animació de les caselles
                }
            }

            this.Taulell.put( c.getNumero() , c );
        }
        return  JugadaOK;
    }

    //Funció que marca un moviment
    public void UndoDoMoviment(int Nom) {
        Casella c = this.Taulell.get(Nom);
        this.UndoEsLinia(c);
        c.removeFitxa(this.getJugador(), this.Nivell);

        this.Taulell.put(c.getNumero(), c);
    }

    public void SaltaNivell(){

        for(Map.Entry<Integer,Casella> c : Taulell.entrySet()){
            c.getValue().doNewLevel(this.Nivell);
        }
        this.Nivell++;
    }

    public int getJugador(){
        return  this.OrdreJugadors.get( this.idOrdreJugadors );
    }
    //retorna el següent jugador
    public int getNextPlayer(){
        int Ordre = this.idOrdreJugadors;
        if( Ordre == this.TotalJugadors - 1 ) Ordre = 0;
        else Ordre++;
        return this.OrdreJugadors.get( Ordre );
    }
      public  int doNextPlayer(){
      if(this.idOrdreJugadors == this.TotalJugadors-1) this.idOrdreJugadors = 0;
      else  this.idOrdreJugadors++;
      return  this.OrdreJugadors.get( this.idOrdreJugadors);
      }
    public void PassaTorn(){
        if(this.getCasellesLliures() == 0) this.SaltaNivell();
        this.doNextPlayer();
    }
    //******************************************************************************
    //PART DE IA
    //******************************************************************************
    //AConseguim el següent millor moviment per la fitxa

    int moveIA() {
        List<Integer> nextMoves = generateMoves(this);
       int score = 0;
        int millorScore = -999999;
        int millorCasella = 0;
        Log.v("------------------------------------------", "------");
        for(Integer numCasella : nextMoves){

            this.doMoviment(numCasella, true );
            score = evaluate(numCasella, this, this.getJugador() );
            Log.v("Mov",String.valueOf(numCasella)+" - "+String.valueOf(score));
            this.UndoDoMoviment(numCasella);

            if(score > millorScore){
                millorCasella = numCasella;
                millorScore = score;
            }

        }
        return  millorCasella;
    }

//*******************************************************************************************************************************ultima ia albert******************************
    //********************************************
    //Part de IA
    //********************************************

    /** Get next best move for computer. Return int[1] of {Num Casella} */
    //primera***************************************************************************************
 /*  int moveIA() {
        int[] result = minimax(1, this.getJugador(), Integer.MIN_VALUE, Integer.MAX_VALUE, this);
        return result[1];
    }*/

    /** Minimax (recursive) at level of depth for maximizing or minimizing player
     with alpha-beta cut-off. Return int[3] of {score, num casella}  */
    //segona***********************************************************************************************
 /* private int[] minimax(int depth, int myPlayer, int alpha, int beta, Joc JocActual) {
        // Generate possible next moves in a list of int[2] of {row, col}.
        List<Integer> nextMoves = generateMoves(JocActual);

        // mySeed is maximizing; while oppSeed is minimizing
        int score;
        int bestCasella = -1;
        int JugadorActual = 0;

        if (nextMoves.isEmpty() || depth == 0) {
            score = 0;
            return new int[] { score, bestCasella };
        } else {
            for (int move : nextMoves) {
                // try this move for the current "player"
                JugadorActual = JocActual.getJugador();
                JocActual.DoMoviment( move );*/
//tercera****************************************************************************************************************
               /* try{ Log.v("JocActual", JocActual.Linia.get(JugadorActual).toString()); } catch (Exception e){}
                //cuarta
                try{ Log.v("JocFutur",JocFutur.Linia.get(JugadorActual).toString()); } catch (Exception e){}
                if ( myPlayer == JugadorActual ) {  // mySeed (computer) is maximizing player

                    int tmp;
                    tmp = minimax( depth - 1, myPlayer, alpha, beta, JocActual )[0];
                    score = tmp + evaluate(move, JocActual, JugadorActual);
                    JocActual.PassaTorn(true);
                    JocActual.UndoDoMoviment( move );
                    //Log.v(nom, String.valueOf(score));
                    if (score > alpha) {
                        alpha = score;
                        bestCasella = move;
                    }
                } else {  // oppSeed is minimizing player
                    int tmp = minimax(depth - 1, myPlayer, alpha, beta, JocActual)[0];
                    score = tmp + evaluate(move, JocActual, JugadorActual);
                    JocActual.PassaTorn(true);
                    JocActual.UndoDoMoviment( move );
                    //Log.v(nom, String.valueOf(score));
                    if (score < beta) {
                        beta = score;
                        bestCasella = move;
                    }
                }
                // cut-off
                if (alpha >= beta) break;
            }
            return new int[] {(myPlayer == JugadorActual) ? alpha : beta, bestCasella };
        }
    }*/
    //FINAL**********************************************************************************************************

    private List<Integer> generateMoves(Joc JocActual) {
        return JocActual.getCasellesLliuresArray();
    }

    private int evaluate(int numCasella, Joc JocActual, int Jugador ) {
        int score = 0;

        //Diem, que si fa més línies, guanya
        //mirem per les casella quines linies juga
        Casella Cas = JocActual.Taulell.get(numCasella);
        int TotalJugadors = JocActual.TotalJugadors;
        HashMap<Integer,Integer> PuntsLiniaJugadors; //Quants punts per linia fa cada jugador (Jugador,Punts)
        PuntsLiniaJugadors = new HashMap<Integer,Integer>(TotalJugadors);
//Agafem les files q toca aquesta casella
        for(String fila : Cas.getFiles()){

            int QuantsJugadorsLinia = 0;
            PuntsLiniaJugadors.clear();

//para todos los jugadores miramos cuantos puntos hacen
            for(int i = 1; i <= TotalJugadors; i++){
                //miro si tenen alguna casella ocupada.
                Integer FitxesALaLinia = JocActual.Linies.get( new KeyValue( i , fila ) );
                if( FitxesALaLinia != null){
                    if ( FitxesALaLinia > 0 ) { QuantsJugadorsLinia++; }
                    PuntsLiniaJugadors.put( i , FitxesALaLinia );


               /* (Anterior programa)HashMap<String,Integer> LiniesJugador= JocActual.Linies.get(i);
                Integer Punts = LiniesJugador.get(fila);
                if(Punts != null){
                    if( Punts > 0 ) { QuantsJugadorsLinia++; }
                    PuntsLiniaJugadors.put(i,Punts);*/
                }
            }
            if(QuantsJugadorsLinia > 1) score -= 100; //A la línia ja no es pot fer...

            Integer FitxesMeves = PuntsLiniaJugadors.get(this.getJugador() );
            Integer FitxesAltre = PuntsLiniaJugadors.get(this.getNextPlayer() );
            if(QuantsJugadorsLinia == 1 && FitxesMeves > 0) score +=20; //Sempre intentem jugar a una línia que poguem fer línia
            switch(FitxesMeves){
                case 1: score +=  50; break;
                case 2: score += 100; break;
                case 3: score += 150; break;
                case 4: score += 300; break;
            }
            if(FitxesAltre != null) {
                switch (FitxesAltre) {
                    case 1:
                        score += 40;
                        break;
                    case 2:
                        score += 90;
                        break;
                    case 3:
                        score += 160;
                        break;
                }
            }

        }
        return score;
    }





   /*(programa anterior) public void setJugador(int jugador) {
        this.Jugador = jugador;*/
    }
