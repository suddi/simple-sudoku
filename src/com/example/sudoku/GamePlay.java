package com.example.sudoku;

import java.util.Arrays;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;


// The super class for both Level 1 and Level 2
public class GamePlay extends Activity implements OnClickListener {
	// The LinearLayout containing the Sudoku label
	// Clicking on this will take the user back to the main page
	LinearLayout btn_home;
	Button btn_submit;
	
	// All the ImageView tiles
	ImageView[] btn_tile;
	LinearLayout[] receiver_grid;
	FrameLayout[] tile;
	
	int[][] field;
	
	AlertDialog.Builder endDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);
		
		btn_home = (LinearLayout)findViewById(R.id.btn_home);
		btn_submit = (Button)findViewById(R.id.btn_submit);
		
		btn_tile = new ImageView[16];
		tile = new FrameLayout[4];
		int btn_count = 0;
		int tile_count = 0;
		// Retrieve the ImageViews and FrameLayout of the tiles
		for (int i = 0; i < btn_tile.length + 4; i++) {
			if (i % 5 != 0) {
				btn_tile[btn_count] = (ImageView)findViewById(R.id.tile1 + i);
				btn_tile[btn_count].setTag(i);
				// Add a OnTouchListener to the tiles
				btn_tile[btn_count].setOnTouchListener(new TouchListener());
				btn_count++;
			}
			else {
				tile[tile_count] = (FrameLayout)findViewById(R.id.tile1 + i);
				tile_count++;
			}
		}
		
		receiver_grid = new LinearLayout[16];
		// Set the background to be used when an object hovers over the 
		// layout and when there is nothing
		Drawable enterShape = getResources().getDrawable(R.drawable.container_drop);
		Drawable normalShape = getResources().getDrawable(R.drawable.container);
		
		for (int i = 0; i < receiver_grid.length; i++) {
			receiver_grid[i] = (LinearLayout)findViewById(R.id.pos1_1 + i);
			// Add a OnDragListener to the Sudoku matrix LinearLayouts
			receiver_grid[i].setOnDragListener(new DragListener(enterShape, normalShape));
		}
		
		// btn_submit.setVisibility(View.INVISIBLE);
		
		btn_home.setOnClickListener(this);
		btn_submit.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.level_one, menu);
		return true;
	}

	@Override
	public void onClick(View view) {
		Intent intent;
		switch (view.getId()) {
			// Go back to the main page if clicked
			case R.id.btn_home:
				intent = new Intent(getBaseContext(), MainActivity.class);
				startActivity(intent);
				break;
			// Submit answer if clicked
			case R.id.btn_submit:
				checkAnswer();
		}
	}
	
	private void checkAnswer() {
		int n = 4;
		int[][] rows = new int[n][n];
		int[][] columns = new int[n][n];
		
		// Instantiate the AlertDialog to be used
		endDialog = new AlertDialog.Builder(this);
		endDialog.setCancelable(false);
		endDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int id) {
				// End this Activity upon clicking "OK"
				GamePlay.this.finish();
			}
		});
		
		for (int i = 0; i < receiver_grid.length; i++) {
			// If there is any empty positions in the matrix
			// the player immediately loses
			if (receiver_grid[i].getChildCount() == 0) {
				endGame("You Lose!", "You did not fill all the positions in the sudoku matrix");
				return;
			}
			else {
				// Collect the value of the child of the LinearLayout
				int value = ((receiver_grid[i].getChildAt(0).getId() - R.id.tile1) / 5) + 1;
				int div = i / n;
				int mod = i % n;
				// Compile the row major array and column major array
				rows[div][mod] = value;
				columns[mod][div] = value;
			}
		}
		
		// Generate the various blocks 
		int[][] blocks = new int[][]{
					{rows[0][0], rows[0][1], rows[1][0], rows[1][1]},
					{rows[0][2], rows[0][3], rows[1][2], rows[1][3]},
					{rows[2][0], rows[2][1], rows[3][0], rows[3][1]},
					{rows[2][2], rows[2][3], rows[3][2], rows[3][3]}
		};
		
		// Set the comparison array
		int[] comparison = new int[]{1, 2, 3, 4};
		for (int i = 0; i < n; i++) {
			// Sort all of the arrays
			// Expected result is that they will all be {1, 2, 3, 4}
			Arrays.sort(rows[i]);
			Arrays.sort(columns[i]);
			Arrays.sort(blocks[i]);
			
			// If any of the arrays are not equivalent to the comparison
			// array, the player loses the game as this Condition 1
			// and Condition 2 are not met
			if (!Arrays.equals(comparison, rows[i]) ||
			    !Arrays.equals(comparison, columns[i]) ||
			    !Arrays.equals(comparison,  blocks[i])) {
				endGame("You Lose!", "You did not place all the tiles correctly");
				return;
			}
		}
		
		// If the program makes it here, the user has won
		endGame("You Win!", "You completed the sudoku matrix correctly");
	}
	
	private void endGame(String title, String message) {
		// Set the message and title and display the AlertDialog
		endDialog.setTitle(title);
		endDialog.setMessage(message);
		AlertDialog dialogBox = endDialog.create();
		dialogBox.show();
	}
}
