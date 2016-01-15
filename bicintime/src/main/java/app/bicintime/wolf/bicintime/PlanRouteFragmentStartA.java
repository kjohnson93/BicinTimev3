package app.bicintime.wolf.bicintime;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.Collections;
import java.util.List;

/**
 * Created by wolf on 12/31/2015.
 */
public class PlanRouteFragmentStartA extends Fragment implements View.OnKeyListener {

    List<Fragment> fragmentList = Collections.emptyList();


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.plan_route_start_a, container, false);

        final FragmentManager fragmentManager = getActivity().getSupportFragmentManager();


        Log.d("BACK","The fragment list is: " + fragmentList);
        //Log.d("BACK", "And its backstack is: " + fragmentManager.bac)


        Button buttonOk = (Button) rootView.findViewById(R.id.button_choose_onfield);
        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText editTextMapStart = (EditText) rootView.findViewById(R.id.editText_to_planroute);
                String startData = editTextMapStart.getText().toString();

                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyData", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("StartLocation", startData);
                editor.commit();


                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.addToBackStack(null);
                fragmentManager.popBackStack();

            }
        });


        Button buttonMap = (Button) rootView.findViewById(R.id.button_choose_onmap);
        buttonMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.replace(R.id.root_framelayout, new PlanRouteFragmentStartA2());
                fragmentTransaction.commit();
                fragmentManager.executePendingTransactions();

            }
        });


        /*
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                EditText editText = (EditText) getActivity().findViewById(R.id.editText_to_planroute);

                String data = editText.getText().toString();

                Log.d("RECYCLER", "My value as input in sharedfragment test is : " + data);


                PlanRouteFragment planRouteFragment = new PlanRouteFragment();

                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyData", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("Plan_route_test", data);
                editor.commit();

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.addToBackStack(PlanRouteFragmentStartA.class.getName());
                fragmentTransaction.replace(R.id.root_framelayout, planRouteFragment).commit();
                fragmentManager.executePendingTransactions();




            }
        });
        */


        return rootView;
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {

        return false;
    }
}
