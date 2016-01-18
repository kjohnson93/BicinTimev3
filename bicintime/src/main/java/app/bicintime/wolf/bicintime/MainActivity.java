package app.bicintime.wolf.bicintime;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;



public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    public FragmentManager fragmentManager;

    private static final String LOG_TAG = "LOGTRACE";
    private static final String LOG_BACK = "LOGBACK";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        agregarToolbar();

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);

        if(navigationView!=null){
            prepararDrawer(navigationView);
            // Seleccionar item por defecto. Este el fragmento que se abre. cuando se abre la app
            seleccionarItem(navigationView.getMenu().getItem(0));
        }

        fragmentManager.addOnBackStackChangedListener(getListener());

    }

    private void agregarToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ActionBar ab = getSupportActionBar();
        if (ab != null) {
            // Poner ícono del drawer toggle
            ab.setHomeAsUpIndicator(R.drawable.drawer_toggle);
            ab.setDisplayHomeAsUpEnabled(true);
        }

    }

    private void prepararDrawer(NavigationView navigationView) {
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

    private void seleccionarItem(MenuItem itemDrawer) {
        Fragment fragmentoGenerico = null;
         fragmentManager = getSupportFragmentManager();

        setTitle(itemDrawer.getTitle());

        switch (itemDrawer.getItemId()) {

            case R.id.id_drawer_map:
                fragmentoGenerico = new MapFragment(); ;
                break;
            case R.id.id_drawer_plan:
                fragmentoGenerico =  new PlanRouteFragment() ;
                break;
            case R.id.id_drawer_bikes:
                fragmentoGenerico = new YellowFragment();
                break;
        }
        if (fragmentoGenerico != null) {


            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction(); //todo surge de aqui, el Add to backstack es el que crea este fragmento de más...
            //fragmentTransaction.addToBackStack(MainActivity.class.getName());
            fragmentTransaction.add(R.id.contenedor_principal, fragmentoGenerico);
            fragmentTransaction.commit();  //dentro de mapfragment debería crear este commit!!

        }

        // Setear título actual

    }

    private FragmentManager.OnBackStackChangedListener getListener()
    {
        FragmentManager.OnBackStackChangedListener result = new FragmentManager.OnBackStackChangedListener()
        {
            public void onBackStackChanged()
            {
                FragmentManager manager = getSupportFragmentManager();

                if (manager != null)
                {

                    Log.d(LOG_BACK, "Hello your backstack has been changed !!");

                    Fragment fragment = fragmentManager.getFragments().get(fragmentManager.getBackStackEntryCount());

                    Log.d(LOG_BACK, "I am the fragment: " + fragment.toString());

                    fragment.onResume(); //This is being called twice, after Fragment's creation and when the backstack changes


                }
            }
        };

        return result;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //TODO MainActivity counts as an entry on the backstack, my plan is to let the map fragment to close the app and not to travel to mainactivty blank screen
    //TODO Also I have to solve or make the right arrangement to provide a correct back and forth navigation through the app.
    //TODO'S DONE
    @Override
    public void onBackPressed() {
        //Log.d(LOG_TAG, "Entering onBackPressed MainActivity with Backstackentrycounty: " + fragmentManager.getBackStackEntryCount() );

        if(fragmentManager.getBackStackEntryCount()==0){
            this.finish();
        }
        else getSupportFragmentManager().popBackStack();//this single line makes the whole navigation go the right way,on the fragment's screen back button pressed execute this, so simple
    }

}
