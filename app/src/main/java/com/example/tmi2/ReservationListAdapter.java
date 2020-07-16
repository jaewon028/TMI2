package com.example.tmi2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tmi2.model.Reservation;

import java.util.ArrayList;

public class ReservationListAdapter extends RecyclerView.Adapter<ReservationListAdapter.ViewHolder> {
    ArrayList<Reservation> items;
    OnItemClickListener listener;

    public ReservationListAdapter(ArrayList<Reservation> items, OnItemClickListener listener) {
        this.items = items;
        this.listener = listener;
    }

    interface OnItemClickListener {
        void OnCancelClicked(View v, int position, Reservation item);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reservation, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final Reservation item = items.get(position);

        holder.cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.OnCancelClicked(view, position, item);
            }
        });

        holder.day.setText(item.getDay());
        holder.peopleNum.setText(item.getPeopleNum() + "명");
        holder.dept.setText(item.getDept());
        holder.dest.setText(item.getDest());
        holder.seat.setText(item.getSeatList().toString());
        holder.time.setText(item.getTime());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView day, time, dest, dept, peopleNum, seat;
        Button cancelBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            day = itemView.findViewById(R.id.day);
            time = itemView.findViewById(R.id.time);
            dest = itemView.findViewById(R.id.dest);
            dept = itemView.findViewById(R.id.dept);
            peopleNum = itemView.findViewById(R.id.peopleNum);
            seat = itemView.findViewById(R.id.seat);
            cancelBtn = itemView.findViewById(R.id.cancelBtn);
        }
    }
}
