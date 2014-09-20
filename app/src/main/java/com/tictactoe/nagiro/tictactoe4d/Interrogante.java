package com.tictactoe.nagiro.tictactoe4d;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.tictactoe.nagiro.tictactoe4d.R;

public class Interrogante extends Activity {
	private Button button_ej_uno_cancelar, button_ej_uno_continuar;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ej_uno);
		button_ej_uno_cancelar=(Button)findViewById(R.id.button_ej_uno_cancelar);
		button_ej_uno_continuar=(Button)findViewById(R.id.button_ej_uno_continuar);
		button_ej_uno_cancelar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			finish();	
			}
		});
		button_ej_uno_continuar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			Intent in=new Intent( Interrogante.this , com.tictactoe.nagiro.tictactoe4d.Interrogante_dos.class );
			startActivity(in);
			}
		});
	}
}
