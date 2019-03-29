package com.example.ems.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ems.R;
import com.example.ems.models.LeaveType;

import java.util.List;

public class LeaveUsageAdapter extends RecyclerView.Adapter<LeaveUsageAdapter.ViewHolder> {
    Context context;
    List<String> leaveTypes;
    List<Integer> entitlements;
    List<Integer> pendingleaves;
    List<Integer> scheduledleave;
    List<Integer> consumedleave;
    List<Integer> leavebalance;

    public LeaveUsageAdapter(Context context1,List<String> leaveTypes,List<Integer> entitlements,List<Integer> pendingleaves,List<Integer> scheduledleave,List<Integer> leavebalance,List<Integer> consumedleave) {
        this.context=context1;
        this.leaveTypes=leaveTypes;
        this.entitlements=entitlements;
        this.pendingleaves=pendingleaves;
        this.scheduledleave=scheduledleave;
        this.leavebalance=leavebalance;
        this.consumedleave=consumedleave;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewtype) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.leave_usage_item, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        viewHolder.LeavetypeText.setText(leaveTypes.get(position));
        viewHolder.EntitlemetText.setText(entitlements.get(position)+"");
        viewHolder.PendingLeaveText.setText(pendingleaves.get(position)+"");
        viewHolder.BalanceLeaveText.setText(leavebalance.get(position)+"");
        viewHolder.ConsumedLeaveText.setText(consumedleave.get(position)+"");
        viewHolder.ScheduledLeaveText.setText(scheduledleave.get(position)+"");
    }

    @Override
    public int getItemCount() {
        return leaveTypes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView LeavetypeText;
        public TextView EntitlemetText;
        public TextView PendingLeaveText;
        public TextView ScheduledLeaveText;
        public TextView ConsumedLeaveText;
        public TextView BalanceLeaveText;


        public ViewHolder(View v) {
            super(v);
            LeavetypeText=v.findViewById(R.id.LeaveTypeText);
            EntitlemetText =  v.findViewById(R.id.EntitlementsText);
            BalanceLeaveText = v.findViewById(R.id.LeaveBalanceText);
            PendingLeaveText = v.findViewById(R.id.PendingLeaveText);
            ConsumedLeaveText = v.findViewById(R.id.ConsumedLeaveText);
            ScheduledLeaveText = v.findViewById(R.id.ScheduleLeaveText);

        }
    }
}
