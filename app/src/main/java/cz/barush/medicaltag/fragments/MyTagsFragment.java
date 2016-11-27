package cz.barush.medicaltag.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import cz.barush.medicaltag.R;
import cz.barush.medicaltag.StaticPool;
import cz.barush.medicaltag.TagInfoActivity;

/**
 * Created by Barbora on 26-Nov-16.
 */

public class MyTagsFragment extends Fragment
{
    private LayoutInflater inf;
    ExpandableListView lv;
    private String[] groups;
    private String[][] children;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        this.inf = inflater;
        return inflater.inflate(R.layout.fragment_mytags, container, false);
    }

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        groups = new String[]{"Test Family", "Test Lyzak"};
        children = new String[2][20];
        children[0][0] = "Barus Suchanova";
        children[0][1] = "Klara Suchanova";
        children[0][2] = "Martin Suchan";

        children[1][0] = "Pavlach";
        children[1][1] = "Novotass";

//        groups = (String[]) StaticPool.groupTags.keySet().toArray();
//        children = new String[StaticPool.groupTags.size()][20];
//        for (int i = 0; i < StaticPool.groupTags.size(); i++)
//        {
//            for (int j = 0; j < StaticPool.groupTags.get(i).size(); i++)
//            {
//                children[i][j] = StaticPool.groupTags.get(i).get(j).getName();
//            }
//        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        lv = (ExpandableListView) view.findViewById(R.id.list_mytags);
        lv.setAdapter(new ExpandableListAdapter(inf, groups, children));
        lv.setGroupIndicator(null);
    }
}

class ExpandableListAdapter extends BaseExpandableListAdapter
{
    private LayoutInflater inf;
    private String[] groups;
    private String[][] children;

    public ExpandableListAdapter(LayoutInflater inf, String[] groups, String[][] children)
    {
        this.groups = groups;
        this.children = children;
        this.inf = inf;
    }

    @Override
    public int getGroupCount()
    {
        return groups.length;
    }

    @Override
    public int getChildrenCount(int groupPosition)
    {
        return children[groupPosition].length;
    }

    @Override
    public Object getGroup(int groupPosition)
    {
        return groups[groupPosition];
    }

    @Override
    public Object getChild(int groupPosition, int childPosition)
    {
        return children[groupPosition][childPosition];
    }

    @Override
    public long getGroupId(int groupPosition)
    {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition)
    {
        return childPosition;
    }

    @Override
    public boolean hasStableIds()
    {
        return true;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent)
    {

        ViewHolder holder;
        if (convertView == null)
        {
            convertView = inf.inflate(R.layout.list_item, parent, false);
            holder = new ViewHolder();

            holder.text = (TextView) convertView.findViewById(R.id.item_header);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.text.setText(getChild(groupPosition, childPosition).toString());
        return convertView;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent)
    {
        ViewHolder holder;

        if (convertView == null)
        {
            convertView = inf.inflate(R.layout.list_group, parent, false);

            holder = new ViewHolder();
            holder.text = (TextView) convertView.findViewById(R.id.group_header);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.text.setText(getGroup(groupPosition).toString());

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition)
    {
        return true;
    }

    private class ViewHolder
    {
        TextView text;
    }
}
