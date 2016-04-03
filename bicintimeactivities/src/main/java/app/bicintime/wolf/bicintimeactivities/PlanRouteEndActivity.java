package app.bicintime.wolf.bicintimeactivities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class PlanRouteEndActivity extends BaseActivity {

    private static final String LOG_INT = "LOGINTENT";
    private static final String LOG_FLOW = "LOG_FLOW";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_route_end);

        agregarToolbar();
        setUpDrawer();
        setTitle("Choose a destination");

        Log.d(LOG_FLOW, "PlanRouteENd onCreate navigation");

        // simply go ahead and show the map
        Intent intent = new Intent(this, MapsActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        intent.putExtra("previousActivity", this.getClass().getName());
        intent.putExtra("previousActivityTitle", this.getTitle());
        startActivity(intent);

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, PlanRouteStartActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
    }
}
