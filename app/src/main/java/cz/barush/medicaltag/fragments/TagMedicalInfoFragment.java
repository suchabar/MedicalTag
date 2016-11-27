package cz.barush.medicaltag.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cz.barush.medicaltag.R;

/**
 * Created by Barbora on 26-Nov-16.
 */

public class TagMedicalInfoFragment extends Fragment
{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_medicalinfo, container, false);
    }
}
