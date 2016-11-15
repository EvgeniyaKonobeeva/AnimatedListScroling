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

public class RVAdapterListString extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements FastScroller.FastScrollAdapter{
    private static final String TAG = "RVAdapterListString";
    public static int SINGLE_SYM = 1;
    public static int WORD = 2;


    public ArrayList<String> list;

    public RVAdapterListString(ArrayList<String> list) {
        this.list = list;
    }

    @Override
    public int getItemViewType(int position) {

        if(list.get(position).length()==1){
            list.get(position).toUpperCase();
            return SINGLE_SYM;
        }else {
            return WORD;
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof WordVH){
            ((WordVH)holder).textView1.setText(list.get(position));
        }else {
            ((SingleSymVH)holder).textView.setText(list.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if(viewType == SINGLE_SYM){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_single_sym, parent, false);
            return new SingleSymVH(view);
        }else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
            return new WordVH(view);
        }

    }

    @Override
    public String getLetter(int position) {
        return list.get(position).substring(0,1).toUpperCase();
    }

    public static class WordVH extends RecyclerView.ViewHolder{
        TextView textView1;


        public WordVH(View rootView) {
            super(rootView);
            textView1 = (TextView)rootView.findViewById(R.id.item_text1);
        }

    }
    public static class SingleSymVH extends RecyclerView.ViewHolder{
        TextView textView;
        public SingleSymVH(View itemView) {
            super(itemView);
            textView = (TextView)itemView.findViewById(R.id.item_text);
        }
    }




}
