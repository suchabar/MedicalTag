package cz.barush.medicaltag.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;

import cz.barush.medicaltag.R;
import cz.barush.medicaltag.StaticPool;

/**
 * Created by Barbora on 26-Nov-16.
 */

public class TagMedicalInfoFragment extends Fragment
{
    View thisView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        thisView = inflater.inflate(R.layout.fragment_medicalinfo, container, false);
        setListeners();
        if(StaticPool.tagToSave != null)setMedicalInfoFromtagToSave();
        return thisView;
    }

    private void setMedicalInfoFromtagToSave()
    {
        if(StaticPool.tagToSave.getBloodType() != null)
        {
            int selectedIndex = 0;
            for (int i = 0; i < 8; i++)
            {
                if(getResources().getStringArray(R.array.bloodTypes_array)[i].equals(StaticPool.tagToSave.getBloodType()))selectedIndex = i;
            }
            ((Spinner)thisView.findViewById(R.id.tag_bloodGroup)).setSelection(selectedIndex);
        }
        if(StaticPool.tagToSave.getAllergies() != null)
        {
            ((EditText)thisView.findViewById(R.id.tag_allergies)).setText(StaticPool.tagToSave.getAllergies());
        }
        if(StaticPool.tagToSave.getMedicamentsInUse() != null)
        {
            ((EditText)thisView.findViewById(R.id.tag_medicaments)).setText(StaticPool.tagToSave.getMedicamentsInUse());
        }
        ((CheckBox)thisView.findViewById(R.id.tag_diabetes)).setChecked(StaticPool.tagToSave.isHaveDiabetes());
    }

    private void setListeners()
    {
        ((Spinner)thisView.findViewById(R.id.tag_bloodGroup)).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                StaticPool.tagToSave.setBloodType(getResources().getStringArray(R.array.bloodTypes_array)[i]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView)
            {
                return;
            }
        });
        ((CheckBox)thisView.findViewById(R.id.tag_diabetes)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b)
            {
                StaticPool.tagToSave.setHaveDiabetes(b);
            }
        });
        //SET ALLERGIES
        ((EditText)thisView.findViewById(R.id.tag_allergies)).setOnFocusChangeListener(new View.OnFocusChangeListener()
        {
            @Override
            public void onFocusChange(View view, boolean b)
            {
                //When user leaves the EditText
                if(!b)
                {
                    StaticPool.tagToSave.setAllergies(((EditText)thisView.findViewById(R.id.tag_allergies)).getText().toString());
                }
            }
        });
        //SET MEDICAMENTS IN USE
        ((EditText)thisView.findViewById(R.id.tag_medicaments)).setOnFocusChangeListener(new View.OnFocusChangeListener()
        {
            @Override
            public void onFocusChange(View view, boolean b)
            {
                //When user leaves the EditText
                if(!b)
                {
                    StaticPool.tagToSave.setAllergies(((EditText)thisView.findViewById(R.id.tag_medicaments)).getText().toString());
                }
            }
        });
    }
}
