package app.bicintime.wolf.bicintimeactivities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;

public class PlanFitnessActivity extends BaseActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_fitness);

        agregarToolbar();
        setUpDrawer();


        SeekBar seekBar = (SeekBar)findViewById(R.id.seekbar);
        seekBar.setProgress(0);
        seekBar.incrementProgressBy(10);
        seekBar.setMax(200);


        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            TextView seekBarValue = (TextView)findViewById(R.id.seekbarvalue);


            int stepSize = 50;
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                progress = (progress/stepSize) * stepSize;
                seekBar.setProgress(progress);
                seekBarValue.setText(progress + "");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, PlanRouteActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
    }
}
