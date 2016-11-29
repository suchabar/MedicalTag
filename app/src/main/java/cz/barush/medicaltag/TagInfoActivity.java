package cz.barush.medicaltag;

import android.Manifest;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cz.barush.medicaltag.fragments.TagContactsFragment;
import cz.barush.medicaltag.fragments.TagMainInfoFragment;
import cz.barush.medicaltag.fragments.TagMedicalInfoFragment;
import cz.barush.medicaltag.model.Tag;

public class TagInfoActivity extends AppCompatActivity
{
    final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1234;
    private android.nfc.Tag currentTag;
    private NdefMessage message;
    private NFCManager nfcMger;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        checkPermissions();
        setContentView(R.layout.activity_taginfo);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setLogo(R.drawable.logo);

        //NFC Manager starting
        nfcMger = new NFCManager(this);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.tag_tab_maininfo)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.tag_tab_medical)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.tag_tab_contacts)));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.container);
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new TagMainInfoFragment());
        fragments.add(new TagMedicalInfoFragment());
        fragments.add(new TagContactsFragment());

        final PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener()
        {
            @Override
            public void onTabSelected(TabLayout.Tab tab)
            {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab)
            {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab)
            {

            }
        });

        findViewById(R.id.fab).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                uploadDataViaNFC();
            }
        });
    }

    private void checkPermissions()
    {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_CONTACTS}, MY_PERMISSIONS_REQUEST_READ_CONTACTS);
        }
    }

    public void uploadDataViaNFC()
    {
        //SAVING NEW TAG
        saveNewTag();
        message = nfcMger.createTextMessage(StaticPool.tagToSave.toString());
        if (message != null)
        {
            dialog = new ProgressDialog(TagInfoActivity.this);
            dialog.setMessage(getString(R.string.tagPlease));
            dialog.show();
        }
    }

    private void saveNewTag()
    {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor prefsEditor = pref.edit();
        Gson gson = new Gson();
        String compressedTag;
//            compressedTag = gson.toJson(StaticPool.tagToSave);
        compressedTag = StaticPool.compressTagToString(StaticPool.tagToSave);
        if(StaticPool.groupTags.containsKey(StaticPool.tagToSave.getName()))
        {
            prefsEditor.remove(StaticPool.tagToSave.getName());
        }
        prefsEditor.putString(StaticPool.tagToSave.getName(), compressedTag);
        prefsEditor.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        try
        {
            nfcMger.verifyNFC();
            Intent nfcIntent = new Intent(this, getClass());
            nfcIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, nfcIntent, 0);
            IntentFilter[] intentFiltersArray = new IntentFilter[]{};
            String[][] techList = new String[][]{{android.nfc.tech.Ndef.class.getName()}, {android.nfc.tech.NdefFormatable.class.getName()}};
            NfcAdapter nfcAdpt = NfcAdapter.getDefaultAdapter(this);
            nfcAdpt.enableForegroundDispatch(this, pendingIntent, intentFiltersArray, techList);
        }
        catch (NFCManager.NFCNotSupported nfcnsup)
        {
            Snackbar.make(findViewById(R.id.container), getString(R.string.tag_notSupported), Snackbar.LENGTH_LONG).show();
        }
        catch (NFCManager.NFCNotEnabled nfcnEn)
        {
            Snackbar.make(findViewById(R.id.container), getString(R.string.tag_notEnabled), Snackbar.LENGTH_LONG).show();
        }
    }


    @Override
    protected void onPause()
    {
        super.onPause();
        if(nfcMger != null)nfcMger.disableDispatch();
    }

    @Override
    public void onNewIntent(Intent intent)
    {
        Log.d("Nfc", "New intent");
        // It is the time to write the tag
        currentTag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        if (message != null)
        {
            nfcMger.writeTag(currentTag, message);
            dialog.dismiss();
            Snackbar.make(findViewById(R.id.container), getString(R.string.tag_successfullyWritten), Snackbar.LENGTH_LONG).show();
            Intent in = new Intent(TagInfoActivity.this, MainActivity.class);
            startActivity(in);

        } else
        {
            Snackbar.make(findViewById(R.id.container), "TagInfoActivity > onNewIntent > message IS null", Snackbar.LENGTH_LONG).show();
        }
    }
}
