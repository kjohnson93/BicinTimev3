package app.bicintime.wolf.bicintimeactivities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.CameraPosition;

public class PlanRouteActivity extends BaseActivity implements GoogleMap.OnCameraChangeListener {


    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    public FragmentManager fragmentManager;
    private static final String LOG_TAG = "LOGTRACE";
    private static final String LOG_MAP = "LOGMAP";
    private static final String LOG_INT = "LOGINTENT";
    private static final String LOG_FLOW = "LOG_FLOW";
    private static final String default_startDate = "";
    private static final String default_endDate = "";


    private ToggleButton toggleButtonLoudiness, toggleButtonLanes;


    String startLocationData;
    String endLocationData;

    TextView textViewStart, textViewEnd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_route);

        Log.d(LOG_FLOW, "PlanRouteActivity onCreate navigation");

        agregarToolbar();
        setUpDrawer();

    }

    //Click Listener inside this method ??? good or not??. We let the user choose the toggleButton state as he desires..
    private void setUpToggleButon() {

        toggleButtonLoudiness = (ToggleButton) findViewById(R.id.toggleButton_loudiness);
        toggleButtonLanes = (ToggleButton) findViewById(R.id.toggleButton_lanes);

        SharedPreferences sharedPrefs = getSharedPreferences("MyData", MODE_PRIVATE);
        toggleButtonLoudiness.setChecked(sharedPrefs.getBoolean("Loudiness", false));

        SharedPreferences sharedPreferences = getSharedPreferences("MyData", MODE_PRIVATE);
        toggleButtonLanes.setChecked(sharedPreferences.getBoolean("Lanes", false));


        toggleButtonLoudiness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (toggleButtonLoudiness.isChecked()) {
                    SharedPreferences.Editor editor = getSharedPreferences("MyData", MODE_PRIVATE).edit();
                    editor.putBoolean("Loudiness", true);
                    editor.commit();
                } else {
                    SharedPreferences.Editor editor = getSharedPreferences("MyData", MODE_PRIVATE).edit();
                    editor.putBoolean("Loudiness", false);
                    editor.commit();
                }
            }
        });

        toggleButtonLanes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (toggleButtonLanes.isChecked()) {
                    SharedPreferences.Editor editor = getSharedPreferences("MyData", MODE_PRIVATE).edit();
                    editor.putBoolean("Lanes", true);
                    editor.commit();
                } else {
                    SharedPreferences.Editor editor = getSharedPreferences("MyData", MODE_PRIVATE).edit();
                    editor.putBoolean("Lanes", false);
                    editor.commit();
                }
            }
        });


    }

    @Override
    protected void onResume() {

        Log.d(LOG_INT, "Onresume being called");
        super.onResume();


        SharedPreferences sharedPreferences = getSharedPreferences("MyData", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        Intent intent = getIntent();

        if (intent.getIntExtra("Main", 0) == 1) {

            Log.d(LOG_INT, "Coming from main activity");

            //editor.clear();
            //editor.commit();

            Log.d(LOG_INT, "After clearing: " + sharedPreferences.getString("currentLocationStart", default_startDate));
            Log.d(LOG_INT, "After clearing: " + sharedPreferences.getString("currentLocationEnd", default_endDate));
        }
        startLocationData = sharedPreferences.getString("currentLocationStart", default_startDate);
        endLocationData = sharedPreferences.getString("currentLocationEnd", default_endDate);
        Log.d(LOG_INT, "Actual value of start: " + startLocationData);
        Log.d(LOG_INT, "Actual value of end: " + endLocationData);


        Log.d(LOG_INT, "Setting Start textview with: " + startLocationData);
        //textViewStart.setText(startLocationData.toString().trim());


        Log.d(LOG_INT, "Setting end textview with: " + endLocationData);
        //textViewEnd.setText(endLocationData.toString().trim());


        // simply go ahead and show the option to select the origin
        Intent newIntent;
        if(startLocationData.length() == 0){
            // the user has not set the starting point yet
            newIntent = new Intent(this, PlanRouteStartActivity.class);
        }else if(endLocationData.length() == 0){
            // the user has not set the destination point yet
            newIntent = new Intent(this, PlanRouteEndActivity.class);

        }else{
            editor.putString("startLocationToSelectTime", startLocationData.trim());
            editor.putString("endLocationToSelectTime", endLocationData.trim());
            editor.putString("loudinessToSelectTime", "false");
            editor.putString("lanesToSelectTime", "false");
            editor.commit();

            // set the time
            newIntent = new Intent(this, PlanSelectTimeActivity.class);

        }

        newIntent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        newIntent.putExtra("previousActivity", this.getClass().getName());
        newIntent.putExtra("previousActivityTitle", this.getTitle());
        startActivity(newIntent);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.d(LOG_INT, "OnStop being called");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.d(LOG_INT, "OnDestroy being called");
        super.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        Log.d(LOG_INT, "OnSaveInstanceState being called");
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onCameraChange(CameraPosition cameraPosition) {

        Log.d(LOG_MAP, "Entering to onCameraChange with position: " + cameraPosition);

    }
}
