package app.bicintime.wolf.bicintimeactivities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class PlanRouteEndActivity extends BaseActivity {

    private static final String LOG_INT = "LOGINTENT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_route_end);

        agregarToolbar();
        setUpDrawer();

        LinearLayout linearlChooseOnMap, linearlCurrentLoc;

        linearlChooseOnMap = (LinearLayout) findViewById(R.id.linearl_choose_onmap);
        linearlCurrentLoc = (LinearLayout) findViewById(R.id.linearl_current_location);
        final TextView textViewEnd = (TextView) findViewById(R.id.textview_coordinates_current_end);


        linearlChooseOnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(PlanRouteEndActivity.this, MapsActivity.class);
                intent.putExtra("previousActivity", PlanRouteEndActivity.class.getName());
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);



            }
        });

        linearlCurrentLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //String data_coord = textView.getText().toString().trim();

                SharedPreferences sharedPreferences = getSharedPreferences("MyData", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("currentLocationEnd", textViewEnd.getText().toString().trim());
                editor.commit();

                Intent intent = new Intent(PlanRouteEndActivity.this, PlanRouteActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                intent.putExtra("previousActivity", PlanRouteEndActivity.class.getName());
                startActivity(intent);


            }
        });


    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, PlanRouteActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
    }
}
