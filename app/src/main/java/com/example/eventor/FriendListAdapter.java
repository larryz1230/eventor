package com.example.Friendor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eventor.R;
import com.example.eventor.User;

import java.util.ArrayList;

public class FriendListAdapter extends RecyclerView.Adapter<FriendListAdapter.FriendViewHolder> {

    private final ArrayList<User> friendslist;
    private LayoutInflater mInflater;

    public FriendListAdapter(Context context,
                            ArrayList<User> wordList) {
        mInflater = LayoutInflater.from(context);
        this.friendslist = wordList;
    }


    @NonNull
    @Override
    public FriendListAdapter.FriendViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.eventlistitem,
                parent, false);
        return new FriendViewHolder(mItemView, this);

    }

    @Override
    public void onBindViewHolder(@NonNull FriendListAdapter.FriendViewHolder holder, int position) {
        String mCurrent = friendslist.get(position).getFname();
//        System.out.println(position);
        holder.wordItemView.setText(mCurrent);
    }

    @Override
    public int getItemCount() {
        return friendslist.size();
    }

    class FriendViewHolder extends RecyclerView.ViewHolder {
        public final TextView wordItemView;
        final FriendListAdapter mAdapter;

        public FriendViewHolder(View itemView, FriendListAdapter adapter) {
            super(itemView);
            wordItemView = itemView.findViewById(R.id.word);
            this.mAdapter = adapter;
        }

    }

}
