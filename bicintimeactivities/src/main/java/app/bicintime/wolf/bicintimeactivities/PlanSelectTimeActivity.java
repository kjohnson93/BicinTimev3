package app.bicintime.wolf.bicintimeactivities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class PlanSelectTimeActivity extends BaseActivity {


    RecyclerView recyclerView;
    RecyclerViewTimeAdapter timeAdapter;
    private static final String LOG_TIME = "LOGTIME";
    private static final String DEFAULT = "Default text";
    private String startLocation, endLocation, loudiness, lanes;
    String timeNow, timeHalfHour, timeOneHour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_select_time);


        agregarToolbar();
        setUpDrawer();

        //recyclerView = (RecyclerView) findViewById(R.id.recyclerViewTime);
        //timeAdapter = new RecyclerViewTimeAdapter(this, getData());

        //recyclerView.setAdapter(timeAdapter);
        //recyclerView.setLayoutManager(new LinearLayoutManager(this));

        setUpTime();
        getSharedPreferencesData();

        LinearLayout linearLayoutNow = (LinearLayout) findViewById(R.id.linearl_now);
        LinearLayout linearLayoutHalfHour = (LinearLayout) findViewById(R.id.linearl_halfhour);
        LinearLayout linearLayoutOneHour = (LinearLayout) findViewById(R.id.linearl_onehour);
        LinearLayout linearLayoutCustom = (LinearLayout) findViewById(R.id.linearl_custom);

        linearLayoutNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences sharedPreferences = getSharedPreferences("MyData", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.putString("startLocationToRoute", startLocation);
                editor.putString("endLocationToRoute", endLocation);
                editor.putString("loudinessToRoute", loudiness);
                editor.putString("lanesToRoute", lanes);
                editor.putString("timeToRoute",timeNow );
                editor.commit();

                Intent intent = new Intent(PlanSelectTimeActivity.this, RouteActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);

            }
        });

        linearLayoutHalfHour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences sharedPreferences = getSharedPreferences("MyData", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.putString("startLocationToRoute", startLocation);
                editor.putString("endLocationToRoute", endLocation);
                editor.putString("loudinessToRoute", loudiness);
                editor.putString("lanesToRoute", lanes);
                editor.putString("timeToRoute",timeHalfHour );
                editor.commit();

                Intent intent = new Intent(PlanSelectTimeActivity.this, RouteActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);
            }
        });

        linearLayoutOneHour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences sharedPreferences = getSharedPreferences("MyData", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.putString("startLocationToRoute", startLocation);
                editor.putString("endLocationToRoute", endLocation);
                editor.putString("loudinessToRoute", loudiness);
                editor.putString("lanesToRoute", lanes);
                editor.putString("timeToRoute",timeOneHour );
                editor.commit();

                Intent intent = new Intent(PlanSelectTimeActivity.this, RouteActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);
            }
        });

        linearLayoutCustom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences sharedPreferences = getSharedPreferences("MyData", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.putString("startLocationToRoute", startLocation);
                editor.putString("endLocationToRoute", endLocation);
                editor.putString("loudinessToRoute", loudiness);
                editor.putString("lanesToRoute", lanes);
                editor.putString("timeToRoute",timeNow );  //TODO Make a dialog fragment to choose an specific time. !
                editor.commit();

                Intent intent = new Intent(PlanSelectTimeActivity.this, RouteActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);

            }
        });







    }

    private void getSharedPreferencesData() {

        SharedPreferences sharedPreferences = getSharedPreferences("MyData", MODE_PRIVATE);
        startLocation = sharedPreferences.getString("startLocationToSelectTime", DEFAULT);
        endLocation = sharedPreferences.getString("endLocationToSelectTime", DEFAULT);
        loudiness = sharedPreferences.getString("loudinessToSelectTime", DEFAULT);
        lanes = sharedPreferences.getString("lanesToSelectTime", DEFAULT);


        Log.d(LOG_TIME, "Im getting the next data (PlanSelectTime): " + startLocation + "\n" + endLocation + "\n" + loudiness + "\n" + lanes);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();





    }

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
        timeNow = simpleDateFormat.format(date);

        Calendar calHalfHour = Calendar.getInstance();
        calHalfHour.setTime(date);
        calHalfHour.add(Calendar.MINUTE, 30);
        timeHalfHour = simpleDateFormat.format(calHalfHour.getTime());

        Calendar calOneHour = Calendar.getInstance();
        calOneHour.setTime(date);
        calOneHour.add(Calendar.HOUR, 1);
        timeOneHour = simpleDateFormat.format(calOneHour.getTime());

        Log.d(LOG_TIME, "Time formmatted to: " + timeNow + "\n" + "Plus half an hour: " + timeHalfHour);





        textViewNow.setText("Now");
        textViewHalfHour.setText("In half an hour: " + timeHalfHour);
        textViewOneHour.setText("In one hour: " + timeOneHour);
        textViewCustom.setText("Choose your time");

    }

    public List<Time> getData() {

        List<Time> timeList = new ArrayList<>();


        Calendar c;
        c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR);
        int minute = c.get(Calendar.MINUTE);

        Time now = new Time(hour,minute);

        Time halfHour = new Time(hour+ 0, minute + 30);
        Time oneHour = new Time(hour+1, minute+ 0);
        Time custom = new Time(0,0);

        timeList.add(now);
        timeList.add(halfHour);
        timeList.add(oneHour);
        timeList.add(custom);

        return timeList;


    }

    class Time {

        int hour, minute;

        public Time(int hour, int minute) {

            this.hour = hour;
            if(minute+30>60){
                this.hour++;
                this.minute = this.minute -60;

            }
            else this.minute = minute;



        }

    }

    @Override
    public void onBackPressed() {

        Intent intent = new Intent(this, PlanRouteActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);

    }
}
