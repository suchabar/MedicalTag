package cz.barush.medicaltag.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cz.barush.medicaltag.MainActivity;
import cz.barush.medicaltag.R;
import cz.barush.medicaltag.StaticPool;
import cz.barush.medicaltag.TagInfoActivity;
import cz.barush.medicaltag.model.Tag;

/**
 * Created by Barbora on 26-Nov-16.
 */

public class MyTagsFragment extends Fragment
{
    ExpandableListView lv;
    private List<String> groups;
    private HashMap<String, List<Tag>> children;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_mytags, container, false);
    }

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //RAW DATA
//        groups = new ArrayList<String>();
//        groups.add("Rodina");
//        groups.add("Lyžák kvinta");
//        children = new HashMap<String, List<Tag>>();
//        List<Tag> familyList = new ArrayList<>();
//        familyList.add(new Tag("Barus Suchanova"));
//        familyList.add(new Tag("Klara Suchanova"));
//        familyList.add(new Tag("Martin Suchan"));
//        children.put("Rodina", familyList);
//
//        List<Tag> schoolList = new ArrayList<>();
//        schoolList.add(new Tag("Pavlach"));
//        schoolList.add(new Tag("Novotass"));
//        children.put("Lyžák kvinta", schoolList);

        //REAL DATA
        groups = new ArrayList<String>(StaticPool.groupTags.keySet());
        children = StaticPool.groupTags;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        lv = (ExpandableListView) view.findViewById(R.id.list_mytags);
        lv.setAdapter(new CustomExpandableListAdapter(getActivity().getApplicationContext(), groups, children));
        Display newDisplay = getActivity().getWindowManager().getDefaultDisplay();
        int width = newDisplay.getWidth();
        lv.setIndicatorBounds(width - 136, width);
        lv.setOnChildClickListener(new ExpandableListView.OnChildClickListener()
        {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id)
            {
                Intent intent = new Intent(getActivity().getApplicationContext(), TagInfoActivity.class);
                StaticPool.tagToSave = StaticPool.groupTags.get(groups.get(groupPosition)).get(childPosition);
                startActivity(intent);
                return true;
            }
        });
    }
}

class CustomExpandableListAdapter extends BaseExpandableListAdapter
{
    private Context context;
    private List<String> expandableListTitle;
    private HashMap<String, List<Tag>> expandableListDetail;

    public CustomExpandableListAdapter(Context context, List<String> expandableListTitle,
                                       HashMap<String, List<Tag>> expandableListDetail)
    {
        this.context = context;
        this.expandableListTitle = expandableListTitle;
        this.expandableListDetail = expandableListDetail;
    }

    @Override
    public Object getChild(int listPosition, int expandedListPosition)
    {
        return this.expandableListDetail.get(this.expandableListTitle.get(listPosition)).get(expandedListPosition).getName();
    }

    @Override
    public long getChildId(int listPosition, int expandedListPosition)
    {
        return expandedListPosition;
    }

    @Override
    public View getChildView(int listPosition, final int expandedListPosition, boolean isLastChild, View convertView, ViewGroup parent)
    {
        final String expandedListText = (String) getChild(listPosition, expandedListPosition);
        if (convertView == null)
        {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_item, null);
        }
        TextView expandedListTextView = (TextView) convertView.findViewById(R.id.item_header);
        expandedListTextView.setText(expandedListText);
        return convertView;
    }

    @Override
    public int getChildrenCount(int listPosition)
    {
        return this.expandableListDetail.get(this.expandableListTitle.get(listPosition)).size();
    }

    @Override
    public Object getGroup(int listPosition)
    {
        return this.expandableListTitle.get(listPosition);
    }

    @Override
    public int getGroupCount()
    {
        return this.expandableListTitle.size();
    }

    @Override
    public long getGroupId(int listPosition)
    {
        return listPosition;
    }

    @Override
    public View getGroupView(int listPosition, boolean isExpanded, View convertView, ViewGroup parent)
    {
        String listTitle = (String) getGroup(listPosition);
        if (convertView == null)
        {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_group, null);
        }
        TextView listTitleTextView = (TextView) convertView.findViewById(R.id.group_header);
        listTitleTextView.setTypeface(null, Typeface.BOLD);
        listTitleTextView.setText(listTitle);
        return convertView;
    }

    @Override
    public boolean hasStableIds()
    {
        return false;
    }

    @Override
    public boolean isChildSelectable(int listPosition, int expandedListPosition)
    {
        return true;
    }
}
