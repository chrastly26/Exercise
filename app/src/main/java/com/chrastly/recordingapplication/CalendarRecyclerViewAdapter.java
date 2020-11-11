package com.chrastly.recordingapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class CalendarRecyclerViewAdapter extends RecyclerView.Adapter<CalendarRecyclerViewAdapter.ViewHolder> {

    private ArrayList<Memo> memoArrayList = new ArrayList<>();


    public CalendarRecyclerViewAdapter(){

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_calendar_recycler_view, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.titleTextField.setText(memoArrayList.get(position).getTitle());

    }

    @Override
    public int getItemCount() {
        return memoArrayList.size();
    }

    public void setMemoArrayList(ArrayList<Memo> memoArrayList) {
        this.memoArrayList = memoArrayList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView titleTextField;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextField = itemView.findViewById(R.id.titleLayout);
        }
    }
}
