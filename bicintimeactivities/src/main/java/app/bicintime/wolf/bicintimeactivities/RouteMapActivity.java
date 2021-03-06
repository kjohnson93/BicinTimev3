package app.bicintime.wolf.bicintimeactivities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.WeakHashMap;

//this class will show our map with the route generated by the parameters set by the user.
//it implements onMapReadyCallBack, needed to be able to display a googleMap.
public class RouteMapActivity extends BaseActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private static final String LOG_DLOAD = "LOGDOWNLOAD";
    private static final String FRAGMENT_KEY = "test";
    private static final String LOG_POINTS = "LOGPOINTS";
    private static final String LOG_PROGRESS = "LOGPROGRESS";
    private GoogleMap googleMap; //our googlemap reference
    private static final String DEFAULT = "Default text";
    String startLocation, endLocation, loudiness, lanes, timeSelected;
    private ArrayList<LatLng> pointsRouteArrayList = new ArrayList<>();
    WeakHashMap<Marker, Object> hashMap = new WeakHashMap<Marker, Object>();
    String waypointUp, waypointDown;

    ArrayList<MarkerOptions> markers = new ArrayList<>();

    private int indexWpStart, indexWpArrive;

    ProgressDialog progressDialog;


    //this variable is useful to store the steps generated by Google Map Directions API.
    ArrayList<Steps> stepsArrayList = new ArrayList<>();
    ArrayList<Waypoints> waypointsArrayList = new ArrayList<>();

    private static final String LOG_MAP = "LOGMAP";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_map);

        agregarToolbar();
        setUpDrawer();

        setTitle("Your Route");
        getSharedPreferencesData();


        //these two steps are needed to display our google maps on our screen.
        SupportMapFragment supportMapFragment = (SupportMapFragment) this.getSupportFragmentManager().findFragmentById(R.id.mapFragment);
        supportMapFragment.getMapAsync(this);//This method will call our onMapReady (inherited method from our interface).

    }

    //On this method we will retrieve the information passed through previous screens as planroute and select time screens.
    private void getSharedPreferencesData() {

        SharedPreferences sharedPreferences = getSharedPreferences("MyData", MODE_PRIVATE);
        startLocation = sharedPreferences.getString("startLocation", DEFAULT);
        endLocation = sharedPreferences.getString("endLocation", DEFAULT);
        timeSelected = sharedPreferences.getString("timeSelected", DEFAULT);
        waypointUp = sharedPreferences.getString("waypointUp", DEFAULT);
        waypointDown = sharedPreferences.getString("waypointDown", DEFAULT);

        Log.d(LOG_DLOAD, "Im getting the next data (RouteActivity): " + startLocation + "\n" + endLocation + "\n" + loudiness + "\n" + lanes + "\n" + timeSelected);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.commit();
    }

    //This will be called to get our map display to java object code reference??
    @Override
    public void onMapReady(GoogleMap googleMap) {

        Log.d(LOG_DLOAD, "onMapReady called");


        this.googleMap = googleMap;

        googleMap.setOnMarkerClickListener(this); //Remember put listener similar tu button listener.

        // centralize the map to the center of Barcelona
        double latitude = 41.372203;
        double longitude = 2.180496;
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(latitude, longitude)).zoom(12).build();
        googleMap.animateCamera(CameraUpdateFactory
                .newCameraPosition(cameraPosition));

        Log.d(LOG_DLOAD, "stepsArrayList inside onMapRady size is: " + stepsArrayList.size());


    }

    @Override
    public void onBackPressed() {

        //todo ????
//        SharedPreferences sharedPreferences = getSharedPreferences("MyData", MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putString("startLocationToSelectTime", startLocation);
//        editor.putString("endLocationToSelectTime", endLocation);
//        editor.putString("loudinessToSelectTime", loudiness);
//        editor.putString("lanesToSelectTim", lanes);
//        // editor.putString("timeToRoute", timeSelected); //I think it may be not needed..
//        editor.commit();

        Intent intent = new Intent(this, RouteSelectionActivity.class);//fix sharedpref values on back navigation
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);

    }


    //We call our thread from the onResume
    //the steps are: OnCreate -> OnResume -> onMapReady -> onPostExecute
    @Override
    protected void onResume() {
        super.onResume();

        //ProcessSteps processSteps = new ProcessSteps(startLocation, endLocation, timeSelected);
        //processSteps.execute();
        Log.d(LOG_MAP, "Testng what are the coordinates:" + startLocation + "\n" + endLocation + "\n" + timeSelected);
        ProcessWaypoints processWaypoints = new ProcessWaypoints(startLocation, endLocation, timeSelected);
        processWaypoints.execute();


    }

    @Override
    public boolean onMarkerClick(Marker marker) {

        Log.d(LOG_MAP, "Marker clicked: " + marker.toString());

        Log.d(LOG_MAP, "Checking...: " + markers.get(0).toString());
        Log.d(LOG_MAP, "Checking...: " + markers.get(1).toString());
        Log.d(LOG_MAP, "Checking...: " + markers.get(2).toString());
        Log.d(LOG_MAP, "Checking...: " + markers.get(3).toString());

        MarkerOptions m1 = markers.get(0);

        //Marker m1= (Marker) hashMap.get(m1);
        Log.d(LOG_MAP, "Marker m1 from map: " + m1);
        Marker m2 = (Marker) hashMap.get("MarkerBiciGreen");
        Log.d(LOG_MAP, "Marker m2 from map: " + m2);
        Marker m3 = (Marker) hashMap.get("MarkerBiciRed");
        Log.d(LOG_MAP, "Marker m3 from map: " + m3);
        Marker m4 = (Marker) hashMap.get("MarkerFlagRed");
        Log.d(LOG_MAP, "Marker m4 from map: " + m4);

        if (marker.equals(m1)) { //Marker != MarkerOptions
            Log.d(LOG_MAP, "Clicked marker start walk");
        }

        if (marker.equals(m2)) {
            Log.d(LOG_MAP, "Clicked marker start bicycling");
        }

        if (marker.equals(m3)) {
            Log.d(LOG_MAP, "Clicked marker end bicycling");
        }

        if (marker.equals(m4)) {
            Log.d(LOG_MAP, "Clicked marker end walking");
        }

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
        }

        public class DownloadData extends DownloadJsonData {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                Log.d(LOG_PROGRESS, "OnPreExecute called from ProcessWaypoints");


                progressDialog = ProgressDialog.show(RouteMapActivity.this, "Please, Wait", "Wait...", true);
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);

                waypointsArrayList = getWaypointsArrayList();


                Log.d(LOG_DLOAD, "Size of waypoints array inside thread is: " + waypointsArrayList.size());

                Waypoints waypointUpTest = waypointsArrayList.get(0); //station subida
                Waypoints waypointDownTest = waypointsArrayList.get(1); //station bajada


//                String [] coordUp = waypointUp.split(",");
//                String [] coordDown = waypointDown.split(",");
//                Waypoints waypointUpTest = new Waypoints(Double.parseDouble(coordUp[0]),Double.parseDouble(coordUp[1]));
//                Waypoints waypointDownTest = new Waypoints(Double.parseDouble(coordDown[0]),Double.parseDouble(coordDown[1]));



                ProcessSteps processSteps = new ProcessSteps(startLocation, endLocation, waypointUpTest, waypointDownTest);
                processSteps.execute();

            }

            @Override
            protected String doInBackground(String... params) {
                return super.doInBackground(params);
            }
        }
    }

    //this class reuse the funcionality of other class with thread inside.
    public class ProcessSteps extends GetMapDirectionsJsonData {

        //constructor
        public ProcessSteps(String origin, String destination, Waypoints waypointUp, Waypoints waypointDown) {
            super(origin, destination, waypointUp, waypointDown);

        }

        public void execute() {
            //super.execute();
            ProcessData processData = new ProcessData();
            processData.execute();
        }

        //this thread will reuse the funcionality of other threads.
        //it is responsible for storing the data and having it available on the map screen for now.
        public class ProcessData extends DownloadJsonData {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                Log.d(LOG_PROGRESS, "OnPreExecute called from ProcessWaySteps");
            }

            protected void onPostExecute(String webData) {
                super.onPostExecute(webData);

                Log.d(LOG_DLOAD, "onPostExecute of processData called");

                //here i should fill the arraylist
                //stepsArrayList = getSteps();

                //Here we obtain the latlong array
                pointsRouteArrayList = getPointsRouteArrayList();

                indexWpStart = getIndexWpStart();
                indexWpArrive = getIndexWpArrive();

                //ArrayList<MarkerOptions> markers = new ArrayList<MarkerOptions>();

          /*      //calling this on Post execute, is right?
                for (int i = 0; i < stepsArrayList.size(); i++) {

                    Log.d(LOG_DLOAD, "Object in arrayList is: " + stepsArrayList.get(i).toString());
                    Polyline line = googleMap.addPolyline(new PolylineOptions()
                            .add(stepsArrayList.get(i).getStartLocationStep(), stepsArrayList.get(i).getEndLocationStep())
                            .width(5)
                            .color(Color.RED));

                    // for debugging purposes: draw a marker at the end of each step
                    // googleMap.addMarker(new MarkerOptions().position(new LatLng(stepsArrayList.get(i).getStartLocationStep().latitude, stepsArrayList.get(i).getStartLocationStep().longitude)));

                }
*/
                drawRoute(indexWpStart, indexWpArrive, pointsRouteArrayList);
        /*        //TODO Now we draw this way, but it has to be modified to draw the right segments of the route (walking, bicyling and walking again).
                for (int i = 0; i < pointsRouteArrayList.size() - 1; i++) {


                    Polyline polyline = googleMap.addPolyline(new PolylineOptions().add(pointsRouteArrayList.get(i), pointsRouteArrayList.get(i + 1)).width(5).color(Color.RED));

                    // for debugging purposes: draw a marker at the end of each step
                    // googleMap.addMarker(new MarkerOptions().position(new LatLng(stepsArrayList.get(i).getStartLocationStep().latitude, stepsArrayList.get(i).getStartLocationStep().longitude)));
                }*/

            }

            //Draw the three sections of the route.
            private void drawRoute(int indexWpStart, int indexWpArrive, ArrayList<LatLng> pointsRouteArrayList) {

                Log.d(LOG_POINTS, "Size of index Start Bicycle: " + indexWpStart);
                Log.d(LOG_POINTS, "Size of index Arrive Bicycle: " + indexWpArrive);
                Log.d(LOG_POINTS, "Size of pointsRouteArrayList" + pointsRouteArrayList.size());
                Log.d(LOG_PROGRESS, "asdsadsadsadas");

                //draw first section
                for (int i = 0; i < indexWpStart; i++) {
                    Polyline polyLine = googleMap.addPolyline(new PolylineOptions().add(pointsRouteArrayList.get(i), pointsRouteArrayList.get(i + 1)).width(5).color(Color.parseColor("#8B0D0A")));
                }

                //draw second section
                for (int i = indexWpStart; i < indexWpArrive; i++) {
                    Polyline polyLine = googleMap.addPolyline(new PolylineOptions().add(pointsRouteArrayList.get(i), pointsRouteArrayList.get(i + 1)).width(5).color(Color.parseColor("#0E568B")));
                }

                //draws third section
                for (int i = indexWpArrive; i < pointsRouteArrayList.size() - 1; i++) {

                    Polyline polyLine = googleMap.addPolyline(new PolylineOptions().add(pointsRouteArrayList.get(i), pointsRouteArrayList.get(i + 1)).width(5).color(Color.parseColor("#8B0D0A")));
                }


                //Getting the latlong coordinates for all the special points
                LatLng latLngWpStart = pointsRouteArrayList.get(indexWpStart);
                LatLng latLngWpArrive = pointsRouteArrayList.get(indexWpArrive);
                String[] startLatlong = startLocation.split(",");
                String[] endLatLong = endLocation.split(",");
                double startLatitude = Double.parseDouble(startLatlong[0]);
                double startLongitude = Double.parseDouble(startLatlong[1]);
                double endLatitude = Double.parseDouble(endLatLong[0]);
                double endLongitude = Double.parseDouble(endLatLong[1]);
                LatLng startLatLng = new LatLng(startLatitude, startLongitude);
                LatLng endLatLng = new LatLng(endLatitude, endLongitude);


                //Draw markers on the 4 special points on the route. It needs every latlong coordinate for each one
                drawMarker(latLngWpStart, latLngWpArrive, startLatLng, endLatLng);


                progressDialog.dismiss();

            }

            //Draws the four markers on the route
            private void drawMarker(LatLng latLngWpStart, LatLng latLngWpArrive, LatLng startLatLng, LatLng endLatLng) {

                ArrayList<LatLng> markersLatLng = new ArrayList<>();


                markersLatLng.add(startLatLng);
                markersLatLng.add(latLngWpStart);
                markersLatLng.add(latLngWpArrive);
                markersLatLng.add(endLatLng);

                for (int i = 0; i < 4; i++) {

                    markers.add(new MarkerOptions().position(markersLatLng.get(i)));
                }

                // first, print the markers at the start and destination points, so they do not
                // override the station marks
                Marker m1 = googleMap.addMarker(markers.get(0).icon(BitmapDescriptorFactory.fromResource(R.drawable.greendot)));
                hashMap.put(m1, m1);
                Marker m4 = googleMap.addMarker(markers.get(3).icon(BitmapDescriptorFactory.fromResource(R.drawable.reddot)));
                hashMap.put(m4, m4);

                // here, the station marks
                Marker m2 = googleMap.addMarker(markers.get(1).icon(BitmapDescriptorFactory.fromResource(R.drawable.pinflatbicigreen)));
                hashMap.put(m2, m2);
                Marker m3 = googleMap.addMarker(markers.get(2).icon(BitmapDescriptorFactory.fromResource(R.drawable.pinflatbicired)));
                hashMap.put(m3, m3);



            }

        }

    }

}
