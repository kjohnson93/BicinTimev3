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
import android.widget.Button;
import android.widget.LinearLayout;
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
    private static final String default_startDate = "Choose a Start";
    private static final String default_endDate = "Choose a Destination";


    private ToggleButton toggleButtonLoudiness, toggleButtonLanes;

    TextView textViewStart, textViewEnd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_route);

        agregarToolbar();
        setUpDrawer();


        LinearLayout linearLayout_start = (LinearLayout) findViewById(R.id.linearl_start);
        LinearLayout linearLayoutEnd = (LinearLayout) findViewById(R.id.linearl_end);
        LinearLayout linearLayoutTime = (LinearLayout) findViewById(R.id.linearl_time);
        LinearLayout linearLayoutFitness = (LinearLayout) findViewById(R.id.linearl_fitness);
        textViewStart = (TextView) findViewById(R.id.title_row1_2);
        textViewEnd = (TextView) findViewById(R.id.title_row_2_2);

        Button buttonSelectTime = (Button) findViewById(R.id.button_to_select_time);

        setUpToggleButon();


        linearLayout_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(PlanRouteActivity.this, PlanRouteStartActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);


            }
        });


        linearLayoutEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(PlanRouteActivity.this, PlanRouteEndActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);

            }
        });

        linearLayoutTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PlanRouteActivity.this, ClockActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);
            }
        });

        linearLayoutFitness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PlanRouteActivity.this, PlanFitnessActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);
            }
        });

        buttonSelectTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences sharedPreferences = getSharedPreferences("MyData", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                String startLocation;
                String endLocation;
                String loudiness, lanes;

                startLocation = textViewStart.getText().toString().trim();
                endLocation = textViewEnd.getText().toString().trim();

                ToggleButton toggleButtonLoudiness = (ToggleButton) findViewById(R.id.toggleButton_loudiness);
                ToggleButton toggleButtonLanes = (ToggleButton) findViewById(R.id.toggleButton_lanes);

                Boolean isLoud = toggleButtonLoudiness.isChecked(); //????
                Boolean isLanes = toggleButtonLanes.isChecked();

                editor.putString("startLocationToSelectTime", startLocation);
                editor.putString("endLocationToSelectTime", endLocation);
                editor.putString("loudinessToSelectTime", isLoud.toString().trim());
                editor.putString("lanesToSelectTime", isLanes.toString().trim());
                editor.commit();

                Log.d(LOG_INT, "What I'm sending: " + startLocation + "\n" + endLocation + "\n" + isLoud.toString().trim() + "\n" + isLanes.toString().trim());

                Intent intent = new Intent(PlanRouteActivity.this, PlanSelectTimeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);
            }
        });


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

            editor.clear();
            editor.commit();

            Log.d(LOG_INT, "After clearing: " + sharedPreferences.getString("currentLocationStart", default_startDate));
            Log.d(LOG_INT, "After clearing: " + sharedPreferences.getString("currentLocationEnd", default_endDate));
        }
        String startLocationData = sharedPreferences.getString("currentLocationStart", default_startDate);
        String endLocationData = sharedPreferences.getString("currentLocationEnd", default_endDate);
        Log.d(LOG_INT, "Actual value of start: " + startLocationData);
        Log.d(LOG_INT, "Actual value of end: " + endLocationData);


        Log.d(LOG_INT, "Setting Start textview with: " + startLocationData);
        textViewStart.setText(startLocationData.toString().trim());


        Log.d(LOG_INT, "Setting end textview with: " + endLocationData);
        textViewEnd.setText(endLocationData.toString().trim());

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
