package app.bicintime.wolf.bicintime;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wolf on 12/26/2015.
 */

//Fragment class that shows the main plan route section of the app, it uses several things as a recycler view and sharedpreferences to read and save things

public class PlanRouteFragment extends Fragment implements RecyclerPlanRouteAdapter.ClickListener {


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

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_id); //getting the recyclerview into java code
        //recyclerView2 = (RecyclerView) rootView.findViewById(R.id.recycler_id2);
        adapter = new RecyclerPlanRouteAdapter(getActivity(), getData()); //passing the context and some resources(or data) to the adapter
        //adapter2 = new RecyclerPlanRouteAdapter2(getActivity(), getData2());
        recyclerView.setHasFixedSize(true); //I have to check what exactly does this

        //adapter2.setClickListener2(this);
        adapter.setClickListener(this);  //means that this activity will handle the clicks on the recycler
        recyclerView.setAdapter(adapter); //This step is mandatory
        //recyclerView2.setAdapter(adapter2);
        //remember layout manager...

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity())); //this step is also mandatory
        //recyclerView2.setLayoutManager(new LinearLayoutManager(getActivity()));

        Button button_plan_route = (Button) rootView.findViewById(R.id.buttonPlanRoute3);

        button_plan_route.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.addToBackStack(PlanRouteFragment.class.getName());//I added it to the backstack with a TAG otherwise will not be saved at the backstack
                Log.d("BACK", "Entrando a PlanRouteFragmentt");

                YellowFragment yellowFragment = new YellowFragment();
                BlackFragment blackFragment = new BlackFragment();


                //fragmentTransaction.replace(R.id.rootB_framelayout, sentFragment).commit();
                fragmentTransaction.replace(R.id.root_framelayout, yellowFragment).commit();
                fragmentManager.executePendingTransactions();


            }
        });



        /*
        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                return e.getAction() == MotionEvent.ACTION_MOVE;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });
        */

        //Have a problem with recycler view scrolling, i guess i would resolve it when  I learn more about touch events


        /*
        Button b = (Button) rootView.findViewById(R.id.buttonPlanRoute);  //using this button test to navigate to another fragment

        b.setText("Changed hahahaha");

        Log.d("BUTTON", "ENTERING #1");

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("BUTTON", "ENTERING #2");

                //SentFragment sentFragment = new SentFragment();
                PlanRouteFragmentStartA planRouteFragmentStartA = new PlanRouteFragmentStartA();

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager(); //note the frag manager is coming from the activity, so I am in the world of the same
                //manager of the attached activity

                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.addToBackStack(PlanRouteFragment.class.getName());//I added it to the backstack with a TAG otherwise will not be saved at the backstack
                Log.d("BACK", "Entrando a PlanRouteFragmentt");

                YellowFragment yellowFragment = new YellowFragment();
                BlackFragment blackFragment = new BlackFragment();


                //fragmentTransaction.replace(R.id.rootB_framelayout, sentFragment).commit();
                fragmentTransaction.replace(R.id.root_framelayout, planRouteFragmentStartA).commit();
                fragmentManager.executePendingTransactions();


            }
        });*/
            /*
        Button b = (Button) getActivity().findViewById(R.id.buttonPlanRoute);
        //El problema esta en que intento a acceder antes de que se cree, aun cuando la actividad se finaliza, solo finaliza el primer fragmento
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        */
        return rootView;
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
