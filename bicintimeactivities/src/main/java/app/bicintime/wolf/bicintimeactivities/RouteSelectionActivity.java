package app.bicintime.wolf.bicintimeactivities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


//Class to provide different routes for the user to pick up
public class RouteSelectionActivity extends BaseActivity implements ExpandableListView.OnChildClickListener {

    private ArrayList<String> parentItems = new ArrayList<String>();
    private ArrayList<String> childItems = new ArrayList<String>();
    private static final String LOG_EXPLIST = "LOG_EXPLIST";
    private static final String LOG_PROGRESS = "LOGPROGRESS";
    private static final String LOG_DLOAD = "LOGDOWNLOAD";
    private static final String DEFAULT = "Default text";

    private static final String LOG_TIME = "LOGTIME";
    private String startLocation, endLocation, timeSelected;

    Map<String, List<String>> childrenItems;
    Boolean isResumed = false;


    ProgressDialog progressDialog;


    //this variable is useful to store the steps generated by Google Map Directions API.
    ArrayList<Waypoints> waypointsArrayList = new ArrayList<>();

    private ArrayList<String> sumTimeArrayList = new ArrayList<>();
    private ArrayList<Integer> distanceArrayList = new ArrayList<>();
    private ArrayList<String> streetUpArrayList = new ArrayList<>();
    private ArrayList<String> streetDownArrayList = new ArrayList<>();
    private ArrayList<String> streetNumberUpArrayList = new ArrayList<>();
    private ArrayList<String> streetNumberDownArrayList = new ArrayList<>();
    private ArrayList<String> stationIdUpArrayList = new ArrayList<>();
    private ArrayList<String> stationIdDownArrayList = new ArrayList<>();
    private ArrayList<Integer> stationBikesUpNowArrayList = new ArrayList<>();
    private ArrayList<Integer> stationBikesDownNowArrayList = new ArrayList<>();
    private ArrayList<Integer> stationBikesUpEstArrayList = new ArrayList<>();
    private ArrayList<Integer> stationBikesDownEstArrayList = new ArrayList<>();
    private ArrayList<Integer> stationSlotsUpNowArrayList = new ArrayList<>();
    private ArrayList<Integer> stationSlotsDownNowArrayList = new ArrayList<>();
    private ArrayList<Integer> stationSlotsUpEstArrayList = new ArrayList<>();
    private ArrayList<Integer> stationSlotsDownEstArrayList = new ArrayList<>();
    private ArrayList<Double> stationRiskArrayList = new ArrayList<>();

    ExpandableListView expandableList;

    MyExpandableAdapter expListAdapter;

    //Setting up the data to the expandableList
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_selection);


        agregarToolbar();
        setUpDrawer();
        setTitle("Choose a Route");



        getSharedPreferencesData();

        //todo acordarse de mover esto de OnResume, aquí
//        ProcessWaypoints processWaypoints = new ProcessWaypoints(startLocation, endLocation, timeSelected);
//        processWaypoints.execute();


        //Getting the id
        expandableList = (ExpandableListView) findViewById(R.id.laptop_list);
//
//                expListAdapter = new MyExpandableAdapter(
//                        RouteSelectionActivity.this, parentItems, childrenItems, routeTest);
//
//
//
//                expandableList.setAdapter(expListAdapter);


        expandableList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                final String selected = (String) expListAdapter.getChild(
                        groupPosition, childPosition);

                //not necessarly though because I'm not clearing the file until it's done?
                SharedPreferences sharedPreferences = getSharedPreferences("MyData", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                Intent intent = new Intent(RouteSelectionActivity.this, RouteMapActivity.class);

                switch (groupPosition) {

                    case 0:

                        //set bundle data
                        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                        editor.putString("waypointUp", waypointsArrayList.get(0).toString());
                        editor.putString("waypointDown", waypointsArrayList.get(1).toString());

                        startActivity(intent);
                        break;
                    case 1:
                        //set bundle data
                        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                        editor.putString("waypointUp", waypointsArrayList.get(2).toString());
                        editor.putString("waypointDown", waypointsArrayList.get(3).toString());
                        startActivity(intent);
                        break;
                    case 2:

                        //set bundle data
                        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                        editor.putString("waypointUp", waypointsArrayList.get(4).toString());
                        editor.putString("waypointDown", waypointsArrayList.get(5).toString());
                        startActivity(intent);
                        break;
                    case 3:
                        //set bundle data
                        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                        editor.putString("waypointUp", waypointsArrayList.get(6).toString());
                        editor.putString("waypointDown", waypointsArrayList.get(7).toString());
                        startActivity(intent);
                        break;
                    case 4:

                        //set bundle data
                        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                        editor.putString("waypointUp", waypointsArrayList.get(8).toString());
                        editor.putString("waypointDown", waypointsArrayList.get(9).toString());
                        startActivity(intent);
                        break;
                    default:
                        return false;

                }

                editor.commit();

                return true;
            }
        });

    }

    //Sets the parent title texts ( km, time and risk)
    public void setGroupParents() {

        //auxiliar arraylists
        ArrayList<String> titles = new ArrayList<>();
        ArrayList<Double> distValues = new ArrayList<>();
        ArrayList<Double> riskValues = new ArrayList<>();

            for (int i = 0; i < distanceArrayList.size(); i++) {
                distValues.add(Double.valueOf(distanceArrayList.get(i)/1000.00));

                //to round down up to two decimals
                riskValues.add(Math.round(((1.0 - (Double.valueOf((stationRiskArrayList.get(i)))))*100.0)*100.0)/100.0);

                titles.add("" + distValues.get(i) + " km" + " (" + sumTimeArrayList.get(i) + ") " + riskValues.get(i) + " % Success");
                parentItems.add(titles.get(i));
            }

//
//        parentItems.add("Route 1 7.9 km (98 min)");
//        parentItems.add("Route 2 8.1 km (100 min)");
//        parentItems.add("Route 3 8.1 km (101 min)");
//        parentItems.add("Route 4 8.1 km (101 min)");
//        parentItems.add("Route 5 8.2 km (102 min)");
    }

    //Setting child text
    public void setChildData() {

        //nueva forma de crear los hijos, con una colección y poniendodolos a todos en el mismo array
        String[] route1 = {"route 1 ...."};
        String[] route2 = {"route2"};
        String[] route3 = {"route3"};
        String[] route4 = {"route4"};
        String[] route5 = {"route5"};

        childrenItems = new LinkedHashMap<String, List<String>>();

        //todo cheeese
        for (String route : parentItems) {
            if (route.equals("Route 1")) {
                loadChild(route1);
            } else if (route.equals("Route 2"))
                loadChild(route2);
            else if (route.equals("Route 3"))
                loadChild(route3);
            else if (route.equals("Route 4"))
                loadChild(route4);
            else if (route.equals("Route 5"))
                loadChild(route4);
            else
                loadChild(route5);
//            if (route.equals("Route 1 7.9 km (98 min)")) {
//                loadChild(route1);
//            } else if (route.equals("Route 2 8.1 km (100 min)"))
//                loadChild(route2);
//            else if (route.equals("Route 3 8.1 km (101 min)"))
//                loadChild(route3);
//            else if (route.equals("Route 4 8.1 km (101 min)"))
//                loadChild(route4);
//            else if (route.equals("Route 5 8.2 km (102 min)"))
//                loadChild(route4);
//            else
//                loadChild(route5);


            childrenItems.put(route, childItems);
        }


    }

    private void loadChild(String[] routes) {
        childItems = new ArrayList<String>();
        for (String route : routes)
            childItems.add(route);
    }


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, PlanSelectTimeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
    }

    //With this method, we get the data coming from PlanRoute screen
    private void getSharedPreferencesData() {

        SharedPreferences sharedPreferences = getSharedPreferences("MyData", MODE_PRIVATE);
        startLocation = sharedPreferences.getString("startLocation", DEFAULT);
        endLocation = sharedPreferences.getString("endLocation", DEFAULT);
        timeSelected = sharedPreferences.getString("timeSelected", DEFAULT);


        Log.d(LOG_TIME, "Im getting the next data (PlanSelectTime): " + startLocation + "\n" + endLocation);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.commit();


    }

    @Override
    protected void onResume() {
        super.onResume();

        if(!isResumed) {
            ProcessWaypoints processWaypoints = new ProcessWaypoints(startLocation, endLocation, timeSelected);
            processWaypoints.execute();
            isResumed = true;
        }


    }

    @Override
    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
        return false;
    }

    public class ProcessWaypoints extends GetBicinTimeJsonData {

        public ProcessWaypoints(String origin, String destination, String time) {
            super(origin, destination, time);


        }

        public void execute() {


            DownloadData downloadData = new DownloadData();
            Log.d(LOG_PROGRESS, "OnPreExecute called from execute ProcessWaypoints");
            downloadData.execute();
//            try {
//                downloadData.get();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            } catch (ExecutionException e) {
//                e.printStackTrace();
//            }
        }

        public class DownloadData extends DownloadJsonData {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                Log.d(LOG_PROGRESS, "OnPreExecute called from ProcessWaypoints");


                progressDialog = ProgressDialog.show(RouteSelectionActivity.this, "Please, Wait", "Wait...", true);
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);

                waypointsArrayList = getWaypointsArrayList();


                sumTimeArrayList = getSumTimeArrayList();
                distanceArrayList = getDistanceArrayList();
                streetUpArrayList = getStreetUpArrayList();
                streetDownArrayList = getStreetDownArrayList();
                streetNumberUpArrayList = getStreetNumberUpArrayList();
                streetNumberDownArrayList = getStreetNumberDownArrayList();
                stationIdUpArrayList = getStationIdUpArrayList();
                stationIdDownArrayList = getStationIdDownArrayList();
                stationBikesUpNowArrayList = getStationBikesUpNowArrayList();
                stationBikesDownNowArrayList = getStationSlotsDownNowArrayList();
                stationBikesUpEstArrayList = getStationBikesDownNowArrayList();
                stationBikesDownEstArrayList = getStationBikesDownEstArrayList();
                stationSlotsUpNowArrayList = getStationSlotsUpNowArrayList();
                stationSlotsDownNowArrayList = getStationSlotsDownNowArrayList();
                stationSlotsUpEstArrayList = getStationSlotsUpEstArrayList();
                stationSlotsDownEstArrayList = getStationSlotsDownEstArrayList();
                stationRiskArrayList = getStationRiskArrayList();

                RouteInformation routeTest = new RouteInformation(waypointsArrayList, sumTimeArrayList, distanceArrayList, streetUpArrayList, streetDownArrayList, streetNumberUpArrayList, streetNumberDownArrayList
                , stationIdUpArrayList, stationIdDownArrayList, stationBikesUpNowArrayList, stationBikesDownNowArrayList, stationBikesUpEstArrayList, stationBikesDownEstArrayList,
                        stationSlotsUpNowArrayList, stationSlotsDownNowArrayList , stationSlotsUpEstArrayList, stationSlotsDownEstArrayList, stationRiskArrayList);
                Log.d(LOG_DLOAD, "Size of waypoints array inside thread is: " + waypointsArrayList.size());
//                expListAdapter.notifyDataSetChanged();


                setGroupParents();
                setChildData();
                //once data loading finished, start to paint the children on the listview
                //this may be done by calling the adapter before the progressDialog dismiss i think



                //Once download both waypoints, we finish the progressdialog...
                progressDialog.dismiss();
                expListAdapter = new MyExpandableAdapter(
                        RouteSelectionActivity.this, parentItems, childrenItems, routeTest);



                expandableList.setAdapter(expListAdapter);

            }

            @Override
            protected String doInBackground(String... params) {
                return super.doInBackground(params);
            }
        }
    }
}
