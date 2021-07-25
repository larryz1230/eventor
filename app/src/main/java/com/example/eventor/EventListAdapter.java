package com.example.eventor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class EventListAdapter extends RecyclerView.Adapter<EventListAdapter.EventViewHolder> {

    private final ArrayList<Eventt> eventList;
    private LayoutInflater mInflater;

    public EventListAdapter(Context context,
                            ArrayList<Eventt> wordList) {
        mInflater = LayoutInflater.from(context);
        this.eventList = wordList;
    }


    @NonNull
    @Override
    public EventListAdapter.EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.eventlistitem,
                parent, false);
        return new EventViewHolder(mItemView, this);

    }

    @Override
    public void onBindViewHolder(@NonNull EventListAdapter.EventViewHolder holder, int position) {
        String mCurrent = eventList.get(position).getEname();
//        System.out.println(position);
        holder.wordItemView.setText(mCurrent);
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    class EventViewHolder extends RecyclerView.ViewHolder {
        public final TextView wordItemView;
        final EventListAdapter mAdapter;

        public EventViewHolder(View itemView, EventListAdapter adapter) {
            super(itemView);
            wordItemView = itemView.findViewById(R.id.word);
            this.mAdapter = adapter;
        }

    }

}
