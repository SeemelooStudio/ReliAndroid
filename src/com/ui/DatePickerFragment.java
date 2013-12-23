package com.ui;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.EditText;

public  class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

	private final int DATE_PICKER_FROM_DIALOG =1;
    private final int DATE_PICKER_TO_DIALOG = 2;
    private EditText _fromDate;
    private EditText _toDate;
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Use the current date as the default date in the picker
		final Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		int day = c.get(Calendar.DAY_OF_MONTH);
	
		_fromDate = (EditText)this.getActivity().findViewById(R.id.pick_from_date);
		_toDate = (EditText)this.getActivity().findViewById(R.id.pick_to_date);
		// Create a new instance of DatePickerDialog and return it
		return new DatePickerDialog(getActivity(), this, year, month, day);
	}

	public void onDateSet(DatePicker view, int year, int month, int day) {
		String tag = this.getTag();
		if(tag == "from") {
			_fromDate.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date(year-1900, month, day)));
		}
		else if(tag == "to") {
			_toDate.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date(year-1900, month, day)));
		}
		
	}
}