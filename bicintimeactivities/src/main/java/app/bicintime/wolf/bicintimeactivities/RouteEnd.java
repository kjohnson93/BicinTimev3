package app.bicintime.wolf.bicintimeactivities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public class RouteEnd extends BaseActivity implements OnMapReadyCallback, LocationListener {

    private static final String FRAGMENT_KEY = "test";
    private GoogleMap googleMap;
    private static final String LOG_MAP = "LOGMAP";
    private static final String LOG_FLOW = "LOG_FLOW";
    LocationManager mLocationManager;
    String startLocation = null;
    String endLocation = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_end);

        //setting the screen nav drawer
        agregarToolbar();
        setUpDrawer();

        //getting data from start scren map
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            startLocation = getIntent().getStringExtra("startLocation");

        }



        //calling the google map methods
        SupportMapFragment supportMapFragment = (SupportMapFragment) this.getSupportFragmentManager().findFragmentById(R.id.mapFragmentEnd);
        supportMapFragment.getMapAsync(this);

        endLocation = getLocation();

        Button buttonSelection = (Button) findViewById(R.id.button_map_setlocation);
        buttonSelection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Intent intent = new Intent(RouteEnd.this, TimeSelection.class);
                intent.putExtra("startLocation", startLocation);
                intent.putExtra("endLocation",endLocation);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            }
        });


    }


    //Returns the latlong location of the center point on the camera map
    private String getLocation() {

        int maxLength = 10;
        //todo watch for the np exception when navigatin' back
        LatLng myCoordinates = googleMap.getCameraPosition().target;
        Double latitudeLong, longitudeLong;
        String latitude, longitude, startLocation;
        latitudeLong = myCoordinates.latitude;
        longitudeLong = myCoordinates.longitude;
        latitude = latitudeLong.toString().substring(0, maxLength);
        longitude = longitudeLong.toString().substring(0, maxLength);
        return "" + latitude + "," + longitude;
    }

    private Location getLastKnownLocation() {
        mLocationManager = (LocationManager)getApplicationContext().getSystemService(LOCATION_SERVICE);
        List<String> providers = mLocationManager.getProviders(true);
        Location bestLocation = null;

        for (String provider : providers) {
            Location l = mLocationManager.getLastKnownLocation(provider);
            if (l == null) {
                continue;
            }
            if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                // Found best last known location: %s", l);
                bestLocation = l;
            }
        }
        return bestLocation;
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

        this.googleMap = googleMap;


        // Getting LocationManager object from System Service LOCATION_SERVICE
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        // Creating a criteria object to retrieve provider
        Criteria criteria = new Criteria();

        // Getting the name of the best provider
        String provider = locationManager.getBestProvider(criteria, true);

        // Assume thisActivity is the current activity
        // int permissionCheck = ContextCompat.checkSelfPermission(thisActivity,
        //       Manifest.permission.WRITE_CALENDAR);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED //This is ok, but i will have to figure it out how to ask a user permission ON RUNTIME
                && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) { //also why ContextCompat instead of ActivityCompat ??
            //We have to request location, otherwise it will throw a null pointer because no app has requested coordinates


            Log.d(LOG_MAP, "Entering checkSelfPermission");

            Location location = getLastKnownLocation();

            if (location != null) {
                onLocationChanged(location);
                Log.d(LOG_MAP, "My location is: " + "\n Latitude: " + location.getLatitude() + "Longitude: " + location.getLongitude());
            }else{
                throw new RuntimeException("It was not possible to get the location");
            }

            locationManager.requestLocationUpdates(provider,2000,0,this);


        }


    }


    @Override
    public void onLocationChanged(Location location) {

        Log.d(LOG_MAP, "My location is: " + "\n Latitude: " + location.getLatitude() + "Longitude: " + location.getLongitude());


        // Getting latitude of the current location
        double latitude = location.getLatitude();

        // Getting longitude of the current location
        double longitude = location.getLongitude();

        // Creating a LatLng object for the current location
        LatLng latLng = new LatLng(latitude, longitude);

        // Showing the current location in Google Map
        //googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));


        // Zoom in the Google Map
        //googleMap.animateCamera(CameraUpdateFactory.zoomTo(15));

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(latitude, longitude)).zoom(12).build();
        googleMap.animateCamera(CameraUpdateFactory
                .newCameraPosition(cameraPosition));

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onBackPressed() {

        Intent intent = new Intent(this, RouteStart.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);


    }
}
