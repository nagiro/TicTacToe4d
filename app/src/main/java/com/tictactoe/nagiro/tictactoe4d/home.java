package com.tictactoe.nagiro.tictactoe4d;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.provider.CalendarContract;
//import android.support.v4.app.Fragment;
//import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.tictactoe.nagiro.tictactoe4d.R;

public class home extends Activity {

    private ImageView boto_so, boto_options, boto_musica, boto_ajuda;
    private Button button1;

    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);

      /**  public void Boton() {
        AnimationDrawable animacio_play;
        animacio_play = (AnimationDrawable) getResources().getDrawable(R.drawable.animacio_play);
        Button boto_play = new Button(this);
        boto_play.setBackground(animacio_play);
        animacio_play.start();


        }**/
        boto_ajuda = (ImageView)findViewById(R.id.boto_ajuda);
        boto_ajuda.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                Intent i=new Intent( home.this, Interrogante.class);
                startActivity(i);
            }
        });

        button1 = (Button)findViewById(R.id.boto_play);
        button1.setOnClickListener(new OnClickListener(){
            public void onClick(View v){
                Intent i = new Intent ( home.this , main.class);
                startActivity(i);
            }
        });

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
    //He dejado el contenido suprimido del placeHolderFragment pues me daba error y no se usa
	/*public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.home, container,
					false);
			return rootView;
		}
	}*/

}
