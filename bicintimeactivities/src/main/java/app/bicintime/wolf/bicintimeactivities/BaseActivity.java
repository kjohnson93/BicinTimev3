package app.bicintime.wolf.bicintimeactivities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

//This class groups similar behaviour shared among activities such as setting the toolbar and navigation drawer.
public class BaseActivity extends AppCompatActivity {

    Toolbar toolbar;

    protected DrawerLayout drawerLayout;
    protected ActionBarDrawerToggle mDrawerToggle;
    protected NavigationView navigationView;
    protected FragmentManager fragmentManager;


    private static final String LOG_TAG = "LOGTRACE";
    private static final String LOG_FLOW = "LOG_FLOW";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        Log.d(LOG_FLOW, "BaseActivity onCreate navigation");


    }

    protected void agregarToolbar() {

        Log.d(LOG_TAG, "Entering agregarToolbar");


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ActionBar ab = getSupportActionBar();
        if (ab != null) {
            // Poner ícono del drawer toggle
            ab.setHomeAsUpIndicator(R.drawable.drawer_toggle);
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setHomeButtonEnabled(true);
        }

    }

    protected void setUpDrawer() {

        Log.d(LOG_TAG, "Entering setUpDrawer");

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);

        if (navigationView != null) {
            Log.d(LOG_TAG, "Entering inside if onCreate");
            prepararDrawer(navigationView);

            // Seleccionar item por defecto. Este el fragmento que se abre. cuando se abre la app
            //seleccionarItem(navigationView.getMenu().getItem(0));
        }
        //fragmentManager.addOnBackStackChangedListener(getListener());

        //this object is needed to open the nav drawer by pressing burger button. Note that it needs a toolbar as argument, different froem what the documentation says.
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                drawerLayout,toolbar,        /* DrawerLayout object */
                R.string.drawer_open,  /* "open drawer" description */
                R.string.drawer_close  /* "close drawer" description */
        ) {


            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                //getApplicationContext().invalidateOptionsMenu();
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                //getSupportActionBar().setTitle("33333");
            }
        };

        //this piece of code is needed to make possible for the user to open the nav drawer by clicking the home button
        if (drawerLayout != null) {
            Log.d(LOG_TAG, "DrawerLayout not null");
            drawerLayout.setDrawerListener(mDrawerToggle);

            drawerLayout.post(new Runnable() {
                @Override
                public void run() {
                    mDrawerToggle.syncState();
                }
            });

        }


    }

    protected void openDrawer() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        // open drawer by default
        drawerLayout.openDrawer(navigationView);

    }


    protected void prepararDrawer(NavigationView navigationView) {

        Log.d(LOG_TAG, "Entering prepararDrawer");
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
                        seleccionarItem(menuItem);
                        drawerLayout.closeDrawers();
                        return true;
                    }
                });


    }

    protected void seleccionarItem(MenuItem itemDrawer) {

        Log.d(LOG_TAG, "Entering seleccionarItem");

        // Setear título actual
        setTitle(itemDrawer.getTitle());

        switch (itemDrawer.getItemId()) {

            case R.id.id_drawer_map:
                Intent intent = new Intent(this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);
                break;
            case R.id.id_drawer_plan:
                Intent intent2 = new Intent(this, PlanRouteStartActivity.class);
                intent2.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent2);
                break;
            case R.id.id_drawer_bikes:

                break;
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

        Log.d(LOG_TAG, "Entering onBackPressend on baseactivity");

        super.onBackPressed();
    }
}
