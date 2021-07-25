package com.example.eventor;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import java.util.List;
public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.ViewHolder> {
    private List<Eventt> mEventData;
    private Context mContext;
    EventsAdapter(Context context, List<Eventt> eventData){
        this.mEventData = eventData;
        this.mContext = context;
    }

    public EventsAdapter.ViewHolder onCreateViewHolder(
            ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).
                inflate(R.layout.list_item, parent, false));
    }

    public void onBindViewHolder(EventsAdapter.ViewHolder holder,
                                 int position) {
        // Get current sport.
        Eventt currentSport = mEventData.get(position);

        // Populate the textviews with data.
        holder.bindTo(currentSport);
    }

    public int getItemCount() {
        return mEventData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener{

        // Member Variables for the TextViews
        private TextView mTitleText;
        private TextView mDate;
        private TextView mTime;

        /**
         * Constructor for the ViewHolder, used in onCreateViewHolder().
         *
         * @param itemView The rootview of the list_item.xml layout file.
         */
        ViewHolder(View itemView) {
            super(itemView);

            // Initialize the views.
            mTitleText = itemView.findViewById(R.id.eventname);
            mDate = itemView.findViewById(R.id.date);
            mTime = itemView.findViewById(R.id.time);

            // Set the OnClickListener to the entire view.
            itemView.setOnClickListener(this);
        }

        void bindTo(Eventt currentEvent){
            // Populate the textviews with data.
            System.out.println(currentEvent.getSday());
            mTitleText.setText(currentEvent.getEname());
            mDate.setText(currentEvent.getDay());
            mTime.setText(currentEvent.getTime());

            // Load the images into the ImageView using the Glide library.
//            Glide.with(mContext).load(
//                    currentEvent.getImageResource()).into(mSportsImage);
        }

        /**
         * Handle click to show DetailActivity.
         *
         * @param view View that is clicked.
         */
        @Override
        public void onClick(View view) {
//            event currentEvent = mEventData.get(getAdapterPosition());
//            Intent detailIntent = new Intent(mContext, DetailActivity.class);
//            detailIntent.putExtra("title", currentEvent.getEventname());
//            detailIntent.putExtra("image_resource",
//                    currentEvent.getImageResource());
//            mContext.startActivity(detailIntent);
        }
    }
}
