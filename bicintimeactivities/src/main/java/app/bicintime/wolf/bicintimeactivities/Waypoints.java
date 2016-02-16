package app.bicintime.wolf.bicintimeactivities;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by wolf on 2/7/2016.
 */
public class Waypoints {



    LatLng coordinates;

    public Waypoints(Double lat, Double lng){

        coordinates = new LatLng(lat,lng);

    }

    public LatLng getCoordinates() {
        return coordinates;
    }

    public String toString(){

        //41.394694, 2.191493
        return coordinates.latitude + "," + coordinates.longitude;

    }

}
