package com.example.sudoku;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.os.Bundle;


public class LevelTwo extends GamePlay {	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		sudokuRandomizer();
		setMatrix();
	}
	
//	private void createMatrix() {
//		int n = 2;
//		int nn = n * n;
//		field = new int[nn][nn];
//		for (int i = 0; i < nn; i++) {
//			for (int j = 0; j < nn; j++) {
//				field[i][j] = (i * n + i / n + j) % (nn) + 1;
//			}
//		}
//	}
	
	private void sudokuRandomizer() {
		// Set all the possible 2 digit combinations that can appear
		// in a correctly filled Sudoku matrix
		final String[] combinations = new String[]{
									"12", "13", "14",
									"21", "23", "24",
									"31", "32", "34",
									"41", "42", "43"
								};
		
		// Initate a randomGenerator
		Random randomGenerator = new Random();
		// Pick a random number from the above combinations
		String firstSet = combinations[randomGenerator.nextInt(combinations.length)];
		
		char first = firstSet.charAt(0);
		char second = firstSet.charAt(1);
		
		String secondSet;
		// Pick a second set, which contains the last digit of the 
		// 1st set as its first digit
		// Also ensure that the second digit of this set, is not the
		// same as the first digit of the 1st set
		do {
			secondSet = combinations[randomGenerator.nextInt(3) + ((second - 48 - 1) * 3)];
		} while (secondSet.charAt(1) == first);
		char third = secondSet.charAt(1);
		
		char fourth = Integer.toString(154 - (first + second + third)).charAt(0);
		// Generate the 3rd and 4th sets
		String thirdSet = "" + third + fourth;
		String fourthSet = "" + fourth + first;
		
		String top, bottom;
		// Store the combination values for blocks in the second column
		String endFirstSet, endSecondSet, endThirdSet, endFourthSet;
		// Generate a random number to figure out whether we need to
		// apply a mirroring effect for the blocks in the second column
		int reverse = randomGenerator.nextInt(2);
		// If there is no need to reverse the values, set the end strings
		// equivalent to their respective strings
		if (reverse == 0) {
			endFirstSet = firstSet;
			endSecondSet = secondSet;
			endThirdSet = thirdSet;
			endFourthSet = fourthSet;
		}
		// If a mirroring effect is to be applied reverse the original values
		// to come up with the end strings
		else {
			endFirstSet = new StringBuffer(firstSet).reverse().toString();
			endSecondSet = new StringBuffer(secondSet).reverse().toString();
			endThirdSet = new StringBuffer(thirdSet).reverse().toString();
			endFourthSet = new StringBuffer(fourthSet).reverse().toString();
		}

		// Compile the top 2 rows for both columns
		top = firstSet + endThirdSet + thirdSet + endFirstSet;
		int i = randomGenerator.nextInt(2);
		if (i == 0) {
			// Compile the bottom 2 rows for both columns where the 
			// secondSet is placed on top
			bottom = secondSet  + endFourthSet + fourthSet  + endSecondSet;
		}
		else {
			// Compile the bottom 2 rows for both columns where the 
			// fourthSet is placed on top
			bottom = fourthSet + endSecondSet + secondSet + endFourthSet;
		}
		
		generateMatrix(top + bottom);
	}
	
	private void generateMatrix(String matrix) {
		int n = 4;
		field = new int[n][n];
		for (int i = 0; i < n * n; i++) {
			field[i / 4][i % 4] = matrix.charAt(i) - 48;
		}
	}
	
	private void setMatrix() {
		Random rand = new Random();
		
		List<Integer> display_num = new ArrayList<Integer>();
		int count = 0;
		// Show 8 of the possible 16 tiles
		do { 
			int num = rand.nextInt(16);
			if (display_num.indexOf(num) == -1) {
				display_num.add(num);
				// Get the value from the previously generate matrix
				int value = field[num / 4][num % 4] - 1;
			
				int child = tile[value].getChildCount();
				// Remove the last child from the FrameLayout tiles
				tile[value].removeViewAt(child - 1);
				// Add the tile into the correct LinearLayout
				receiver_grid[num].addView(btn_tile[value * 4 + child - 1]);
				count++;
			}
		} while (count < 8);
	}
}
