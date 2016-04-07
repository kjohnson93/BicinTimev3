package app.bicintime.wolf.bicintimeactivities;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
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

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends BaseActivity implements OnMapReadyCallback, LocationListener {

    private static final String FRAGMENT_KEY = "test";
    private GoogleMap googleMap;

    private static final String LOG_MAP = "LOGMAP";
    private static final String LOG_FLOW = "LOG_FLOW";

    LocationManager mLocationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        String previousActivityTitle = intent.getStringExtra("previousActivityTitle");
        setTitle(previousActivityTitle);


        setContentView(R.layout.activity_maps);

        agregarToolbar();
        setUpDrawer();

        Log.d(LOG_FLOW, "MapsActivity onCreate navigation");

        SupportMapFragment supportMapFragment = (SupportMapFragment) this.getSupportFragmentManager().findFragmentById(R.id.mapFragment);
        supportMapFragment.getMapAsync(this);
        /*
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //DO CHECK PERMISSION

            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                    || checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMI
                    SSION_GRANTED) {
                //DO OP WITH LOCATION SERVICE
            }
        }*/


        Button buttonSetLocation = (Button) findViewById(R.id.map_setlocation);


        buttonSetLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LatLng myCoordinates = googleMap.getCameraPosition().target;
                Double latitudeLong, longitudeLong;

                Log.d(LOG_MAP, "My center coordinates are: " + myCoordinates + "=)");

                Intent intent = getIntent();
                String previousActivity = intent.getStringExtra("previousActivity");

                if (previousActivity.equals(PlanRouteStartActivity.class.getName())) {

                    Log.d(LOG_MAP, "Entering start if: " + myCoordinates);

                    Intent intent1 = new Intent(MapsActivity.this, PlanRouteEndActivity.class);
                    intent1.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

                    SharedPreferences sharedPreferences = getSharedPreferences("MyData", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();

                    //when we start the route calculation, we wipe out the data from the sharedpreferences file.
                    editor.clear();

                    int maxLength = 10;
                    String latitude, longitude, location;
                    //location = myCoordinates.toString();
                    latitudeLong = myCoordinates.latitude;
                    latitude = latitudeLong.toString().substring(0, maxLength);
                    longitudeLong = myCoordinates.longitude;
                    longitude = longitudeLong.toString().substring(0, maxLength);
                    location = "" + latitude + "," + longitude;


                    Geocoder geocoder;
                    List<Address> addresses = null;
                    geocoder = new Geocoder(MapsActivity.this, Locale.getDefault());

                    try {
                        addresses = geocoder.getFromLocation(latitudeLong,longitudeLong, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                    String city = addresses.get(0).getLocality();
//                    String state = addresses.get(0).getAdminArea();
//                    String country = addresses.get(0).getCountryName();
//                    String postalCode = addresses.get(0).getPostalCode();
//                    String knownName = addresses.get(0).getFeatureName(); // Only if available else return NULL

                    editor.putString("streetStart", address);
                    editor.putString("currentLocationStart", location);
                    Log.d(LOG_MAP, "Passing the following start coordinates:" + location);
                    editor.commit();

                    startActivity(intent1);


                } else if (previousActivity.equals(PlanRouteEndActivity.class.getName())) {

                    Log.d(LOG_MAP, "Entering end if: " + myCoordinates);

                    Intent intent1 = new Intent(MapsActivity.this, PlanSelectTimeActivity.class);
                    intent1.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

                    SharedPreferences sharedPreferences = getSharedPreferences("MyData", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();

                    int maxLength = 10;
                    String latitude, longitude, location;
                    //location = myCoordinates.toString();
                    latitudeLong = myCoordinates.latitude;
                    latitude = latitudeLong.toString().substring(0, maxLength);
                    longitudeLong = myCoordinates.longitude;
                    longitude = longitudeLong.toString().substring(0, maxLength);
                    location = "" + latitude + "," + longitude;

                    Geocoder geocoder;
                    List<Address> addresses = null;
                    geocoder = new Geocoder(MapsActivity.this, Locale.getDefault());

                    try {
                        addresses = geocoder.getFromLocation(latitudeLong,longitudeLong, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()

                    editor.putString("streetEnd", address);
                    editor.putString("currentLocationEnd", location);
                    Log.d(LOG_MAP, "Passing the following end coordinates:" + location);
                    editor.commit();

                    startActivity(intent1);

                }
            }
        });


    }

    @Override
    public void onBackPressed() {

        // go back to the menu
//        Intent intent = new Intent(this, PlanRouteActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
//        startActivity(intent);

        Intent intent = getIntent();
        String previousActivity = intent.getStringExtra("previousActivity");

        if (previousActivity.equals(PlanRouteStartActivity.class.getName())) {

            Intent intent1 = new Intent(this, MainActivity.class);
            intent1.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

            startActivity(intent1);


        } else if (previousActivity.equals(PlanRouteEndActivity.class.getName())) {

            Intent intent1 = new Intent(this, PlanRouteStartActivity.class);
            intent1.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            //put extra flagscurrentLocationEnd
            startActivity(intent1);

        }

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


        googleMap.setMyLocationEnabled(true);


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

            locationManager.requestLocationUpdates(provider, 20000, 0, this);


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
}
