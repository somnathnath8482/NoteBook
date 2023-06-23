package com.easy.myapplication.Helper;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.util.Log;
import android.widget.TimePicker;

import com.easy.myapplication.Interface.AllInterface;
import com.easy.myapplication.Interface.OnDateSelect;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MethodClass {
    public static void GetSelectdDate(OnDateSelect onselect, Context context, Long max, Long min) {

        final Calendar c = Calendar.getInstance();
        // c.add(Calendar.YEAR, -18);
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(context, (datePicker, year, monthOfYear, dayOfMonth) -> {
            String date = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
            onselect.OnSelect(getDate("yyyy-M-d", "dd-MM-yyyy", date));
        }, mYear, mMonth, mDay);
        datePickerDialog.getDatePicker().setMaxDate(max);
        datePickerDialog.getDatePicker().setMinDate(min);
        datePickerDialog.show();


    }

    public static String getDate(String inputPattern, String outputPattern, String data) {
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
            Date date = inputFormat.parse(data);
            SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);
            return outputFormat.format(date);

        } catch (Exception e) {
            e.printStackTrace();
            Log.e("ParseException", e.toString());
        }
        return data;
    }

    public static void getTime(Activity activity, Calendar min, AllInterface onDateSelect) {
        TimePickerDialog timePickerDialogn
                = new TimePickerDialog(activity, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                onDateSelect.OnSuccess(i,i1);

            }
        }, min.HOUR_OF_DAY, min.MINUTE, false);
        timePickerDialogn.show();
    }
}
