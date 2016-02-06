package app.bicintime.wolf.bicintimeactivities;

import android.net.Uri;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wolf on 2/2/2016.
 */


//This class will make the json parsing to extract the data from the json files.
public class GetMapDirectionsJsonData extends GetRawData {

    private static final String LOG_DLOAD = "LOGDOWNLOAD";
    private Uri mDestinationUri;
    String origin, destination, time;
    //private ArrayList<Route> routes = new ArrayList<>();
    private List<Steps> stepsList;
    private ArrayList<Steps> stepsArrayList = new ArrayList<>();

    //constructor
    public GetMapDirectionsJsonData(String origin, String destination, String time) {
        super(null);
        this.origin = origin;
        this.destination = destination;
        this.time = time;
        //CreateUri();

        //The URI, Hardcoded for now.
        final LatLng WTC = new LatLng(41.372203, 2.180496);
        mDestinationUri = Uri.parse("https://maps.googleapis.com/maps/api/directions/json?mode=bicycling&origin=41.401845,2.181116&destination=41.372203,2.180496&key=AIzaSyCgKXy1mAqFeLr0H-NgVCCTEb3qimgQnWA");

        Log.d(LOG_DLOAD, "Getting into getMap... constructor..");

    }

    //This method will prepared and execute the json parsing thread.
    public void execute() {
        Log.d(LOG_DLOAD, "Getting into getMap... execute().." + mDestinationUri.toString());
        super.setmRawUrl(mDestinationUri.toString());
        DownloadJsonData downloadJsonData = new DownloadJsonData();
        downloadJsonData.execute(mDestinationUri.toString()); //care


    }

    //in this method we will form the uri, not used for now.
    private Boolean CreateUri() {

        final String BICINTIME_BASE_URL = "http://bicing.h2o.net.br/getroute.php";
        final String PARAM_ORIGIN = "origin";
        final String PARAM_DESTINATION = "destination";
        final String PARAM_TIME = "time";


        Log.d(LOG_DLOAD, "Im getting the next data (RouteActivity): " + origin + "\n" + destination + "\n" + time);

        //leave it encoded as %2C, no problem !! decoder will restore it!
        mDestinationUri = Uri.parse(BICINTIME_BASE_URL).buildUpon().appendQueryParameter(PARAM_ORIGIN, origin).appendQueryParameter(PARAM_DESTINATION, destination).appendQueryParameter(PARAM_TIME, time).
                build();


        Log.d(LOG_DLOAD, "My uri is: " + mDestinationUri);


        return mDestinationUri != null;

    }


    private ArrayList<LatLng> decodePoly(String encoded) {

        Log.i("Location", "String received: "+encoded);
        ArrayList<LatLng> poly = new ArrayList<LatLng>();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;

        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            LatLng p = new LatLng((((double) lat / 1E5)),(((double) lng / 1E5)));
            poly.add(p);
        }

        for(int i=0;i<poly.size();i++){
            Log.i("Location", "Point sent: Latitude: "+poly.get(i).latitude+" Longitude: "+poly.get(i).longitude);
        }
        return poly;
    }

    private ArrayList<Steps> generateSteps(ArrayList<LatLng> arrayLatLng){
        ArrayList<Steps> steps = new ArrayList<Steps>();

        // each step is composed by two points (starting and ending)
        for(int i = 0; i < arrayLatLng.size() - 1; i++){
            steps.add(
                    new Steps(arrayLatLng.get(i).latitude,
                            arrayLatLng.get(i).longitude,
                            arrayLatLng.get(i + 1).latitude,
                            arrayLatLng.get(i + 1).longitude));
        }

        return steps;
    }

    //This method is intended to being called after we get the data from GetRawData, so its called on PostExecute. It is responsible for json parsing process.
    public void processResult() {


        Log.d(LOG_DLOAD, "Entering on ProcessResult");
        if (getmDownloadStatus() != DownloadStatus.OK) {
            Log.d(LOG_DLOAD, "Error downloading raw file");
            return;
        }

        Log.d(LOG_DLOAD, "After if on ProcessResult");

        /*
        final String stationid = "station_id";
        final String bikes = "bikes";
        final String slots = "slots";
        final String latitude = "latitude";
        final String longitude = "longitude";
        final String street = "street";
        final String street_number = "street_number";*/

        try {

            JSONObject jsonResult = new JSONObject(getmData());

            JSONArray jsonRoutes = jsonResult.getJSONArray("routes");
            JSONObject routes = jsonRoutes.getJSONObject(0);

            JSONArray jsonLegs = routes.getJSONArray("legs");
            JSONObject legs = jsonLegs.getJSONObject(0);

            JSONArray jsonSteps = legs.getJSONArray("steps");

            List<JSONObject> stepJsonObjects;

           /* for (int i = 0; i < jsonSteps.length(); i++) {

                JSONObject stepObject = jsonSteps.getJSONObject(i);

                JSONObject startLocationObject = stepObject.getJSONObject("start_location");
                JSONObject endLocationObject = stepObject.getJSONObject("end_location");

                Double startLatitude = startLocationObject.getDouble("lat");
                Double startLongitude = startLocationObject.getDouble("lng");
                Double endLatitude = endLocationObject.getDouble("lat");
                Double endLongitude = endLocationObject.getDouble("lng");

                Steps step = new Steps(startLatitude, startLongitude, endLatitude, endLongitude);
                Log.d(LOG_DLOAD, "Step object:" + step.toString());
                //stepsList.add(step);null
                stepsArrayList.add(step);

                Log.d(LOG_DLOAD, "SIZE of steps in GetMapDirectionsJsonData is: " + stepsArrayList.size());


            }*/

            // get all encoded points and generate the array to draw the polyline
            JSONObject points = routes.getJSONObject("overview_polyline");
            ArrayList<LatLng> allPoints = decodePoly(points.getString("points"));
            stepsArrayList.addAll(generateSteps(allPoints));


            Log.d(LOG_DLOAD, "Let's see how many elements exists inside this array: " + jsonRoutes.length());
            Log.d(LOG_DLOAD, "Now let's see how many elements exist inside legs array:" + jsonLegs.length());
            Log.d(LOG_DLOAD, "Now let's see how many elements exist inside steps array:" + jsonSteps.length());
            //this returns 6 steps,
            //So i have to make a leg for each path between two waypoints. So i have to get those LatLng !


        } catch (JSONException jsone) {

            Log.e(LOG_DLOAD, jsone.getMessage());

        }


    }

    //Once we have filled the steps array, this is intended to be called to retrieve the information to the map screen showing process.
    public ArrayList<Steps> getSteps() {

        return stepsArrayList;
    }


    //this thread extends downloadrawdata to reuse funcionality, as extra, it will call processResult to parse json data.
    //Note that we always call first the super methods so the process is set correctly.
    public class DownloadJsonData extends DownloadRawData {

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.d(LOG_DLOAD, "Calling on ProcessResult");
            processResult();
        }

        @Override
        protected String doInBackground(String... params) {
            //Log.d(LOG_DLOAD, "Getting into getMap... DoInBackground..");
            //return super.doInBackground(params);
            String[] par = {mDestinationUri.toString()};
            return super.doInBackground(par);
        }

        private void getTest(){
            // nothing
            int i = 10;
        }
    }
}
