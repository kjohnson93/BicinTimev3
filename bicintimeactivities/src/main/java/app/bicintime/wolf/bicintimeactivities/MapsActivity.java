package app.bicintime.wolf.bicintimeactivities;

import android.content.Intent;
import android.os.Bundle;

public class MapsActivity extends BaseActivity {

    private static final String FRAGMENT_KEY = "test";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        agregarToolbar();
        setUpDrawer();



    }

    @Override
    public void onBackPressed() {

        Intent intent = getIntent();
        String previousActivity = intent.getStringExtra("previousActivity");

        if(previousActivity.equals(PlanRouteStartActivity.class.getName())){

            Intent intent1 = new Intent(this, PlanRouteStartActivity.class);
            intent1.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(intent1);


        }

        else if (previousActivity.equals(PlanRouteEndActivity.class.getName())){

            Intent intent1 = new Intent(this, PlanRouteEndActivity.class);
            intent1.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(intent1);

        }

    }
}
