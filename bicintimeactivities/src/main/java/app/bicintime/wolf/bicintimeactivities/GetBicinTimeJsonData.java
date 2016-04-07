package app.bicintime.wolf.bicintimeactivities;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by wolf on 2/2/2016.
 */
public class GetBicinTimeJsonData extends GetRawData {

    private static final String LOG_DLOAD = "LOGDOWNLOAD";
    private static final String LOG_URI = "LOGURI";
    private Uri mDestinationUri;
    String origin, destination, time;
    private ArrayList<Route> routes = new ArrayList<>();
    private ArrayList<Waypoints> waypointsArrayList = new ArrayList<>();

    //en print, sumTime
    //en distance, sum
    //en start, street
    //en arrive, street, street_number
    //en arrive tambien, dentro de estimation, bikes, slot, station_id

    private ArrayList<String> sumTimeArrayList = new ArrayList<>();
    private ArrayList<Integer> distanceArrayList = new ArrayList<>();
    private ArrayList<String> streetUpArrayList = new ArrayList<>();
    private ArrayList<String> streetDownArrayList = new ArrayList<>();
    private ArrayList<String> streetNumberUpArrayList = new ArrayList<>();
    private ArrayList<String> streetNumberDownArrayList = new ArrayList<>();
    private ArrayList<String> stationIdUpArrayList = new ArrayList<>();
    private ArrayList<String> stationIdDownArrayList = new ArrayList<>();
    private ArrayList<Integer> stationBikesUpNowArrayList = new ArrayList<>();
    private ArrayList<Integer> stationBikesDownNowArrayList = new ArrayList<>();
    private ArrayList<Integer> stationBikesUpEstArrayList = new ArrayList<>();
    private ArrayList<Integer> stationBikesDownEstArrayList = new ArrayList<>();
    private ArrayList<Integer> stationSlotsUpNowArrayList = new ArrayList<>();
    private ArrayList<Integer> stationSlotsDownNowArrayList = new ArrayList<>();
    private ArrayList<Integer> stationSlotsUpEstArrayList = new ArrayList<>();
    private ArrayList<Integer> stationSlotsDownEstArrayList = new ArrayList<>();
    private ArrayList<Double> stationRiskArrayList = new ArrayList<>();

    public GetBicinTimeJsonData(String origin, String destination, String time) {
        super(null);
        this.origin = origin;
        this.destination = destination;
        this.time = time;
        CreateUri();


        //http://bicing.h2o.net.br/getroute.php?origin=41.4434894%2C2.17383787&destination=41.4147391%2C2.20838706&time=1459071324774

    }

    public void execute() {
        super.setmRawUrl(mDestinationUri.toString());
        DownloadJsonData downloadJsonData = new DownloadJsonData();
        downloadJsonData.execute(mDestinationUri.toString()); //care


    }

    private Boolean CreateUri() {

        final String BICINTIME_BASE_URL = "http://bicing.h2o.net.br/getroute.php";
        final String PARAM_ORIGIN = "origin";
        final String PARAM_DESTINATION = "destination";
        final String PARAM_TIME = "time";


        Log.d(LOG_DLOAD, "Im getting the next data (RouteActivity): " + origin + "\n" + destination + "\n" + time);

        //leave it encoded as %2C, no problem !! decoder will restore it!
        mDestinationUri = Uri.parse(BICINTIME_BASE_URL).buildUpon().appendQueryParameter(PARAM_ORIGIN, origin).appendQueryParameter(PARAM_DESTINATION, destination).appendQueryParameter(PARAM_TIME, time).
                build();

//        mDestinationUri = Uri.parse("http://bicing.h2o.net.br/getroute.php?origin=41.40293,2.1917018000000326&destination=41.39464359999999,2.1588451000000077&time=1439804497313");

        Log.d(LOG_URI, "My uri from BicinTime is: " + mDestinationUri);

        return mDestinationUri != null;

    }

    public void processResult() {

        Log.d(LOG_DLOAD, "Entering on ProcessResult");
        if (getmDownloadStatus() != DownloadStatus.OK) {
            Log.d(LOG_DLOAD, "Error downloading raw file");
            return;
        }

        Log.d(LOG_DLOAD, "After if on ProcessResult");

        //getting the waypoints coordinates coming from the 5 possible routes (10 waypoints)
        try {
            JSONArray jsonArray = new JSONArray(getmData());

            Log.d(LOG_DLOAD, "Length of jsonarray is: " + jsonArray.length());

            for (int i = 0; i < jsonArray.length(); i++) {

                //should be called another way, because first object gets: station object, distance object!, time object, print object and risk object. All inside a "NoName" object,
                //which are the 5 available routes to pick for the user...
                JSONObject jsonRoute = jsonArray.getJSONObject(i);
                JSONObject jsonStation = jsonRoute.getJSONObject("station");

                //JSONObject jsonStation = jsonArray.getJSONObject(i);

                JSONObject start = jsonStation.getJSONObject("start");

                String stationid = start.getString("station_id");
//                String bikesUp = start.getString("bikes");
                //now
                Integer bikesUpNow = start.getInt("bikes");
//                String slotsUp = start.getString("slots");
                Integer slotsUpNow = start.getInt("slots");
                Double latitudeStart = start.getDouble("latitude");
                Double longitudeStart = start.getDouble("longitude");
               /* String latitude = start.getString("latitude");
                String longitude = start.getString("longitude");*/
                String streetUp = start.getString("street");
                String streetNumberUp = start.getString("street_number");


                JSONObject estimation = start.getJSONObject("estimation");
//                String bikes_est = estimation.getString("bikes");
//                String slots_est = estimation.getString("slots");
                Integer bikesUpEst = estimation.getInt("bikes");
                Integer slotsUpEst = estimation.getInt("slots");
                JSONObject risk_est = estimation.getJSONObject("risk");
//                String departure = risk_est.getString("departure");
//                String destination = risk_est.getString("destination");
                Double departure = risk_est.getDouble("departure");
                Double destination = risk_est.getDouble("destination");

                JSONObject arrive = jsonStation.getJSONObject("arrive");
                String stationidDown = arrive.getString("station_id");
//                String bikesDownNow = arrive.getString("bikes");
//                String slotsDownNow = arrive.getString("slots");
                Integer bikesDownNow = arrive.getInt("bikes");
                Integer slotsDownNow = arrive.getInt("slots");
                Double latitudeArrive = arrive.getDouble("latitude");
                Double longitudeArrive = arrive.getDouble("longitude");
       /*         String latitude_arr = arrive.getString("latitude");
                String longitude_arr = arrive.getString("longitude");*/
                String streetDown = arrive.getString("street");
                String streetNumberDown = arrive.getString("street_number");

                JSONObject estimation_arr = arrive.getJSONObject("estimation");
//                String bikes_arr_est = estimation_arr.getString("bikes");
//                String slots_arr_est = estimation_arr.getString("slots");
                Integer bikesDownEst = estimation_arr.getInt("bikes");
                Integer slotsDownEst = estimation_arr.getInt("slots");

                JSONObject risk_arr_est = estimation_arr.getJSONObject("risk");
//                String departure_arr_est = risk_arr_est.getString("departure");
//                String destination_arr_est = risk_arr_est.getString("destination");
                Double departure_arr_est = risk_arr_est.getDouble("departure");
                Double destination_arr_est = risk_arr_est.getDouble("destination");

                JSONObject distance = jsonRoute.getJSONObject("distance");
//                String depToStation = distance.getString("depToStation");
                Integer departToStation = distance.getInt("depToStation");
//                String bicing = distance.getString("bicing");
                Integer distBicing = distance.getInt("bicing");
//                String stationToDest = distance.getString("stationToDest");
                Integer stationToDest = distance.getInt("stationToDest");
//                String distanceSum = distance.getString("sum");
                Integer distanceSum = distance.getInt("sum");

                JSONObject time = jsonRoute.getJSONObject("time");
//                String depToStation_time = time.getString("depToStation");
                //in minutes
                Integer timeDepartToStation = time.getInt("depToStation");
//                String bicing_time = time.getString("bicing");
                Integer timeBicing = time.getInt("bicing");
//                String stationToDest_time = time.getString("stationToDest");
//                String sum_time = time.getString("sum");
                Integer timeStationToDest = time.getInt("stationToDest");
                Integer timeSum = time.getInt("sum");

                JSONObject print = jsonRoute.getJSONObject("print");
                String sumTime = print.getString("sumTime");

                JSONObject risk = jsonRoute.getJSONObject("risk");
//                String departure_risk = risk.getString("departure");
//                String destination_risk = risk.getString("destination");
                Double departure_risk = risk.getDouble("departure");
                Double destination_risk = risk.getDouble("destination");

                //waypoint data
                Waypoints waypointStationStart = new Waypoints(latitudeStart, longitudeStart);
                Waypoints waypointStationArrive = new Waypoints(latitudeArrive, longitudeArrive);
                waypointsArrayList.add(waypointStationStart);
                waypointsArrayList.add(waypointStationArrive);

                //todo pillar los siguientes datos...

                //sumTime data
                sumTimeArrayList.add(sumTime);
                //en distance, sum
                distanceArrayList.add(distanceSum);
                //en start, street
                streetUpArrayList.add(streetUp);
                streetNumberUpArrayList.add(streetNumberUp);
                //bikes and slots
                stationBikesUpNowArrayList.add(bikesUpNow);
                stationSlotsUpNowArrayList.add(slotsUpNow);
                stationBikesUpEstArrayList.add(bikesUpEst);
                stationSlotsUpEstArrayList.add(slotsUpEst);

                //en arrive, street, street_number
                streetDownArrayList.add(streetDown);
                streetNumberDownArrayList.add(streetNumberDown);
                //en arrive tambien, dentro de estimation, bikes, slot, station_id
                stationBikesDownNowArrayList.add(bikesDownNow);
                stationSlotsDownNowArrayList.add(slotsDownNow);
                stationBikesDownEstArrayList.add(bikesDownEst);
                stationSlotsDownEstArrayList.add(slotsDownEst);
                stationRiskArrayList.add(departure_risk);


            }


        } catch (JSONException jsone) {

            String error = jsone.getMessage();

        }


    }

    public ArrayList<Waypoints> getWaypointsArrayList() {
        return waypointsArrayList;
    }


    public class DownloadJsonData extends DownloadRawData {

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.d(LOG_DLOAD, "Calling on ProcessResult");
            //here we sotring the data into variables
            processResult();


        }

        @Override
        protected String doInBackground(String... params) {
            String[] uri = {mDestinationUri.toString()}; //because on super method below, we must enter an array of strings as input.
            return super.doInBackground(uri);
        }
    }

    public ArrayList<String> getSumTimeArrayList() {
        return sumTimeArrayList;
    }

    public ArrayList<Integer> getDistanceArrayList() {
        return distanceArrayList;
    }

    public ArrayList<String> getStreetUpArrayList() {
        return streetUpArrayList;
    }

    public ArrayList<String> getStreetDownArrayList() {
        return streetDownArrayList;
    }

    public ArrayList<String> getStreetNumberUpArrayList() {
        return streetNumberUpArrayList;
    }

    public ArrayList<String> getStreetNumberDownArrayList() {
        return streetNumberDownArrayList;
    }

    public ArrayList<String> getStationIdUpArrayList() {
        return stationIdUpArrayList;
    }

    public ArrayList<String> getStationIdDownArrayList() {
        return stationIdDownArrayList;
    }

    public ArrayList<Integer> getStationBikesUpNowArrayList() {
        return stationBikesUpNowArrayList;
    }

    public ArrayList<Integer> getStationBikesDownNowArrayList() {
        return stationBikesDownNowArrayList;
    }

    public ArrayList<Integer> getStationBikesUpEstArrayList() {
        return stationBikesUpEstArrayList;
    }

    public ArrayList<Integer> getStationBikesDownEstArrayList() {
        return stationBikesDownEstArrayList;
    }

    public ArrayList<Integer> getStationSlotsUpNowArrayList() {
        return stationSlotsUpNowArrayList;
    }

    public ArrayList<Integer> getStationSlotsDownNowArrayList() {
        return stationSlotsDownNowArrayList;
    }

    public ArrayList<Integer> getStationSlotsDownEstArrayList() {
        return stationSlotsDownEstArrayList;
    }

    public ArrayList<Integer> getStationSlotsUpEstArrayList() {
        return stationSlotsUpEstArrayList;
    }

    public ArrayList<Double> getStationRiskArrayList() {
        return stationRiskArrayList;
    }
}
