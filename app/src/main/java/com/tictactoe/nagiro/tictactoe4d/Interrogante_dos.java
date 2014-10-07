package com.tictactoe.nagiro.tictactoe4d;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Interrogante_dos extends Interrogante {
	private Button button_ej_dos_cancelar;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ej_dos);
	button_ej_dos_cancelar=(Button)findViewById(R.id.button_ej_dos_cancelar);
	button_ej_dos_cancelar.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
		finish();	
		}
	});
	}

}
