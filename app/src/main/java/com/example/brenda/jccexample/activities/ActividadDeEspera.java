package com.example.brenda.jccexample.activities;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.brenda.jccexample.R;
import com.example.brenda.jccexample.dialogos.ProveedorToast;


public class ActividadDeEspera extends Activity {

	private int attempts;

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.esperando_participante);
		if(savedInstanceState == null) {
			attempts = 0;
			try {
				((TextView) findViewById(R.id.message)).setText(getIntent().getExtras().getString("message"));
			} catch (NullPointerException e) {
				e.printStackTrace();
			}
		}else{
			attempts = savedInstanceState.getInt("attempts");
		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState){
		super.onSaveInstanceState(outState);
		outState.putString("message",((TextView)findViewById(R.id.message)).getText().toString());
		outState.putInt("attempts", attempts);
	}
	
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState){
		super.onRestoreInstanceState(savedInstanceState);
		((TextView)findViewById(R.id.message)).setText(savedInstanceState.getString("message"));
		attempts = savedInstanceState.getInt("attempts");
	}

	@Override
	public void onBackPressed(){
		attempts++;
		switch(attempts){
			case 1:
				ProveedorToast.showToast(this, getString(R.string.on_back_pressed_message));
				break;
			case 2:
				super.onBackPressed();
			default:
		}
	}
}