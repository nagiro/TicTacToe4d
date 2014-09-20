package com.tictactoe.nagiro.tictactoe4d;

import android.app.Activity;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;

import org.javatuples.KeyValue;
import org.javatuples.Triplet;
import org.javatuples.Tuple;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.Random;

import static java.lang.Math.random;

public class Joc {

    public int QuantsJugadorsHumans;
    public int QuantsJugadorsMaquina;
    public int QuantsNivells;
    public int TotalJugadors;
    private int Jugador;
    public int Nivell;
    public HashMap<Integer, Casella> Taulell;
    //public HashMap<Integer, HashMap<String, Integer>> Linies = new HashMap<Integer, HashMap<String, Integer>>();
    public HashMap< KeyValue<Integer,Integer>, Integer> Punts = new HashMap< KeyValue<Integer,Integer>, Integer>();              //Nivell, Jugador, Punts
    public HashMap< KeyValue<Integer,String>, Integer> Linies = new HashMap< KeyValue<Integer,String>, Integer>();              //Jugador, Fila, fitxes


    public Joc(HashMap<Integer, Casella> Taulell){

        this.QuantsJugadorsHumans = 4;
        this.QuantsJugadorsMaquina = 0;
        this.TotalJugadors = this.QuantsJugadorsHumans + this.QuantsJugadorsMaquina;
        this.Nivell = 1;
        this.Jugador = 1;
        this.Taulell = Taulell;
        this.QuantsNivells = 5;
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

        //Fem el repartiment inicial del taulell, perquè no estigui buit
        Vector<Integer> tempc = new Vector<Integer>();
        Random r = new Random();
        int player = 1;
        for( int i = 1; i < 17; i++){ tempc.add(i); } //Carrego un vector amb totes les caselles disponibles
        while( tempc.size() > 0){
            int indexCas = r.nextInt(tempc.size());
            int idCasella = tempc.get(indexCas);
            this.DoMoviment(idCasella);
            this.PassaTorn(false);
            tempc.remove(indexCas);
        }

    }


    public Vector<String> EsLinia(Casella c){

        Vector<String> LiniesNoves = new Vector();

        for(String linia : c.getFiles()){
            Integer fitxes = this.Linies.get( new KeyValue(this.Jugador, linia));
            if(fitxes != null){
                int JugadorAnterior = c.getJugador(this.Nivell-1);
                if( JugadorAnterior != this.Jugador ){
                    fitxes = fitxes + 1;
                    this.Linies.put( new KeyValue(this.Jugador, linia), fitxes );
                    Integer FitxesJugadorAnterior = this.Linies.get( new KeyValue(JugadorAnterior, linia) );
                    this.Linies.put( new KeyValue(JugadorAnterior,linia), FitxesJugadorAnterior - 1 );
                }
            }
            if(fitxes == 4){
                Integer PuntsActuals = this.Punts.get( new KeyValue(this.Nivell, this.Jugador ) );
                if(PuntsActuals != null) this.Punts.put( new KeyValue(this.Nivell, this.Jugador), PuntsActuals+1);
                LiniesNoves.add(linia);
            }
        }

        return LiniesNoves;

    }


    public int CalculaPuntsJugador(int Jugador){

        int punts = 0;
        for( int j = this.Nivell ; j > 0; j-- ){
            punts += this.Punts.get( new KeyValue(j,Jugador) );
        }
        return punts;
    }

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
    public Boolean DoMoviment(int NumeroCasella){

        Casella c = this.Taulell.get(NumeroCasella);

        boolean JugadaOK = c.putFitxa( this.getJugador(), this.Nivell );
        if( JugadaOK ){

            Vector<String> LiniesNoves = this.EsLinia(c);
            if(LiniesNoves != null) {
                for (String linia : LiniesNoves) {
                    for (Integer i : Casella.getCasellesFromFila(linia)) {
                        if (i instanceof Integer) {
                            Casella temp = (Casella) Taulell.get(i);
                            temp.showAnimation(this.Nivell);
                        }
                    }
                    //Fem l'animació de les caselles
                }
            }

            this.Taulell.put( c.getNumero() , c );
            return true;
        } else {
            return false;
        }

    }

    //Funció que marca un moviment
    public void UndoDoMoviment(int Nom){


        this.PassaTornAnterior();

        Casella c = this.Taulell.get(Nom);
        c.removeFitxa( this.getJugador(), this.Nivell );

        this.Taulell.put( c.getNumero() , c );
        if(this.getCasellesLliures() == 16 && this.Nivell > 1) this.TornaNivellAnterior();

    }

    public void TornaNivellAnterior(){
        this.Nivell--;
    }

    public void SaltaNivell(boolean ia){
        this.Nivell++;
        for(Map.Entry<Integer,Casella> c : Taulell.entrySet()){
            c.getValue().doNewLevel();
        }
    }

    public int getJugador(){
        return this.Jugador;
    }

    public void PassaTorn(boolean ia){

        if(this.getCasellesLliures() == 0) this.SaltaNivell(ia);

        if( this.getJugador() < this.TotalJugadors ) this.setJugador( this.getJugador() + 1 );
        else { this.setJugador(1); }
    }

    public void PassaTornAnterior(){

        if(this.getCasellesLliures() == 16 && this.Nivell > 1) this.TornaNivellAnterior();

        if(this.getJugador() == 1) this.setJugador( this.TotalJugadors );
        else this.setJugador(this.getJugador() - 1);
    }


    //********************************************
    //Part de IA
    //********************************************

    /** Get next best move for computer. Return int[1] of {Num Casella} */
/*    int moveIA() {
        int[] result = minimax(1, this.getJugador(), Integer.MIN_VALUE, Integer.MAX_VALUE, this);
        return result[1];
    }

    /** Minimax (recursive) at level of depth for maximizing or minimizing player
     with alpha-beta cut-off. Return int[3] of {score, num casella}  */
/*    private int[] minimax(int depth, int myPlayer, int alpha, int beta, Joc JocActual) {
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
    }

    private List<Integer> generateMoves(Joc JocActual) {
        return JocActual.getCasellesLliuresArray();
    }

    private int evaluate(int c, Joc JocActual, int myPlayer ) {
        int score = 0;

        //Diem, que si fa més línies, guanya
        //mirem per les casella quines linies juga
        Casella Cas = JocActual.Taulell.get(c);
        int Jugadors = JocActual.TotalJugadors;
        HashMap<Integer,Integer> PuntsLiniaJugadors;
        PuntsLiniaJugadors = new HashMap<Integer,Integer>(Jugadors);

        for(String fila : Cas.getFiles()){

            int QuantsJugadorsLinia = 0;
            PuntsLiniaJugadors.clear();


            for(int i = 1; i <= Jugadors; i++){
                //miro si tenen alguna casella ocupada.
                HashMap<String,Integer> LiniesJugador= JocActual.Linies.get(i);
                Integer Punts = LiniesJugador.get(fila);
                if(Punts != null){
                    if( Punts > 0 ) { QuantsJugadorsLinia++; }
                    PuntsLiniaJugadors.put(i,Punts);
                }
            }
            if(QuantsJugadorsLinia > 1) score -= 100; //A la línia ja no es pot fer...

            Integer FitxesMeves = PuntsLiniaJugadors.get(myPlayer);
            Integer FitxesAltre = PuntsLiniaJugadors.get(myPlayer-1);
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
*/
    public void setJugador(int jugador) {
        this.Jugador = jugador;
    }
}