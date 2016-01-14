package app.bicintime.wolf.bicintime;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by wolf on 1/2/2016.
 */
public class BlackFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.black_fragment_layout, container, false);
    }

    public void onResumeFragment(){

        Log.d("BACK", "Fuck you, Reloading Black");

    }
}
