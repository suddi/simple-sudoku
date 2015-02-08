package com.example.sudoku;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener{
	// Level 1 and Level 2 buttons
	Button btn_level_1, btn_level_2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		btn_level_1 = (Button)findViewById(R.id.btn_level_1);
		btn_level_2 = (Button)findViewById(R.id.btn_level_2);
		
		btn_level_1.setOnClickListener(this);
		btn_level_2.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public void onClick(View v) {
		Intent intent;
		switch (v.getId()) {
			// Change page to according to the buttton press
			case R.id.btn_level_1:
				intent = new Intent(getBaseContext(), LevelOne.class);
				startActivity(intent);
				break;
			case R.id.btn_level_2:
				intent = new Intent(getBaseContext(), LevelTwo.class);
				startActivity(intent);
				break;
		}
	}
}
