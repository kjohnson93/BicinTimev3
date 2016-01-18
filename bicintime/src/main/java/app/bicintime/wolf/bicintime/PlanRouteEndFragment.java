package app.bicintime.wolf.bicintime;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by wolf on 1/16/2016.
 */
public class PlanRouteEndFragment extends Fragment {

    private static final String LOG_TAG = "LOGTRACE";
    private static final String LOG_BACK = "LOGBACK";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.plan_route_end, container, false);


        LinearLayout linearlChooseOnMap, linearlCurrentLoc;

        linearlChooseOnMap = (LinearLayout) rootView.findViewById(R.id.linearl_choose_onmap);
        linearlCurrentLoc = (LinearLayout) rootView.findViewById(R.id.linearl_current_location);


        final FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

        linearlChooseOnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.root_framelayout, new PlanRouteFragmentStartA2());
                fragmentTransaction.addToBackStack(PlanRouteEndFragment.class.getName());
                fragmentTransaction.commit();
                fragmentManager.executePendingTransactions();


            }
        });

        linearlCurrentLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                TextView textViewStart = (TextView) getView().findViewById(R.id.textview_coordinates_current);



                Intent intent = new Intent();
                intent.putExtra("test", "Calle Roc Boronat");
                getTargetFragment().onActivityResult(getTargetRequestCode(), getActivity().RESULT_OK, intent);
                fragmentManager.popBackStack();
                fragmentManager.executePendingTransactions();


            }
        });



        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        Log.d(LOG_BACK, "Entering onResume PlanRouteFragmentEndFragment");
        Log.d(LOG_BACK, "The backentrycount is: " + fragmentManager.getBackStackEntryCount());
    }
}
