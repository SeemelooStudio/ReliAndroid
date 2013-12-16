package com.util;

import java.util.HashMap;
import java.util.Map;

import com.ui.R;


public class CellBackgroundHelper {
	private static Map<Integer, Integer> mapBackgroundResources;
	public static int CELL_STATE_DEFAULT = 0;
	public static int CELL_STATE_EXCEED_STANDARD = 1;
	public static int CELL_STATE_BELOW_STANDARD = 2;
	static
    {
		mapBackgroundResources = new HashMap<Integer, Integer>();
		mapBackgroundResources.put(CELL_STATE_DEFAULT, R.drawable.button_default);
		mapBackgroundResources.put(CELL_STATE_EXCEED_STANDARD, R.drawable.button_warn);
		mapBackgroundResources.put(CELL_STATE_BELOW_STANDARD, R.drawable.button_dark);

    }
	static public Integer getBackgroundResourceByCellState( Integer intStateId) {
		Integer intResourceId = CELL_STATE_DEFAULT;
		intResourceId = mapBackgroundResources.get(intStateId);
		return intResourceId;
	}
}
