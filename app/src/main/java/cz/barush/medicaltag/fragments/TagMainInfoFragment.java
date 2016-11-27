package cz.barush.medicaltag.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.RadioButton;
import android.widget.Spinner;

import cz.barush.medicaltag.R;

/**
 * Created by Barbora on 26-Nov-16.
 */

public class TagMainInfoFragment extends Fragment
{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        View v = inflater.inflate(R.layout.fragment_maininfo, container, false);
        setPickers(v);
        return v;
    }
    private void setPickers(View v)
    {
        //SETTING THE HINTS FOR COUNTRIES
        AutoCompleteTextView textView = (AutoCompleteTextView) v.findViewById(R.id.tag_state);
        String[] countries = getResources().getStringArray(R.array.countries_array);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_list_item_1, countries);
        textView.setAdapter(adapter);

        //SETTING THE SPINNER FOR GROUP NAMES
        Spinner spinner2 = (Spinner) v.findViewById(R.id.tag_groupName);
        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(this.getActivity(),
                R.array.groupNames, android.R.layout.simple_spinner_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter3);
        spinner2.setSelection(0);
    }

    private void changeIfPregnantVisibility(View view)
    {
        boolean isWoman = ((RadioButton) view.findViewById(R.id.tag_woman)).isChecked();
        if(isWoman)view.findViewById(R.id.tag_pregnant).setVisibility(View.VISIBLE);
        else view.findViewById(R.id.tag_pregnant).setVisibility(View.INVISIBLE);
    }

}
