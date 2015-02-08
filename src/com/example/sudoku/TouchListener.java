package com.example.sudoku;

import android.content.ClipData;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.view.View.OnTouchListener;


// Custom OnTouchListener implementation
public final class TouchListener implements OnTouchListener {
	
	public boolean onTouch(View view, MotionEvent motionEvent) {
		// Check if finger is pressed down to start motion
		if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
			ClipData data = ClipData.newPlainText("id", String.valueOf(view.getId()));
			// Use a DragShadowBuilder to create the transparent image 
			// that moves with the finger around the screen
			DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
			view.startDrag(data, shadowBuilder, view, 0);
			return true;
		}
		return false;
	}
}
