package com.tictactoe.nagiro.tictactoe4d;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Interrogante_dos extends Interrogante {
	private Button button1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ej_dos);
	button1=(Button)findViewById(R.id.button1);
	button1.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
		finish();	
		}
	});
	}

}
