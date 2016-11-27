package cz.barush.medicaltag;

import android.content.Intent;
import android.content.SharedPreferences;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.nfc.NfcEvent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cz.barush.medicaltag.fragments.GetTagsFragment;
import cz.barush.medicaltag.fragments.MyTagsFragment;
import cz.barush.medicaltag.model.Tag;

public class MainActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setLogo(R.drawable.logo);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.tab_myTags)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.tab_getTags)));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.container);

        loadUsersTags();
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new MyTagsFragment());
        fragments.add(new GetTagsFragment());
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
                Intent intent = new Intent(MainActivity.this, TagInfoActivity.class);
                startActivity(intent);
            }
        });

    }

    private void loadUsersTags()
    {
        SharedPreferences prefs = getPreferences(MODE_PRIVATE);
        Gson gson = new Gson();
        Map<String, String> tags = (Map<String,String>) prefs.getAll();
        for(String key : tags.keySet())
        {
            String json = prefs.getString(key, "");
            Tag medTag = gson.fromJson(json, Tag.class);
            if(StaticPool.groupTags.get(medTag.getGroup()) == null)StaticPool.groupTags.put(medTag.getGroup(), new ArrayList<Tag>());
            StaticPool.groupTags.get(medTag.getGroup()).add(medTag);
        }
    }

    public void navigateToTagInfo()
    {
        Intent intent = new Intent(this, TagInfoActivity.class);
        startActivity(intent);
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
    protected void onNewIntent(Intent intent)
    {
//        super.onNewIntent(intent);
//        if (intent != null && NfcAdapter.ACTION_NDEF_DISCOVERED.equals(intent.getAction()))
//        {
//            Parcelable[] rawMessages = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
//            if (rawMessages != null)
//            {
//                NdefMessage[] messages = new NdefMessage[rawMessages.length];
//                Tag newTag = new Tag();
//                for (int i = 0; i < rawMessages.length; i++)
//                {
//                    messages[i] = (NdefMessage) rawMessages[i];
//                    for (int j = 0; j < messages[i].getRecords().length; j++)
//                    {
//
//                    }
//                }
//                // Process the messages array.
//
//            }
//        }
    }
}
