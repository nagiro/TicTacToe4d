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
	private Button button1, button2;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ej_uno);
		button1=(Button)findViewById(R.id.boto_so);
		button2=(Button)findViewById(R.id.boto_ajuda);
		button1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			finish();	
			}
		});
		button2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			Intent in=new Intent( Interrogante.this , com.tictactoe.nagiro.tictactoe4d.Interrogante_dos.class );
			startActivity(in);
			}
		});
	}
}
