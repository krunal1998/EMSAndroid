package com.example.ems.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ems.R;
import com.example.ems.models.Holiday;

import java.util.List;

public class HolidayAdapter extends RecyclerView.Adapter<HolidayAdapter.ViewHolder> {
    Context context;
    private List<Holiday> Holidays;

    public void add(int position, Holiday item) {
        Holidays.add(position, item);
        notifyItemInserted(position);
    }
    public void remove(Holiday item) {
        int position = Holidays.indexOf(item);
        Holidays.remove(position);
        notifyItemRemoved(position);
    }
    public HolidayAdapter(Context context1, List<Holiday> holidaylist) {
        Holidays =holidaylist;
        context=context1;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewtype) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.holiday_item, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        String date= Holidays.get(position).HolidayDate;
        Integer duration=Holidays.get(position).WorkDuration;

        viewHolder.holidayName.setText(Holidays.get(position).HolidayName);
        viewHolder.holidayDate.setText(date.substring(8,10) + "/" + date.substring(5,7) +"/"+ date.substring(0,4));
        if(duration == 4)
            viewHolder.holidaywork.setText("Half Day");
        else
            viewHolder.holidaywork.setText("");


    }

    @Override
    public int getItemCount() {
        return Holidays.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView holidayName;
        public TextView holidayDate;
        public TextView holidaywork;


        public ViewHolder(View v) {
            super(v);
            holidayName =  v.findViewById(R.id.HolidayName);
            holidayDate = v.findViewById(R.id.HolidayDate);
            holidaywork = v.findViewById(R.id.HolidayWork);

        }
    }
}
