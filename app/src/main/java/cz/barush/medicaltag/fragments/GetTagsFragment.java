package cz.barush.medicaltag.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.TextViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import cz.barush.medicaltag.R;

/**
 * Created by Barbora on 26-Nov-16.
 */

public class GetTagsFragment extends Fragment
{
    View thisView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        thisView = inflater.inflate(R.layout.fragment_gettags, container, false);
        setListeners();
        return thisView;
    }

    private void setListeners()
    {
        //MEDICAL TAG BAND
        ((Button)thisView.findViewById(R.id.band_nfc_buy)).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://www.ebay.com/sch/i.html?_from=R40nfc+sticker+rectangle.TRS0&_nkw=nfc+sticker+rectangle&_sacat=0"));
                startActivity(browserIntent);
            }
        });

        ((Button)thisView.findViewById(R.id.band_buy)).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://www.ebay.co.uk/sch/i.html?_from=R40&_trksid=p2047675.m570.l1313.TR0.TRC0.H0.XInfoBand+Single+Use+Child+ID+Wristband.TRS0&_nkw=InfoBand+Single+Use+Child+ID+Wristband&_sacat=0"));
                startActivity(browserIntent);
            }
        });

        //TUTORIAL VIDEO
        ((TextView)thisView.findViewById(R.id.band_tutorial)).setOnClickListener(new View.OnClickListener()
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
