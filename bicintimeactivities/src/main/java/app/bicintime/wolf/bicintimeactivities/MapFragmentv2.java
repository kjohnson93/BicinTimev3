package app.bicintime.wolf.bicintimeactivities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

    MapView mMapView;
    private GoogleMap googleMap;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.map_fragment_v2, container, false);

        MapFragment mapFragment= (MapFragment) getActivity().getFragmentManager().findFragmentById(R.id.mapfragment);
        mapFragment.getMapAsync(this);

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
