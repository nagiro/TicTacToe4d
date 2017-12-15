package com.tictactoe.nagiro.tictactoe4d;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.provider.CalendarContract;
//import android.support.v4.app.Fragment;
//import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.tictactoe.nagiro.tictactoe4d.R;

public class home extends Activity {
private SoundPool sp;
    private ImageView  boto_options, boto_ajuda, boto_play;
    private ToggleButton boto_musica;
   // private Button button1;
private  Toast toast;
    private long lastBackPressTime = 0;
    private boolean HiHaSo = true;
    @Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);


        boto_ajuda = (ImageView)findViewById(R.id.boto_ajuda);
        boto_ajuda.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                Intent i=new Intent( home.this, Interrogante.class);
                startActivity(i);
            }
        });

        boto_musica = (ToggleButton)findViewById(R.id.boto_musica);
        boto_musica.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                home.this.HiHaSo = !b;
            }
        });

        boto_play = (ImageView)findViewById(R.id.boto_play);
        boto_play.setOnClickListener(new OnClickListener(){
            public void onClick(View v){
                Intent i = new Intent ( home.this , main.class);
                i.putExtra("HiHaSo",(Boolean)home.this.HiHaSo);
                startActivity(i);
            }
        });

	}
    public  void onBackPressed(){
        Toast toastExit = new Toast(getApplicationContext());
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.layout_exit, (ViewGroup)findViewById(R.id.exit_layout));
        TextView textoExit = (TextView)findViewById(R.id.textoExit);
        if( this.lastBackPressTime < System.currentTimeMillis() - 4000 ){
            toastExit.setDuration(Toast.LENGTH_LONG);
            toastExit.setView(layout);
            toastExit.setGravity(Gravity.CENTER|Gravity.LEFT,0,0);
            toastExit.show();
            this.lastBackPressTime = System.currentTimeMillis();

        }else{
            if(toast != null){
                toast.cancel();
            }
            super.onBackPressed();
        }
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
