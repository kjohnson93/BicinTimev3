package app.bicintime.wolf.bicintimeactivities;

/**
 * Created by wolf on 2/20/2016.
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckedTextView;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MyExpandableAdapter extends BaseExpandableListAdapter implements ExpandableListView.OnChildClickListener {

    Context context;
    private Activity activity;
    private ArrayList<Object> childtems;
    private LayoutInflater inflater;
    private ArrayList<String> parentItems, child;
    private static final String LOG_EXPLIST = "LOG_EXPLIST";

    public MyExpandableAdapter(ArrayList<String> parents, ArrayList<Object> childern, Context context) {
        this.parentItems = parents;
        this.childtems = childern;
        this.context = context;
    }

    public void setInflater(LayoutInflater inflater, Activity activity) {
        this.inflater = inflater;
        this.activity = activity;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        child = (ArrayList<String>) childtems.get(groupPosition);

        TextView textView = null;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.group, null);
        }

        //When we click a differentes route it open different map
        convertView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Log.d(LOG_EXPLIST, "Group clicked from convertView: " + groupPosition + "\n" + "And its child position: " + childPosition);

                switch (groupPosition){

                    case 0:
                        Intent intent = new Intent(context, RouteMapActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                        context.startActivity(intent);
                        break;
                    case 1:
                        Intent intent2 = new Intent(context, RouteMapActivity.class);
                        intent2.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                        context.startActivity(intent2);
                        break;
                    case 2:
                        Intent intent3 = new Intent(context, RouteMapActivity.class);
                        intent3.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                        context.startActivity(intent3);
                        break;
                    case 3:
                        Intent intent4 = new Intent(context, RouteMapActivity.class);
                        intent4.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                        context.startActivity(intent4);
                        break;
                    case 4:


                }
            }
        });


        return convertView;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.row, null);
        }

        ((CheckedTextView) convertView).setText(parentItems.get(groupPosition));
        ((CheckedTextView) convertView).setChecked(isExpanded);

        return convertView;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return ((ArrayList<String>) childtems.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return null;
    }

    @Override
    public int getGroupCount() {
        return parentItems.size();
    }

    @Override
    public void onGroupCollapsed(int groupPosition) {
        super.onGroupCollapsed(groupPosition);
    }

    @Override
    public void onGroupExpanded(int groupPosition) {
        super.onGroupExpanded(groupPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    @Override
    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

        Log.d(LOG_EXPLIST, "cHIld clicked: " + childPosition);

        return false;
    }
}