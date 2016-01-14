package app.bicintime.wolf.bicintime;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by wolf on 12/31/2015.
 */
public class PlanRouteFragmentStartA2 extends Fragment implements GoogleMap.OnMarkerClickListener{

    MapView mMapView;
    private GoogleMap googleMap;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        // inflat and return the layout
        View v = inflater.inflate(R.layout.plan_route_start_a2, container,
                false);
        mMapView = (MapView) v.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume();// needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
    }


        googleMap = mMapView.getMap();
        // latitude and longitude
        double latitude = 41.372203;
        double longitude = 2.180496;

        final LatLng WTC = new LatLng(41.372203, 2.180496);

        // create marker
        MarkerOptions marker = new MarkerOptions().position(
                new LatLng(latitude, longitude)).title("Hello Maps").draggable(true);

        // Changing marker icon
        marker.icon(BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_ROSE));

        // adding marker
        googleMap.addMarker(marker);

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(latitude, longitude)).zoom(12).build();
        googleMap.animateCamera(CameraUpdateFactory
                .newCameraPosition(cameraPosition));

        return v;

    }

    @Override
    public void onResume() {
        super.onResume();

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();


        Log.d("BACK", "The backentrycount is: " + fragmentManager.getBackStackEntryCount());
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    public void onResumeFragment(){

        Log.d("BACK", "Fuck you, Reloading PlanA2");

    }


    @Override
    public boolean onMarkerClick(Marker marker) {



        return false;
    }


}
