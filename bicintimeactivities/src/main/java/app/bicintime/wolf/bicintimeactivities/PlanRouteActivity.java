package app.bicintime.wolf.bicintimeactivities;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;

public class PlanRouteActivity extends BaseActivity {


    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    public FragmentManager fragmentManager;
    private static final String LOG_TAG = "LOGTRACE";

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
}
