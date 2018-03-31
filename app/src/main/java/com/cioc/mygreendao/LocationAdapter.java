package com.cioc.mygreendao;

import android.content.Context;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cioc.mygreendao.db.GPSLocation;

import java.util.List;

/**
 * Created by Ashish on 1/30/2018.
 */

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.MyHolder> {

    private List<GPSLocation> dataset;
    Context context;

    public LocationAdapter(Context context)
    {
        this.context = context;
    }
    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = layoutInflater.inflate(R.layout.location_layout,parent,false);
        MyHolder mh = new MyHolder(v);
        return mh;
    }


    public void setGPSLocation(@NonNull List<GPSLocation> locations) {
        dataset = locations;
        notifyDataSetChanged();
    }

    public GPSLocation getGPSLocation(int position) {
        return dataset.get(position);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        GPSLocation gpsLocation = dataset.get(position);
        holder.tv1.setText(gpsLocation.getLongitude_value()+" \n"+gpsLocation.getLatitude_value());
        holder.tv2.setText(""+LocationService.loc);
        holder.tv3.setText(gpsLocation.getDate_time()+" d "+LocationService.distance);
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public static class MyHolder extends RecyclerView.ViewHolder{
        TextView tv1, tv2, tv3;
        public MyHolder(View itemView) {
            super(itemView);
            tv1 = itemView.findViewById(R.id.longitude);
            tv2 = itemView.findViewById(R.id.latitude);
            tv3 = itemView.findViewById(R.id.date_time);
        }
    }
}
