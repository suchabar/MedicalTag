package cz.barush.medicaltag.fragments;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import cz.barush.medicaltag.R;
import cz.barush.medicaltag.StaticPool;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Barbora on 26-Nov-16.
 */

public class TagContactsFragment extends Fragment
{
    View thisView;
    String selectedContact;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE}, 1234);
        }
        View v = inflater.inflate(R.layout.fragment_contacts, container, false);
        thisView = v;
        setListeners();
        if(StaticPool.tagToSave != null)setContactsFromtagToSave();
        return v;
    }

    private void setContactsFromtagToSave()
    {
        if(StaticPool.tagToSave.getEmergencyContact1() != null)
        {
            ((EditText)thisView.findViewById(R.id.tag_firstContact)).setText(StaticPool.tagToSave.getEmergencyContact1());
            ((TextView) thisView.findViewById(R.id.tag_firstContactName)).setText(StaticPool.tagToSave.getEmergencyContactTitle1());
            ((ImageButton) thisView.findViewById(R.id.tag_call1)).setVisibility(View.VISIBLE);
        }
        if(StaticPool.tagToSave.getEmergencyContact2() != null)
        {
            ((EditText)thisView.findViewById(R.id.tag_secondContact)).setText(StaticPool.tagToSave.getEmergencyContact2());
            ((TextView) thisView.findViewById(R.id.tag_secondContactName)).setText(StaticPool.tagToSave.getEmergencyContactTitle2());
            ((ImageButton) thisView.findViewById(R.id.tag_call2)).setVisibility(View.VISIBLE);
        }
//        if(StaticPool.tagToSave.getEmergencyContact3() != null)
//        {
//            ((EditText)thisView.findViewById(R.id.tag_thirdContact)).setText(StaticPool.tagToSave.getEmergencyContact3());
//            ((TextView) thisView.findViewById(R.id.tag_thirdContactName)).setText(StaticPool.tagToSave.getEmergencyContactTitle3());
//            ((ImageButton) thisView.findViewById(R.id.tag_call3)).setVisibility(View.VISIBLE);
//        }
    }

    private void setListeners()
    {
        ((EditText) thisView.findViewById(R.id.tag_firstContact)).setOnFocusChangeListener(MyOnClickContacts());
        ((EditText) thisView.findViewById(R.id.tag_secondContact)).setOnFocusChangeListener(MyOnClickContacts());
        //((EditText) thisView.findViewById(R.id.tag_thirdContact)).setOnFocusChangeListener(MyOnClickContacts());

        ((ImageButton) thisView.findViewById(R.id.tag_call1)).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + StaticPool.tagToSave.getEmergencyContact1()));
                startActivity(intent);
            }
        });

        ((ImageButton) thisView.findViewById(R.id.tag_call2)).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + StaticPool.tagToSave.getEmergencyContact2()));
                startActivity(intent);
            }
        });

//        ((ImageButton) thisView.findViewById(R.id.tag_call3)).setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View view)
//            {
//                Intent intent = new Intent(Intent.ACTION_CALL);
//                intent.setData(Uri.parse("tel:" + StaticPool.tagToSave.getEmergencyContact3()));
//                startActivity(intent);
//            }
//        });
    }

    private View.OnFocusChangeListener MyOnClickContacts()
    {
        return new View.OnFocusChangeListener()
        {
            @Override
            public void onFocusChange(View view, boolean b)
            {
                if (b)
                {
                    selectedContact = view.getResources().getResourceName(view.getId());
                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
                    startActivityForResult(intent, 1);
                }
            }
        };
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1)
        {
            if (resultCode == RESULT_OK)
            {
                Uri contactData = data.getData();
                Cursor cursor = getActivity().getContentResolver().query(contactData, null, null, null, null);
                cursor.moveToFirst();

                String number = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));

                if (selectedContact.equals("cz.barush.medicaltag:id/tag_firstContact"))
                {
                    StaticPool.tagToSave.setEmergencyContact1(number);
                    StaticPool.tagToSave.setEmergencyContactTitle1(name);
                    ((EditText) thisView.findViewById(R.id.tag_firstContact)).setText(number);
                    ((TextView) thisView.findViewById(R.id.tag_firstContactName)).setText(name);
                    ((ImageButton) thisView.findViewById(R.id.tag_call1)).setVisibility(View.VISIBLE);
                }
                else if (selectedContact.equals("cz.barush.medicaltag:id/tag_secondContact"))
                {
                    StaticPool.tagToSave.setEmergencyContact2(number);
                    StaticPool.tagToSave.setEmergencyContactTitle2(name);
                    ((EditText) thisView.findViewById(R.id.tag_secondContact)).setText(number);
                    ((TextView) thisView.findViewById(R.id.tag_secondContactName)).setText(name);
                    ((ImageButton) thisView.findViewById(R.id.tag_call2)).setVisibility(View.VISIBLE);
                }
//                else if (selectedContact.equals("cz.barush.medicaltag:id/tag_thirdContact"))
//                {
//                    StaticPool.tagToSave.setEmergencyContact3(number);
//                    StaticPool.tagToSave.setEmergencyContactTitle3(name);
//                    ((EditText) thisView.findViewById(R.id.tag_thirdContact)).setText(number);
//                    ((TextView) thisView.findViewById(R.id.tag_thirdContactName)).setText(name);
//                    ((ImageButton) thisView.findViewById(R.id.tag_call3)).setVisibility(View.VISIBLE);
//                }
            }
        }
    }

}
