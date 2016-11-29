package cz.barush.medicaltag.fragments;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;

import cz.barush.medicaltag.R;
import cz.barush.medicaltag.StaticPool;
import cz.barush.medicaltag.TagInfoActivity;
import cz.barush.medicaltag.model.Tag;

/**
 * Created by Barbora on 26-Nov-16.
 */

public class TagMainInfoFragment extends Fragment
{
    View thisView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        thisView = inflater.inflate(R.layout.fragment_maininfo, container, false);
        setPickers(thisView);
        setListeners();
        if(StaticPool.tagToSave != null)setMainInfoFromtagToSave();
        return thisView;
    }

    private void setMainInfoFromtagToSave()
    {
        if(StaticPool.tagToSave.getGroup() != null)
        {
            int selectedIndex = 0;
            int i = 0;
            for (String group : StaticPool.groupTags.keySet())
            {
                if(group.equals(StaticPool.tagToSave.getGroup()))selectedIndex = i;
                i++;
            }
            ((Spinner)thisView.findViewById(R.id.tag_groupName)).setSelection(selectedIndex);
        }
        if(StaticPool.tagToSave.getName() != null)
        {
            ((EditText)thisView.findViewById(R.id.tag_name)).setText(StaticPool.tagToSave.getName());
        }
        if(StaticPool.tagToSave.getDateOfBirth() != null)
        {
            ((EditText)thisView.findViewById(R.id.tag_dateOfBirth)).setText(StaticPool.tagToSave.getDateOfBirth());
        }
        if(StaticPool.tagToSave.getState() != null)
        {
            ((EditText)thisView.findViewById(R.id.tag_state)).setText(StaticPool.tagToSave.getState());
        }
        switch (StaticPool.tagToSave.getGender())
        {
            case 0:
                ((RadioButton)thisView.findViewById(R.id.tag_woman)).setChecked(true);
                break;
            case 1:
                ((RadioButton) thisView.findViewById(R.id.tag_man)).setChecked(true);
                break;
        }
        ((CheckBox)thisView.findViewById(R.id.tag_pregnant)).setChecked(StaticPool.tagToSave.isPregnant());
        if(StaticPool.tagToSave.getHeight() != 0)
        {
            ((EditText)thisView.findViewById(R.id.tag_height)).setText(String.valueOf(StaticPool.tagToSave.getHeight()));
        }
        if(StaticPool.tagToSave.getWeight() != 0)
        {
            ((EditText)thisView.findViewById(R.id.tag_weight)).setText(String.valueOf(StaticPool.tagToSave.getWeight()));
        }
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
        ArrayAdapter<String> adapter3;
        if(StaticPool.groupTags.isEmpty())
        {
            adapter3 = new ArrayAdapter<String>(this.getActivity(),
                    android.R.layout.simple_spinner_item, new String[]{getString(R.string.tag_firstGroupName)});
        }
        else
        {
            adapter3 = new ArrayAdapter<String>(this.getActivity(),
                    android.R.layout.simple_spinner_item, StaticPool.groupTags.keySet().toArray(new String[StaticPool.groupTags.size()]));
        }
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter3);
        spinner2.setSelection(0);
    }

    private void setListeners()
    {
        ((Spinner)thisView.findViewById(R.id.tag_groupName)).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {

                String[] groups = StaticPool.groupTags.keySet().toArray(new String[StaticPool.groupTags.size()]);
                if(groups.length == 0)StaticPool.tagToSave.setGroup(getString(R.string.tag_firstGroupName));
                else StaticPool.tagToSave.setGroup(groups[i]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView)
            {
                return;
            }
        });

        //SET NAME
        ((EditText)thisView.findViewById(R.id.tag_name)).setOnFocusChangeListener(new View.OnFocusChangeListener()
        {
            @Override
            public void onFocusChange(View view, boolean b)
            {
                //When user leaves the EditText
                if(!b)
                {
                    StaticPool.tagToSave.setName(((EditText)thisView.findViewById(R.id.tag_name)).getText().toString());
                }
            }
        });

        //DATE PICKER INITIALIZER
        ((EditText) thisView.findViewById(R.id.tag_dateOfBirth)).setOnFocusChangeListener(new View.OnFocusChangeListener()
        {
            @Override
            public void onFocusChange(View view, boolean b)
            {
                if(b)
                {
                    StaticPool.fragmentView = thisView;
                    DialogFragment newFragment = new DatePickerFragment();
                    newFragment.show(getActivity().getSupportFragmentManager(), "datePicker");
                }
            }
        });

        //SET STATE
        ((EditText)thisView.findViewById(R.id.tag_state)).setOnFocusChangeListener(new View.OnFocusChangeListener()
        {
            @Override
            public void onFocusChange(View view, boolean b)
            {
                //When user leaves the EditText
                if(!b)
                {
                    StaticPool.tagToSave.setState(((EditText)thisView.findViewById(R.id.tag_state)).getText().toString());
                }
            }
        });

        //SET GENDER
        ((RadioButton) thisView.findViewById(R.id.tag_woman)).setOnClickListener(MyOnRadioClickListener());
        ((RadioButton) thisView.findViewById(R.id.tag_man)).setOnClickListener(MyOnRadioClickListener());

        //IF PREGNANT
        ((CheckBox)thisView.findViewById(R.id.tag_pregnant)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b)
            {
                StaticPool.tagToSave.setPregnant(b);
            }
        });

        //SET HEIGHT
        ((EditText)thisView.findViewById(R.id.tag_height)).setOnFocusChangeListener(new View.OnFocusChangeListener()
        {
            @Override
            public void onFocusChange(View view, boolean b)
            {
                //When user leaves the EditText
                if(!b)
                {
                    StaticPool.tagToSave.setHeight(Integer.parseInt(((EditText)thisView.findViewById(R.id.tag_height)).getText().toString()));
                }
            }
        });

        //SET WEIGHT
        ((EditText)thisView.findViewById(R.id.tag_weight)).setOnFocusChangeListener(new View.OnFocusChangeListener()
        {
            @Override
            public void onFocusChange(View view, boolean b)
            {
                //When user leaves the EditText
                if(!b)
                {
                    StaticPool.tagToSave.setWeight(Integer.parseInt(((EditText)thisView.findViewById(R.id.tag_weight)).getText().toString()));
                }
            }
        });
    }

    private View.OnClickListener MyOnRadioClickListener()
    {
        return new View.OnClickListener()
        {
            public void onClick(View v)
            {
                changeIfPregnantVisibility();
            }
        };
    }

    private void changeIfPregnantVisibility()
    {
        boolean isWoman = ((RadioButton) thisView.findViewById(R.id.tag_woman)).isChecked();
        if (isWoman)
        {
            StaticPool.tagToSave.setGender(0);
            thisView.findViewById(R.id.tag_pregnant).setVisibility(View.VISIBLE);
        }
        else
        {
            StaticPool.tagToSave.setGender(1);
            thisView.findViewById(R.id.tag_pregnant).setVisibility(View.GONE);
        }
    }
}
