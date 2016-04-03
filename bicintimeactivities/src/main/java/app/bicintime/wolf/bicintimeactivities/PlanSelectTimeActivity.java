package app.bicintime.wolf.bicintimeactivities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

//On this activity, we force the user to pick the time
public class PlanSelectTimeActivity extends BaseActivity {


    private static final String LOG_TIME = "LOGTIME";
    private static final String DEFAULT = "Default text";
    private static final String LOG_FLOW = "LOG_FLOW";
    private String startLocation, endLocation, loudiness, lanes;
    String timeNow, timeHalfHour, timeOneHour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_select_time);

        Log.d(LOG_FLOW, "PlanSelectTime onCreate navigation");



        agregarToolbar();
        setUpDrawer();
        setTitle("Select the time");

        setUpTime();
        getSharedPreferencesData();

        //We are getting the references into java object code from the xml layout or display file
        LinearLayout linearLayoutNow = (LinearLayout) findViewById(R.id.linearl_now);
        LinearLayout linearLayoutHalfHour = (LinearLayout) findViewById(R.id.linearl_halfhour);
        LinearLayout linearLayoutOneHour = (LinearLayout) findViewById(R.id.linearl_onehour);
        LinearLayout linearLayoutCustom = (LinearLayout) findViewById(R.id.linearl_custom);

        //Here we define what happens if we click this section of the screen. In this case, pass the time info along the previous data to the Route Map Screen
        linearLayoutNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences sharedPreferences = getSharedPreferences("MyData", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.putString("startLocation", startLocation);
                editor.putString("endLocation", endLocation);
                editor.putString("loudiness", loudiness);
                editor.putString("lanes", lanes);
                editor.putString("timeSelected", timeNow);
                editor.commit();

//                Intent intent = new Intent(PlanSelectTimeActivity.this, RouteSelectionActivity.class);

                Intent intent = new Intent(PlanSelectTimeActivity.this, RouteSelectionActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);

            }
        });

        //In this case, pass the time info along the previous data to the Route Map Screen
        linearLayoutHalfHour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences sharedPreferences = getSharedPreferences("MyData", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.putString("startLocation", startLocation);
                editor.putString("endLocation", endLocation);
                editor.putString("loudiness", loudiness);
                editor.putString("lanes", lanes);
                editor.putString("timeSelected", timeHalfHour);
                editor.commit();

//                Intent intent = new Intent(PlanSelectTimeActivity.this, RouteSelectionActivity.class);

                Intent intent = new Intent(PlanSelectTimeActivity.this, RouteSelectionActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);
            }
        });

        //In this case, pass the time info along the previous data to the Route Map Screen
        linearLayoutOneHour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences sharedPreferences = getSharedPreferences("MyData", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.putString("startLocationToRoute", startLocation);
                editor.putString("endLocationToRoute", endLocation);
                editor.putString("loudinessToRoute", loudiness);
                editor.putString("lanesToRoute", lanes);
                editor.putString("timeSelected", timeOneHour);
                editor.commit();

                Intent intent = new Intent(PlanSelectTimeActivity.this, RouteSelectionActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);
            }
        });

        //In this case, pass the time info along the previous data to the Route Map Screen
        linearLayoutCustom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences sharedPreferences = getSharedPreferences("MyData", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.putString("startLocationToRoute", startLocation);
                editor.putString("endLocationToRoute", endLocation);
                editor.putString("loudinessToRoute", loudiness);
                editor.putString("lanesToRoute", lanes);
                editor.putString("timeSelected", timeNow);  //TODO Make a dialog fragment to choose an specific time. !
                editor.commit();

                Intent intent = new Intent(PlanSelectTimeActivity.this, RouteSelectionActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);

            }
        });


    }

    //With this method, we get the data coming from PlanRoute screen
    private void getSharedPreferencesData() {

        SharedPreferences sharedPreferences = getSharedPreferences("MyData", MODE_PRIVATE);
        startLocation = sharedPreferences.getString("currentLocationStart", DEFAULT);
        endLocation = sharedPreferences.getString("currentLocationEnd", DEFAULT);
        loudiness = sharedPreferences.getString("loudinessToSelectTime", DEFAULT);
        lanes = sharedPreferences.getString("lanesToSelectTime", DEFAULT);


        Log.d(LOG_TIME, "Im getting the next data (PlanSelectTime): " + startLocation + "\n" + endLocation + "\n" + loudiness + "\n" + lanes);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.commit();


    }

    //With this method we do some parsing to get a miliseconds timestamp format for our four options to choose on the screen.
    private void setUpTime() {

        TextView textViewNow, textViewHalfHour, textViewOneHour, textViewCustom;

        textViewNow = (TextView) findViewById(R.id.textviewTime1);
        textViewHalfHour = (TextView) findViewById(R.id.textviewTime2);
        textViewOneHour = (TextView) findViewById(R.id.textviewTime3);
        textViewCustom = (TextView) findViewById(R.id.textViewTime4);

        Calendar c;
        c = Calendar.getInstance();
        Date date = c.getTime();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        //timeNow = simpleDateFormat.format(date);

        Long nowLong = System.currentTimeMillis();

        timeNow = nowLong.toString();

        final Long halfHourLong = Long.valueOf((30 * 60) * 1000);
        Long timeHalfHourLong = nowLong + halfHourLong;
        timeHalfHour = simpleDateFormat.format(new Date(timeHalfHourLong)).toString();

        final Long oneHourLong = Long.valueOf((60 * 60) * 1000);
        Long timeOneHourLong = nowLong + oneHourLong;
        timeOneHour = simpleDateFormat.format(new Date(timeOneHourLong)).toString();

        Log.d(LOG_TIME, "Time formmatted to: " + timeNow + "\n" + "Plus half an hour: " + timeHalfHour);

        textViewNow.setText("Now");
        textViewHalfHour.setText("In half an hour\n(at " + timeHalfHour + ")");
        textViewOneHour.setText("In one hour\n(at " + timeOneHour + ")");
        textViewCustom.setText("Choose your time");

    }

    //With thise method, we can navigate back to the previous screen
    @Override
    public void onBackPressed() {

        Intent intent = new Intent(this, PlanRouteStartActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(LOG_TIME, "OnResume being called from planselectTimeActivity");
    }
}
