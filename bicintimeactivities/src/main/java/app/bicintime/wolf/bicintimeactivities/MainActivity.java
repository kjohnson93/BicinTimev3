package app.bicintime.wolf.bicintimeactivities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

//AKA MapActivity
public class MainActivity extends BaseActivity implements OnMapReadyCallback, LocationListener {

    private GoogleMap googleMap;
    private static final String LOG_MAP = "LOGMAP";
    private static final String LOG_FLOW = "LOG_FLOW";

    LocationManager mLocationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(LOG_FLOW, "MainActivity on create navigation");

        agregarToolbar();
        setUpDrawer();
        setTitle("BicinTime");


        Bundle mainScreen = getIntent().getExtras();
        if (mainScreen == null) {
            // open drawer by default
            openDrawer();
        }

        SupportMapFragment supportMapFragment = (SupportMapFragment) this.getSupportFragmentManager().findFragmentById(R.id.mapFragmentStartContenidoPrincipal);
        supportMapFragment.getMapAsync(this);

    }

    private Location getLastKnownLocation() {
        mLocationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
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
    public void onBackPressed() {

        super.onBackPressed();

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
            } else {
                throw new RuntimeException("It was not possible to get the location");
            }

            locationManager.requestLocationUpdates(provider, 2000, 0, this);


        }

        double latitude = 41.372203;
        double longitude = 2.180496;

        final LatLng WTC = new LatLng(41.372203, 2.180496);

        double latitude_array[] = {41.401845, 41.395149, 41.398755, 41.388452};
        double longitude_array[] = {2.181116, 2.171503, 2.195879, 2.196050};

        ArrayList<MarkerOptions> markers = new ArrayList<MarkerOptions>();


        for (int i = 0; i < latitude_array.length; i++) {

            markers.add(new MarkerOptions().position(
                    new LatLng(latitude_array[i], longitude_array[i])).title("Hello Maps"));

            markers.get(i).icon(BitmapDescriptorFactory
                    .defaultMarker(BitmapDescriptorFactory.HUE_ROSE));

            googleMap.addMarker(markers.get(i));


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
