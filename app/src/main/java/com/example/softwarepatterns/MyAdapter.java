package com.example.softwarepatterns;


import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {


    private OnItemClickListener mListner;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListner = listener;
    }


    private ArrayList<String> mylistvalues;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView txtView;

        public MyViewHolder(View itemView) {
            super(itemView);
            txtView = (TextView) itemView.findViewById(R.id.textView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mListner != null){
                        int postion = getAdapterPosition();
                        if(postion != RecyclerView.NO_POSITION){
                            mListner.onItemClick(postion);
                        }
                    }
                }
            });
        }


    }



    public MyAdapter(ArrayList<String> myDataset) {
        mylistvalues= myDataset;
    }


    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(parent.getContext());
        View itemView= inflater.inflate(R.layout.row_layout, parent, false);
        MyViewHolder viewHolder= new MyViewHolder(itemView);
        return viewHolder;
    }

    Activity context;

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final String name = mylistvalues.get(position);
        holder.txtView.setText(name);
    }

    @Override
    public int getItemCount() {
        return mylistvalues.size();
    }
}

