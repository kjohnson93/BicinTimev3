package app.bicintime.wolf.bicintimeactivities;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by wolf on 1/30/2016.
 */
public class RecyclerViewTimeAdapter extends RecyclerView.Adapter<RecyclerViewTimeAdapter.MyViewHolder> {

    private LayoutInflater layoutInflater;
    private Context context;
    List<PlanSelectTimeActivity.Time> data;


    public RecyclerViewTimeAdapter(Context context, List<PlanSelectTimeActivity.Time> data){

    this.context = context;
        layoutInflater = layoutInflater.from(context);
        this.data = data;

    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = layoutInflater.inflate(R.layout.custom_time_selection, parent, false);

        MyViewHolder viewHolder = new MyViewHolder(view);

        return viewHolder;


    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        PlanSelectTimeActivity.Time currentData = data.get(position);

        holder.textViewTime.setText(currentData.toString());



    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView textViewTime;


        public MyViewHolder(View itemView) {
            super(itemView);

            textViewTime = (TextView) itemView.findViewById(R.id.textViewTime);

        }


    }
}
