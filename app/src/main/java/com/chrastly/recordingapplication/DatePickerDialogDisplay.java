package com.chrastly.recordingapplication;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.view.View;
import android.widget.DatePicker;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;

public class DatePickerDialogDisplay {


    private int calendarDate, calendarMonth, calendarYear, calendarHour, calendarMinute;
    private Context activityContext;
    private String outputDate = null;
    private Activity activity;

    public void setContext(Context context){
        activityContext = context;
    }

    public void setActivity(Activity act){
        activity = act;
    }

    public void datePickerDialogDisplay(){
        final Calendar calendar = Calendar.getInstance();

        calendarDate = calendar.get(Calendar.DAY_OF_MONTH);
        calendarMonth = calendar.get(Calendar.MONTH);
        calendarYear = calendar.get(Calendar.YEAR);

        DatePickerDialog datePickerDialog = new DatePickerDialog(activityContext, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                outputDate = dayOfMonth + "-" + (month + 1) + "-" + year;
            }
        }, calendarYear, calendarMonth, calendarDate);

        datePickerDialog.show();
    }

    public String setDateInput(){
        return outputDate;
    }

}
