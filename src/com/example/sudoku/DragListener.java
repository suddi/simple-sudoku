package com.example.sudoku;

import android.graphics.drawable.Drawable;
import android.view.DragEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnDragListener;
import android.widget.LinearLayout;


public final class DragListener implements OnDragListener {
	Drawable enterShape, normalShape;
	
	// Constructor for my custom OnDragListener
	DragListener(Drawable shape1, Drawable shape2) {
		// Collect the shape background to be used when an object is
		// hovering over the LinearLayout and when it is not
		enterShape = shape1;
		normalShape = shape2;
	}
	
	@Override
	public boolean onDrag(View grid, DragEvent event) {
		switch (event.getAction()) {
			case DragEvent.ACTION_DRAG_STARTED:
				break;
			case DragEvent.ACTION_DRAG_ENTERED:
				// If entered over the View, show a highlight tint
				grid.setBackgroundDrawable(enterShape);
				break;
			case DragEvent.ACTION_DRAG_EXITED:
				// Remove highlight tint if the object is moved out
				// of the View
				grid.setBackgroundDrawable(normalShape);
				break;
			case DragEvent.ACTION_DROP:
				LinearLayout container = (LinearLayout) grid;
				// Confirm that the position does not already have 
				// a tile placed on it
				if (container.getChildCount() == 0) {
					View tile = (View) event.getLocalState();
					// Add the tile to the LinearLayout and remove
					// it from its original parent view
					ViewGroup owner = (ViewGroup) tile.getParent();
					owner.removeView(tile);
					container.addView(tile);
					tile.setVisibility(View.VISIBLE);
				}
				break;
			case DragEvent.ACTION_DRAG_ENDED:
				// Remove highlight tint if the drag has ended
				grid.setBackgroundDrawable(normalShape);
				break;
			default:
				break;
		}
		return true;
	}
}
