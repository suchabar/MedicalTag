package cz.barush.medicaltag;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.concurrent.TimeUnit;

import cz.barush.medicaltag.model.Tag;

public class NFCLaunchActivity extends AppCompatActivity
{
    PendingIntent nfcPendingIntent;
    IntentFilter[] intentFiltersArray;
    private NfcAdapter nfcAdpt;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfclaunch);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        nfcAdpt = NfcAdapter.getDefaultAdapter(this);

        // Check if the smartphone has NFC
        if (nfcAdpt == null)
        {
            Toast.makeText(this, "NFC not supported", Toast.LENGTH_LONG).show();
            finish();
        }

        // Check if NFC is enabled
        if (!nfcAdpt.isEnabled())
        {
            Toast.makeText(this, "Enable NFC before using the app", Toast.LENGTH_LONG).show();
        }

        Intent nfcIntent = new Intent(this, getClass());
        nfcIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

        nfcPendingIntent = PendingIntent.getActivity(this, 0, nfcIntent, 0);

        IntentFilter tagIntentFilter = new IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED);
        try
        {
            tagIntentFilter.addDataType("text/plain");
            intentFiltersArray = new IntentFilter[]{tagIntentFilter};
        }
        catch (Throwable t)
        {
            t.printStackTrace();
        }
    }

    @Override
    public void onNewIntent(Intent intent)
    {
        Log.d("Nfc", "New intent");
        getTag(intent);
    }

    private void handleIntent(Intent i)
    {
        Log.d("NFC", "Intent [" + i + "]");
        getTag(i);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        nfcAdpt.enableForegroundDispatch(this, nfcPendingIntent, intentFiltersArray,null);
        handleIntent(getIntent());
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        nfcAdpt.disableForegroundDispatch(this);
    }

    private void getTag(Intent i)
    {
        if (i == null)
            return;

        String type = i.getType();
        String action = i.getAction();

        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action))
        {
            Log.d("Nfc", "Action NDEF Found");
            Parcelable[] parcs = i.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);

            for (Parcelable p : parcs)
            {
                NdefMessage msg = (NdefMessage) p;
                NdefRecord[] records = msg.getRecords();
                for (NdefRecord record : records)
                {
                    String content =  new String(record.getPayload());
                    content = content.substring(3);
                    Gson gson = new Gson();
                    //Tag scannedMedicalTag = gson.fromJson(content, Tag.class);
                    Tag scannedMedicalTag = StaticPool.createTagFromString(content);
                    if(scannedMedicalTag != null)
                    {
                        StaticPool.tagToSave = new Tag(scannedMedicalTag);
                        Intent intent = new Intent(NFCLaunchActivity.this, TagInfoActivity.class);
                        startActivity(intent);
                    }
                    else return;
                    //Log.d("RecordContent ", content);
                }
            }
        }
    }
}