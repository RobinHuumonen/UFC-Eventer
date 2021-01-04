package com.example.ufceventer;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {

    private List<EventListItem> eventListItems;
    private Context context;

    public EventAdapter(List<EventListItem> eventListItems, Context context) {
        this.eventListItems = eventListItems;
        this.context = context;
    }

    @Override
    public EventViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View eventItem = LayoutInflater.from(context)
                .inflate(R.layout.recyclerview_event_item, parent, false);
        return new EventViewHolder(eventItem);
    }

    @Override
    public void onBindViewHolder(EventViewHolder holder, int position) {
        EventListItem eventListItem = eventListItems.get(position);

        holder.textViewEventTitle.setText((eventListItem.getTitle()));
        holder.textViewEventDate.setText((eventListItem.getDate()));
        holder.linearLayoutEventItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent fights = new Intent(context, FightsActivity.class);
                fights.putExtra("URI", eventListItem.getUri());
                context.startActivity(fights);
            }
        });
    }

    @Override
    public int getItemCount() {
        return eventListItems.size();
    }

    public class EventViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewEventTitle;
        public TextView textViewEventDate;
        public LinearLayout linearLayoutEventItem;

        public EventViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewEventTitle = itemView.findViewById(R.id.textViewEventTitle);
            textViewEventDate = itemView.findViewById(R.id.textViewEventDate);
            linearLayoutEventItem = itemView.findViewById(R.id.linearLayoutEventItem);
        }
    }
}
