package com.example.ekonobeeva.animatedlistscroling;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by e.konobeeva on 10.11.2016.
 */

public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static int SINGLE_SYM = 1;
    public static int WORD = 2;
    public ArrayList<String> list;

    public MyAdapter(ArrayList<String> list) {
        this.list = list;
    }

    @Override
    public int getItemViewType(int position) {
        if(list.get(position).matches("[A-Z]")){
            return SINGLE_SYM;
        }else {
            return WORD;
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((MyViewHolder)holder).textView.setText(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new MyViewHolder(view);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView textView;


        public MyViewHolder(View rootView) {
            super(rootView);
            textView = (TextView)rootView.findViewById(R.id.item_text);
        }
    }




}
