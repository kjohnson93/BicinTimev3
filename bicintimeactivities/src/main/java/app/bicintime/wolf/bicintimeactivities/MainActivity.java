package app.bicintime.wolf.bicintimeactivities;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;

//AKA MapActivity
public class MainActivity extends BaseActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    public FragmentManager fragmentManager;
    private static final String LOG_TAG = "LOGTRACE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        agregarToolbar();
        setUpDrawer();

        // open drawer by default
        openDrawer();

    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();

    }
}
