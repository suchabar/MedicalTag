package cz.barush.medicaltag.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import cz.barush.medicaltag.R;

/**
 * Created by Barbora on 26-Nov-16.
 */

public class GetStickerFragment extends Fragment
{
    View thisView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        thisView = inflater.inflate(R.layout.fragment_getsticker, container, false);
        setListeners();
        return thisView;
    }

    private void setListeners()
    {
        //MEDICAL TAG STICKER
        ((Button)thisView.findViewById(R.id.nfc_sticker_buy)).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://www.ebay.com/sch/i.html?_from=R40&_sacat=0&_nkw=nfc+sticker+round&_sop=15"));
                startActivity(browserIntent);
            }
        });

        ((Button)thisView.findViewById(R.id.sticker_buy)).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://drive.google.com/file/d/0B6n45dJaqMVbU2YxcmQ4X2s1Zjg/view"));
                startActivity(browserIntent);
            }
        });

        //TUTORIAL VIDEO
        ((TextView)thisView.findViewById(R.id.sticker_tutorial)).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://www.youtube.com"));
                startActivity(browserIntent);
            }
        });
    }
}
