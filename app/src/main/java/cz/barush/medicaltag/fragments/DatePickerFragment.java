package cz.barush.medicaltag.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;

import cz.barush.medicaltag.R;
import cz.barush.medicaltag.StaticPool;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener
{
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        DatePickerDialog newDatePicker = new DatePickerDialog(getActivity(), this, year, month, day);
        return newDatePicker;
    }

    public void onDateSet(DatePicker view, int year, int month, int day)
    {
        ((EditText)StaticPool.fragmentView.findViewById(R.id.tag_dateOfBirth)).setText(day + "/" + month + "/" + year);
        StaticPool.tagToSave.setDateOfBirth(day + "/" + month + "/" + year);
    }
}
