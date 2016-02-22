package app.bicintime.wolf.bicintimeactivities;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ExpandableListView;

import java.util.ArrayList;


//Class to provide different routes for the user to pick up
public class RouteSelectionActivity extends BaseActivity implements ExpandableListView.OnChildClickListener{

    private ArrayList<String> parentItems = new ArrayList<String>();
    private ArrayList<Object> childItems = new ArrayList<Object>();
    private static final String LOG_EXPLIST = "LOG_EXPLIST";

    //Setting up the data to the expandableList
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_selection);


        agregarToolbar();
        setUpDrawer();


        //Getting the id
        ExpandableListView expandableList = (ExpandableListView) findViewById(R.id.list);

        expandableList.setDividerHeight(0);
        expandableList.setGroupIndicator(null);
        expandableList.setClickable(true);

        setGroupParents();
        setChildData();

        //Setting adapter
        MyExpandableAdapter adapter = new MyExpandableAdapter(parentItems, childItems, this);

        adapter.setInflater((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE), this);
        expandableList.setAdapter(adapter);
        expandableList.setOnChildClickListener(this);
    }

    //Setting parent text
    public void setGroupParents() {
        parentItems.add("Route 1");
        parentItems.add("Route 2");
        parentItems.add("Route 3");
        parentItems.add("Route 4");
        parentItems.add("Route 5");
    }

    //Setting child text
    public void setChildData() {

        // Android
        ArrayList<String> child = new ArrayList<String>();
        child.add("Leave at");
        childItems.add(child);

        // Core Java
        child = new ArrayList<String>();
        child.add("Get bike at");
        childItems.add(child);

        // Desktop Java
        child = new ArrayList<String>();
        child.add("Get bike at");
        childItems.add(child);

        // Enterprise Java
        child = new ArrayList<String>();
        child.add("Leave at");
        childItems.add(child);

        // Enterprise Java
        child = new ArrayList<String>();
        child.add("Leave at");
        childItems.add(child);

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
        return false;
    }
}
