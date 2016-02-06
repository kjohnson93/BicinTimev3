package app.bicintime.wolf.bicintimeactivities;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by wolf on 2/5/2016.
 */

//Thise class is useful to store the coordinates coming from Google Map Directions API.
public class Steps {

    private LatLng startLocationStep;
    private LatLng endLocationStep;

    public Steps(double startLatitude, double startlongitude, double endLatitude, double endLongitude ){

        startLocationStep = new LatLng(startLatitude, startlongitude);
        endLocationStep = new LatLng(endLatitude, endLongitude);

    }

    public LatLng getStartLocationStep() {
        return startLocationStep;
    }

    public LatLng getEndLocationStep() {
        return endLocationStep;
    }

    public String toString(){

        String startLocation = startLocationStep.toString();

        String endLocation = endLocationStep.toString();


        return "Start Location: " + startLocation + "End Location: " + endLocation;


    }
}
