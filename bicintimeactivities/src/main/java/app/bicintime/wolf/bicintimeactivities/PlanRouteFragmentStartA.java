package app.bicintime.wolf.bicintimeactivities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.Collections;
import java.util.List;

/**
 * Created by wolf on 12/31/2015.
 */
public class PlanRouteFragmentStartA extends Fragment implements View.OnClickListener {

    private static final String FRAGMENT_KEY = "test";
    List<Fragment> fragmentList = Collections.emptyList();
    private static final String LOG_TAG = "LOGTRACE";
    private static final String LOG_BACK = "LOGBACK";


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.plan_route_start_a, container, false);

        final FragmentManager fragmentManager = getActivity().getSupportFragmentManager();


        Log.d("BACK", "The fragment list is: " + fragmentList);
        //Log.d("BACK", "And its backstack is: " + fragmentManager.bac)

        LinearLayout linearl_choose_onmap, linearl_current_loc;


        linearl_choose_onmap = (LinearLayout) rootView.findViewById(R.id.linearl_choose_onmap);
        linearl_choose_onmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(LOG_TAG, "Entering onClick with view id: " + v.getId());

                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.addToBackStack(PlanRouteFragmentStartA.class.getName());
                fragmentTransaction.replace(R.id.root_framelayout, new PlanRouteFragmentStartA2());
                fragmentTransaction.commit();
                fragmentManager.executePendingTransactions();

            }
        });

        linearl_current_loc= (LinearLayout) rootView.findViewById(R.id.linearl_current_location);
        linearl_current_loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d(LOG_TAG, "Entering onClick with view id: " + v.getId());

                /*
                TextView textViewStart = (TextView) getView().findViewById(R.id.textview_coordinates_current);

                String startData = textViewStart.getText().toString();


                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyData", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                Log.d(LOG_TAG, "Data put is:" + startData);

                editor.putString("StartLocation", startData);
                editor.commit();*/

                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                //fragmentTransaction.addToBackStack(PlanRouteFragmentStartA.class.getName());
                //fragmentTransaction.replace(R.id.root_framelayout, new PlanRouteFragment());//I have to call this method instead of popbackstack because the later won't call the onResume
                //fragmentTransaction.commit();
                //fragmentManager.executePendingTransactions(); //when doing this, sharedpreferences works, otherwise don't

                Intent intent = new Intent();
                intent.putExtra(FRAGMENT_KEY, "Ok");
                getTargetFragment().onActivityResult(getTargetRequestCode(), getActivity().RESULT_OK, intent);

                fragmentManager.popBackStack();
                //TODO Find out why this behaviour of sharedpreferences -> because onresumen on PlanRouteFragment is being called twice. I need to popbackstack nstead
                //but popbacktack is not colling onResume, onResume is only called when Activity onResume is being called
            }
        });


        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        Log.d(LOG_BACK, "Entering onResume PlanRouteFragmentStartA");
        Log.d(LOG_BACK, "The backentrycount is: " + fragmentManager.getBackStackEntryCount());
    }

    @Override
    public void onClick(View v) { //Dunno why never called ???

        Log.d(LOG_TAG,"Entering onClick override with view id: " + v.getId());

        final FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

        if(v.getId() == R.id.linearl_choose_onmap){
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.root_framelayout, new PlanRouteFragmentStartA2());
            fragmentTransaction.addToBackStack(PlanRouteFragmentStartA.class.getName());
            fragmentTransaction.commit();
            fragmentManager.executePendingTransactions();
        }

        if(v.getId() == R.id.linearl_current_location){



            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.addToBackStack(PlanRouteFragmentStartA.class.getName());
            fragmentTransaction.replace(R.id.root_framelayout, new PlanRouteFragment());
            fragmentTransaction.commit();
            fragmentManager.executePendingTransactions();
        }

    }
}
