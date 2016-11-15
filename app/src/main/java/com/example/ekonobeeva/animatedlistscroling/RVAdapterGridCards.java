package com.example.ekonobeeva.animatedlistscroling;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by e.konobeeva on 15.11.2016.
 */

public class RVAdapterGridCards extends RecyclerView.Adapter implements FastScroller.FastScrollAdapter {
    public static int SINGLE_SYM = 1;
    public static int IMG_CARD = 2;
    private ArrayList<FoodRestModel> list;

    public RVAdapterGridCards(ArrayList<FoodRestModel> list) {
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == SINGLE_SYM){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_single_sym, parent, false);
            return new SingleSymVH(view);
        }else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card, parent, false);
            return new CardsVH(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof SingleSymVH){
            ((SingleSymVH)holder).textView.setText(list.get(position).getTitle());
        }else {
            ((CardsVH)holder).im.setImageBitmap(list.get(position).getBmp());
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public String getLetter(int position) {
        return list.get(position).getTitle().substring(0,1);
    }

    @Override
    public int getItemViewType(int position) {
        if(list.get(position).getBmp() == null){
            return SINGLE_SYM;
        }else return IMG_CARD;
    }

    public static class SingleSymVH extends RecyclerView.ViewHolder{
        TextView textView;
        public SingleSymVH(View itemView) {
            super(itemView);
            textView = (TextView)itemView.findViewById(R.id.item_text);
        }
    }
    public class CardsVH extends RecyclerView.ViewHolder{
        ImageView im;
        public CardsVH(View itemView) {
            super(itemView);
            im = (ImageView)itemView.findViewById(R.id.image1);
        }
    }


}
