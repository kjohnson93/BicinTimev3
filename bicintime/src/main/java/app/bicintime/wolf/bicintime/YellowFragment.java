package app.bicintime.wolf.bicintime;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by wolf on 1/3/2016.
 */
public class YellowFragment extends Fragment implements View.OnKeyListener{

    public static final String DEFAULT = "";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.yellow_fragment_layout, container, false);

        Button button = (Button) view.findViewById(R.id.button_yellow);

        Log.d("BACK", "Referencia de button");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("BACK", "Dentro del listener del boton");
                /*
                BlueFragment blueFragment = new BlueFragment();
                BlackFragment blackFragment = new BlackFragment();

                */

                BlueFragment blueFragment = new BlueFragment();

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.addToBackStack(YellowFragment.class.getName());



                fragmentTransaction.replace(R.id.rootB_framelayout, blueFragment).commit();
                //fragmentManager.putFragment(savedInstanceState, "FBLUE", blueFragment);
                fragmentManager.executePendingTransactions();



            }
        });



        return view;
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

    public void changeData(String data){ //sobra

        TextView textView = (TextView) getView().findViewById(R.id.textView_yellow);

        textView.setText(data);


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d("BACK", "onActivityCreated de yellowfragment");

        //String data = savedInstanceState.getString("blue");


        //TextView textView = (TextView) getView().findViewById(R.id.textView_yellow);

        //textView.setText(data);

    }

    @Override
    public void onResume() {
        super.onResume();

        Log.d("BACK", "onResume de yellowfragment");

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyData", Context.MODE_PRIVATE);

        String data = sharedPreferences.getString("Blue_editor_text",DEFAULT);

        TextView textView = (TextView) getView().findViewById(R.id.textView_yellow);

        textView.setText(data);

        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.remove("Blue_editor_text");
        editor.commit();




    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if( keyCode == KeyEvent.KEYCODE_BACK )
        {
            Log.d("BACK", "Back tracking");
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            fragmentManager.popBackStack();
            return true;
        }

        return false;
    }

    public void onResumeFragment(){

        Log.d("BACK","Fuck you, Reloading Yellow");

    }
}
