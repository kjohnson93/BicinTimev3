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
    private Uri mDestinationUri;
    String origin, destination, time;
    private ArrayList<Route> routes = new ArrayList<>();

    public GetBicinTimeJsonData(String origin, String destination, String time) {
        super(null);
        this.origin = origin;
        this.destination = destination;
        this.time = time;
        CreateUri();

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


        Log.d(LOG_DLOAD, "My uri is: " + mDestinationUri);


        return mDestinationUri != null;

    }

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
                String bikes = start.getString("bikes");
                String slots = start.getString("slots");
                String latitude = start.getString("latitude");
                String longitude = start.getString("longitude");
                String street = start.getString("street");
                String street_number = start.getString("street_number");

                Log.d(LOG_DLOAD, "Data inside these objects: \n " + "bikes: " + stationid + " bikes: " + bikes + " slots: " + slots);

                JSONObject estimation = start.getJSONObject("estimation");

                String bikes_est = estimation.getString("bikes");
                String slots_est = estimation.getString("slots");

                JSONObject risk_est = estimation.getJSONObject("risk");

                String departure = risk_est.getString("departure");
                String destination = risk_est.getString("destination");


                JSONObject arrive = jsonStation.getJSONObject("arrive");

                String stationid_arr = arrive.getString("station_id");
                String bikes_arr = arrive.getString("bikes");
                String slots_arr = arrive.getString("slots");
                String latitude_arr = arrive.getString("latitude");
                String longitude_arr = arrive.getString("longitude");
                String street_arr = arrive.getString("street");
                String street_number_arr = arrive.getString("street_number");

                JSONObject estimation_arr = arrive.getJSONObject("estimation");
                String bikes_arr_est = estimation_arr.getString("bikes");
                String slots_arr_est = estimation_arr.getString("slots");

                JSONObject risk_arr_est = estimation_arr.getJSONObject("risk");
                String departure_arr_est = risk_arr_est.getString("departure");
                String destination_arr_est = risk_arr_est.getString("destination");

                Log.d(LOG_DLOAD, "Length of jsonarray is: " + jsonArray.length()); //not getting to this point


                JSONObject distance = jsonRoute.getJSONObject("distance");
                String depToStation = distance.getString("depToStation");
                String bicing = distance.getString("bicing");
                String stationToDest = distance.getString("stationToDest");
                String sum = distance.getString("sum");

                JSONObject time = jsonRoute.getJSONObject("time");
                String depToStation_time = time.getString("depToStation");
                String bicing_time = time.getString("bicing");
                String stationToDest_time = time.getString("stationToDest");
                String sum_time = time.getString("sum");

                JSONObject print = jsonRoute.getJSONObject("print");
                String sumTime = print.getString("sumTime");

                JSONObject risk = jsonRoute.getJSONObject("risk");
                String departure_risk = risk.getString("departure");
                String destination_risk = risk.getString("destination");


            }


        } catch (JSONException jsone) {

        }


    }


    public class DownloadJsonData extends DownloadRawData {

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.d(LOG_DLOAD, "Calling on ProcessResult");
            processResult();
        }

        @Override
        protected String doInBackground(String... params) {
            return super.doInBackground(params);
        }
    }
}
