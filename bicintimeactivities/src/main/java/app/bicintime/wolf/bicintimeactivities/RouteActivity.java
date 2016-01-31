package app.bicintime.wolf.bicintimeactivities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

public class RouteActivity extends BaseActivity {

    private static final String DEFAULT = "Default text";
    private static final String LOG_TIME =  "LOGTIME";
    String startLocation, endLocation, loudiness, lanes, timeSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route);

        agregarToolbar();
        setUpDrawer();

        getSharedPreferencesData();

    }

    private void getSharedPreferencesData() {

        SharedPreferences sharedPreferences = getSharedPreferences("MyData", MODE_PRIVATE);
        startLocation = sharedPreferences.getString("startLocationToRoute", DEFAULT);
        endLocation = sharedPreferences.getString("endLocationToRoute", DEFAULT);
        loudiness = sharedPreferences.getString("loudinessToRoute", DEFAULT);
        lanes = sharedPreferences.getString("lanesToRoute", DEFAULT);
        timeSelected = sharedPreferences.getString("timeToRoute", DEFAULT);


        Log.d(LOG_TIME, "Im getting the next data (RouteActivity): " + startLocation + "\n" + endLocation + "\n" + loudiness + "\n" + lanes + "\n" + timeSelected);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();

    }

    @Override
    public void onBackPressed() {

        Intent intent = new Intent(this, PlanSelectTimeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);

    }
}
