package app.bicintime.wolf.bicintimeactivities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class PlanRouteStartActivity extends BaseActivity{

    private static final String LOG_INT ="LOGINTENT";

    private static final String LOG_TAG = "LOGTRACE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_route_start);

        agregarToolbar();
        setUpDrawer();


        LinearLayout linearlChooseOnMap, linearlCurrentLoc;

        linearlChooseOnMap = (LinearLayout)findViewById(R.id.linearl_choose_onmap);
        linearlCurrentLoc= (LinearLayout)findViewById(R.id.linearl_current_location);
        final TextView textViewStart = (TextView) findViewById(R.id.textview_coordinates_current);



        linearlChooseOnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(LOG_TAG, "Entering onClick with view id: " + v.getId());

                Intent intent = new Intent(PlanRouteStartActivity.this, MapsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                intent.putExtra("previousActivity", PlanRouteStartActivity.class.getName());
                startActivity(intent);


            }
        });

        linearlCurrentLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences sharedPreferences = getSharedPreferences("MyData", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("currentLocationStart", textViewStart.getText().toString());
                editor.commit();

                Intent intent_loc = new Intent(PlanRouteStartActivity.this, PlanRouteActivity.class);
                intent_loc.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                intent_loc.putExtra("previousActivity", PlanRouteStartActivity.class.getName());
                startActivity(intent_loc);


            }
        });

    }

    @Override
    public void onBackPressed() {

        Log.d(LOG_TAG, "Entering onBackPresssend planroutestartactivity");

        Intent intent = new Intent(this, PlanRouteActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);


    }
}
