package app.bicintime.wolf.bicintime;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wolf on 12/26/2015.
 */

//Fragment class that shows the main plan route section of the app, it uses several things as a recycler view and sharedpreferences to read and save things

public class PlanRouteFragment extends Fragment implements RecyclerPlanRouteAdapter.ClickListener {

    private static final String LOG_TAG = "LOGTRACE";
    private static final String LOG_BACK = "LOGBACK";
    private static final String DEFAULT = "Choose a Start";
    private static final String DEFAULT_END = "Choose a destination" ;


    RecyclerPlanRouteAdapter adapter; //this adapter is necessary because I must create a layout for the recycler view with the help of an recyclerview adapter
    RecyclerPlanRouteAdapter2 adapter2;
    RecyclerView recyclerView, recyclerView2;


    //public empty constrctor may solve the problem??
    public PlanRouteFragment() {

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.plan_route, container, false);

        Log.d(LOG_TAG, "Entering onCreateView PlanRouteFragment");
        Log.d(LOG_BACK, "Entering onCreateView PlanRouteFragment");

        LinearLayout linearLayout_start = (LinearLayout) rootView.findViewById(R.id.linearl_start);

        linearLayout_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getActivity().getApplicationContext(), "Hi!", Toast.LENGTH_SHORT).show();

                Log.d(LOG_TAG,"Entering on the clicklistener");

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.root_framelayout, new PlanRouteFragmentStartA());
                    fragmentTransaction.addToBackStack(PlanRouteFragment.class.getName());
                fragmentTransaction.commit();
                fragmentManager.executePendingTransactions();

            }
        });

        LinearLayout linearLayoutEnd = (LinearLayout) rootView.findViewById(R.id.linearl_end);
        linearLayoutEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.root_framelayout, new PlanRouteEndFragment());
                fragmentTransaction.addToBackStack(PlanRouteFragment.class.getName());
                fragmentTransaction.commit();
                fragmentManager.executePendingTransactions();

            }
        });


        return rootView;
    }

    @Override
    public void onResume() {

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        Log.d(LOG_BACK, "Entering onResume PlanRouteFragment");
        Log.d(LOG_BACK, "The backentrycount is: " + fragmentManager.getBackStackEntryCount());
        super.onResume();

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String startData = sharedPreferences.getString("StartLocation", DEFAULT);
        String endData = sharedPreferences.getString("EndLocation", DEFAULT_END);
        Log.d(LOG_TAG, "Data get is:" + startData);
        TextView textViewStart = (TextView) getView().findViewById(R.id.title_row1_2);
        TextView textViewEnd = (TextView) getView().findViewById(R.id.title_row_2_2);
        textViewStart.setText(startData);
        textViewEnd.setText(endData);
        editor.remove("StartLocation"); //Para que no quede almacenda en caso de un futuro acceso, se lee y se quita.
        editor.remove("EndLocation");
        editor.commit();


    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(LOG_BACK, "Entering onCreate PlanRouteFragment");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(LOG_BACK, "Entering onActivityCreated PlanRouteFragment");
    }

    public static List<Information> getData() {
        //With this method I am giving content to the recyclerview with the help of Information class

        Log.d("RECYCLER", "Entering at getData()");

        List<Information> data = new ArrayList<>();

        int[] icons = {R.drawable.ic_markers, R.drawable.ic_markers, R.drawable.ic_clocks};
        //int[] icons2 = {R.drawable.drawer_toggle, R.drawable.drawer_toggle, R.drawable.drawer_toggle, R.drawable.drawer_toggle};
        int[] icons2 = {R.drawable.ic_togglerbar, R.drawable.ic_togglerbar, R.drawable.ic_togglerbar};

        String[] titles = {"Current Location", "Choose Destination", "Choose a Time"};
        String[] titles2 = {"Start", "Destination", "00:00h"};

        for (int i = 0; i < titles.length && i < icons.length; i++) {

            Information current = new Information();
            current.title = titles[i];
            current.title2 = titles2[i];
            current.iconid1 = icons[i];
            current.iconid2 = icons2[i];

            data.add(current);


        }

        return  data;


    }

    public static List<Information> getData2(){



        List<Information> data = new ArrayList<>();

        int[] icons = {R.drawable.ic_hearths, R.drawable.ic_sounds, R.drawable.ic_lanes};
        //int[] icons2 = {R.drawable.drawer_toggle, R.drawable.drawer_toggle, R.drawable.drawer_toggle, R.drawable.drawer_toggle};
        int[] icons2 = {R.drawable.ic_togglerbar, R.drawable.ic_togglerbar, R.drawable.ic_togglerbar};

        String[] titles = {"Fitness Level", "Loudiness ?", "Bike lane ?"};
        String[] titles2 = {"Very Strong", "Don't mind", "Don't mind"};

        for (int i = 0; i < titles.length && i < icons.length; i++) {

            Information current = new Information();
            current.title = titles[i];
            current.title2 = titles2[i];
            current.iconid1 = icons[i];
            current.iconid2 = icons2[i];

            data.add(current);


        }

        return  data;



    }

    //BACK BUTTON MAKE APP TO DESTROY /CLOSE

    @Override
    public void itemClicked(View view, int position) {

        Log.d("RECYCLER","ItemCLick on PlanRoute called with position: " + position);


    }



}
