package app.bicintime.wolf.bicintimeactivities;

import android.net.Uri;
import android.util.Log;

/**
 * Created by wolf on 2/2/2016.
 */
public class GetBicinTimeJsonData extends GetRawData {

    private static final String LOG_DLOAD = "LOGDOWNLOAD";
    private Uri mDestinationUri;
    String origin, destination, time;

    public GetBicinTimeJsonData(String origin, String destination, String time) {
        super(null);
        this.origin = origin;
        this.destination = destination;
        this.time = time;
        CreateUri();
        
    }

    private Boolean CreateUri() {

        final String BICINTIME_BASE_URL = "http://bicing.h2o.net.br/getroute.php";
        final String PARAM_ORIGIN = "origin";
        final String PARAM_DESTINATION = "destination";
        final String PARAM_TIME = "time";

        mDestinationUri = Uri.parse(BICINTIME_BASE_URL).buildUpon().appendQueryParameter(PARAM_ORIGIN, origin).appendQueryParameter(PARAM_DESTINATION, destination).appendQueryParameter(PARAM_TIME, time).
                build();

        Log.d(LOG_DLOAD, "My uri is: " + mDestinationUri);


        return mDestinationUri != null;

    }
}
