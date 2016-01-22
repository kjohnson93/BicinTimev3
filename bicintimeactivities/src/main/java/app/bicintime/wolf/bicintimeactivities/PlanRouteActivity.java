package app.bicintime.wolf.bicintimeactivities;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.CameraPosition;

public class PlanRouteActivity extends BaseActivity implements GoogleMap.OnCameraChangeListener {


    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    public FragmentManager fragmentManager;
    private static final String LOG_TAG = "LOGTRACE";
    private static final String LOG_MAP = "LOGMAP";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_route);

        agregarToolbar();
        setUpDrawer();





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
