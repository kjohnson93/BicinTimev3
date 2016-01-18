package app.bicintime.wolf.bicintimeactivities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

public class BaseActivity extends AppCompatActivity {

    protected DrawerLayout drawerLayout;
    protected NavigationView navigationView;
    protected FragmentManager fragmentManager;


    private static final String LOG_TAG = "LOGTRACE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);







    }

    protected void setUpDrawer(){

        Log.d(LOG_TAG, "Entering setUpDrawer");

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);

        if(navigationView!=null){
            Log.d(LOG_TAG, "Entering inside if onCreate");
            prepararDrawer(navigationView);


            // Seleccionar item por defecto. Este el fragmento que se abre. cuando se abre la app
            //  seleccionarItem(navigationView.getMenu().getItem(0));
        }

        //fragmentManager.addOnBackStackChangedListener(getListener());



    }


    protected void agregarToolbar() {

        Log.d(LOG_TAG, "Entering agregarToolbar");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ActionBar ab = getSupportActionBar();
        if (ab != null) {
            // Poner ícono del drawer toggle
            ab.setHomeAsUpIndicator(R.drawable.drawer_toggle);
            ab.setDisplayHomeAsUpEnabled(true);
        }

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
                startActivity(intent);
                break;
            case R.id.id_drawer_plan:
                Intent intent2 = new Intent(this, PlanRouteActivity.class);
                startActivity(intent2);
                break;
            case R.id.id_drawer_bikes:

                break;
        }

    }

    @Override
    public void onBackPressed() {

super.onBackPressed();
    }
}
