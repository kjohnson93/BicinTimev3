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
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

/**
 * Created by wolf on 1/15/2016.
 */
public class MapFragment extends Fragment {


    MapView mMapView;
    private GoogleMap googleMap;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.map_fragment, container, false);

        mMapView = (MapView) rootView.findViewById(R.id.mapFragmentView);
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

        double latitude_array[]={41.401845, 41.395149, 41.398755, 41.388452 };
        double longitude_array[]= {2.181116, 2.171503, 2.195879, 2.196050};


        googleMap = mMapView.getMap();



        MarkerOptions markerOptions[];

        ArrayList<MarkerOptions> markers = new ArrayList<MarkerOptions>();


        for (int i= 0; i<latitude_array.length; i++){

            markers.add(new MarkerOptions().position(
                    new LatLng(latitude_array[i], longitude_array[i])).title("Hello Maps"));

            markers.get(i).icon(BitmapDescriptorFactory
                    .defaultMarker(BitmapDescriptorFactory.HUE_ROSE));

            googleMap.addMarker(markers.get(i));



        }


        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(latitude, longitude)).zoom(12).build();
        googleMap.animateCamera(CameraUpdateFactory
                .newCameraPosition(cameraPosition));



        return rootView;
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
}
