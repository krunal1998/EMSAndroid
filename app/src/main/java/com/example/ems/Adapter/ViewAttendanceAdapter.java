package com.example.ems.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ems.R;
import com.example.ems.models.Attendance;

import java.util.List;

public class ViewAttendanceAdapter extends RecyclerView.Adapter<ViewAttendanceAdapter.ViewHolder> {

    Context context;
    private List<Attendance> attendances;

    public ViewAttendanceAdapter(Context context1, List<Attendance> attendancelist) {
        context=context1;
        attendances=attendancelist;
    }

    public void add(int position, Attendance item) {
        attendances.add(position, item);
        notifyItemInserted(position);
    }
    public void remove(Attendance item) {
        int position = attendances.indexOf(item);
        attendances.remove(position);
        notifyItemRemoved(position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewtype) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_attendance_item, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        String date = attendances.get(position).PunchInDate;
        Double workinghours = attendances.get(position).WorkingHours;
        date= date.substring(8,10) + "/" + date.substring(5,7) +"/"+ date.substring(0,4);
        String dateandstatus;
        if(workinghours==8.0)
            dateandstatus=date + "  -  Present(Full Day)" ;
        else if(workinghours==4.0)
            dateandstatus=date+ "  -  Present(Half Day)";
        else {
            dateandstatus = date + "  -  Absent";
            viewHolder.DateAndStatusText.setTextColor(Color.RED);
        }


        viewHolder.DateAndStatusText.setText(dateandstatus);
        viewHolder.PunchInTimeText.setText(attendances.get(position).PunchInTime);
        viewHolder.PunchOutTimeText.setText(attendances.get(position).PunchOutTime);

    }

    @Override
    public int getItemCount() {
        return attendances.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView DateAndStatusText;
        public TextView PunchInTimeText;
        public TextView PunchOutTimeText;


        public ViewHolder(View v) {
            super(v);
            DateAndStatusText =  v.findViewById(R.id.DateAndStatusText);
            PunchInTimeText = v.findViewById(R.id.PunchInTimeText);
            PunchOutTimeText = v.findViewById(R.id.PunchOutTimeText);

        }
    }
}
