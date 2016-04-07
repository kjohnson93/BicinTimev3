package app.bicintime.wolf.bicintimeactivities;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MyExpandableAdapter extends BaseExpandableListAdapter {

    private Activity context;
    private Map<String, List<String>> childItems;
    private List<String> parentItems;
    RouteInformation routeInformation;

    public MyExpandableAdapter(Activity context, List<String> parentItems,
                               Map<String, List<String>> childItems, RouteInformation routeTest) {
        this.context = context;
        this.childItems = childItems;
        this.parentItems = parentItems;
        this.routeInformation = routeTest;
    }

    public Object getChild(int groupPosition, int childPosition) {
        return childItems.get(parentItems.get(groupPosition)).get(childPosition);
    }

    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }


    public View getChildView(final int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        final String laptop = (String) getChild(groupPosition, childPosition);
        LayoutInflater inflater = context.getLayoutInflater();

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.child_item_route, null);
        }

//        TextView item = (TextView) convertView.findViewById(R.id.laptop);
//
//        item.setText(laptop);

        ArrayList<String> streetUpArrayList = routeInformation.getStreetUpArrayList();
        ArrayList<String> streetDownArrayList = routeInformation.getStreetDownArrayList();


        TextView txtvwUp = (TextView) convertView.findViewById(R.id.txtvw_up);
        TextView txtvwDown = (TextView) convertView.findViewById(R.id.txtvw_down);

        switch (groupPosition) {


            case 0:

                txtvwUp.setText(streetUpArrayList.get(groupPosition));
                txtvwDown.setText(streetDownArrayList.get(groupPosition));
                break;
            case 1:
                txtvwUp.setText(streetUpArrayList.get(groupPosition));
                txtvwDown.setText(streetDownArrayList.get(groupPosition));
                break;
            case 2:
                txtvwUp.setText(streetUpArrayList.get(groupPosition));
                txtvwDown.setText(streetDownArrayList.get(groupPosition));
                break;
            case 3:
                txtvwUp.setText(streetUpArrayList.get(groupPosition));
                txtvwDown.setText(streetDownArrayList.get(groupPosition));
                break;
            case 4:
                txtvwUp.setText(streetUpArrayList.get(groupPosition));
                txtvwDown.setText(streetDownArrayList.get(groupPosition));
                break;


        }
        return convertView;
    }

    public int getChildrenCount(int groupPosition) {
        return childItems.get(parentItems.get(groupPosition)).size();
    }

    public Object getGroup(int groupPosition) {
        return parentItems.get(groupPosition);
    }

    public int getGroupCount() {
        return parentItems.size();
    }

    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String laptopName = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.group_item,
                    null);
        }

        //Ahora si tengo datos, el problema era al no rellenarse los datos del JSON file...
        ArrayList<Integer> distanceRoute = routeInformation.getDistanceArrayList();


        //here we set the grouper title
        TextView item = (TextView) convertView.findViewById(R.id.txtvw_parent);
        item.setTypeface(null, Typeface.BOLD);
        item.setText(laptopName);
        return convertView;
    }

    public boolean hasStableIds() {
        return true;
    }

    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}