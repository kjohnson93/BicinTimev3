package app.bicintime.wolf.bicintimeactivities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by wolf on 1/18/2016.
 */
public class MapFragmentv2 extends Fragment implements OnMapReadyCallback, GoogleMap.OnCameraChangeListener, GoogleMap.OnMarkerClickListener, GoogleMap.OnMapClickListener {

    private static final String LOG_TAG = "LOGTRACE";
    private static final String LOG_BACK = "LOGBACK";
    private static final String LOG_MAP = "LOGMAP";
    private static final String FRAGMENT_KEY = "test";

    MapView mMapView;
    private GoogleMap googleMap;

    private static View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {



        if (rootView != null) {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null)
                parent.removeView(rootView);
        }
        try {
            rootView = inflater.inflate(R.layout.map_fragment_v2, container, false);
        } catch (InflateException e) {
        /* map is already there, just return view as it is  */
        }

        MapFragment mapFragment= (MapFragment) getActivity().getFragmentManager().findFragmentById(R.id.mapfragment);
        mapFragment.getMapAsync(this);

        final FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        final Button buttonCenterLocation = (Button) rootView.findViewById(R.id.map_setlocation);
        buttonCenterLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*
                Intent intent = new Intent();
                intent.putExtra(FRAGMENT_KEY, "Location :)");
                getTargetFragment().onActivityResult(getTargetRequestCode(), getActivity().RESULT_OK, intent);

                for(int i = 0; i<fragmentManager.getBackStackEntryCount(); i++) {

                    Log.d(LOG_TAG, "PRINting backstack: " + fragmentManager.getBackStackEntryAt(i));
                }*/ //this approach not working, using sharedpreferences instead...
                fragmentManager.popBackStack(PlanRouteFragment.class.getName(),0); //dunno why pop_back_stack_inclusive is not working as intended

            }
        });

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);



        //com.google.android.gms.maps.MapSFragment = (com.google.android.gms.maps.MapSFragment)  getActivity().getFragmentManager().findFragmentById(R.id.mapfragment);






    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

        this.googleMap = googleMap;
        googleMap.setMyLocationEnabled(true);

        final LatLng WTC = new LatLng(41.372203, 2.180496);
        double latitude = 41.372203;
        double longitude = 2.180496;

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(latitude, longitude)).zoom(12).build();
        googleMap.animateCamera(CameraUpdateFactory
                .newCameraPosition(cameraPosition));

        googleMap.addMarker(new MarkerOptions()
                .position(WTC)
                .title("Marker"));

        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                Log.d(LOG_MAP," Map was clicked (inside onMapReady) =): " + latLng.toString());

            }
        });

    }

    @Override
    public void onCameraChange(CameraPosition cameraPosition) {


        Log.d(LOG_MAP," Camera was changed =): " + cameraPosition);
    }

    @Override
    public void onMapClick(LatLng latLng) {

        Log.d(LOG_MAP," Map was clicked =): " + latLng.toString());

    }

    @Override
    public boolean onMarkerClick(Marker marker) {

        Log.d(LOG_MAP, "Marker was clicked: " + marker.toString());

        return false;


    }
}
