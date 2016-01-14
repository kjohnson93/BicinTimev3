package app.bicintime.wolf.bicintime;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by wolf on 1/2/2016.
 */
public class BlueFragment extends Fragment{




    EditText editText_blue;
    String test_string;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("HOST", "Entering to BlueFragment onCreateView");
        View view = inflater.inflate(R.layout.blue_fragment_layout, container, false);

        Log.d("HOST", "Entering to BlueFragment onCreateView");

        editText_blue = (EditText) view.findViewById(R.id.editText_blue);



        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        /*

        communicator = (Communicator) getActivity();

        Button button = (Button) getView().findViewById(R.id.button_blue);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                communicator.respond("My first communicated info");
            }
        });
        */

        Button button = (Button) getView().findViewById(R.id.button_blue);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                test_string = editText_blue.getText().toString();

                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyData", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("Blue_editor_text", test_string);
                editor.commit();

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.popBackStack();



            }
        });


        Button buttonmap = (Button) getView().findViewById(R.id.button_to_map);

        buttonmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PlanRouteFragmentStartA2 mapFragment = new PlanRouteFragmentStartA2();
                BlackFragment blackFragment = new BlackFragment();

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.addToBackStack(BlueFragment.class.getName());
                fragmentTransaction.replace(R.id.root_framelayout, blackFragment);
                fragmentTransaction.commit();



            }
        });



    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);


        test_string = editText_blue.getText().toString();

        outState.putString("blue", test_string);

    }

    /*
    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if( keyCode == KeyEvent.KEYCODE_BACK )
        {
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            fragmentManager.popBackStack();
            return true;
        }
        return false;
    }*/

    @Override
    public void onResume() {

        Log.d("BACK","Fuck you, Reloading Blue");
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    public void onResumeFragment(){

    Log.d("BACK","Fuck you, Reloading Blue");

    }
}
