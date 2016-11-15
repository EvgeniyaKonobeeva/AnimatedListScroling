package com.example.ekonobeeva.animatedlistscroling;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private FastScroller fastScroller;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<String> list = new ArrayList<>();
        generateStringList(list);

        ArrayList<FoodRestModel> cards = new ArrayList<>();
        generateImgList(cards);

        recyclerView =(RecyclerView)findViewById(R.id.recyclerWiew);
        recyclerView.addItemDecoration(new RVItemDecorator(this, 6, 3));


        LinearLayoutManager ll = new LinearLayoutManager(this);
        RVAdapterListString adapter1 = new RVAdapterListString(list);

        final GridLayoutManager gr = new GridLayoutManager(this, 3);
        final RVAdapterGridCards adapter2 = new RVAdapterGridCards(cards);

        recyclerView.setAdapter(adapter2);
        recyclerView.setLayoutManager(gr);

        gr.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if(adapter2.getItemViewType(position) == RVAdapterGridCards.SINGLE_SYM){
                    return 3;
                }else return 1;
            }
        });



        fastScroller = (FastScroller)findViewById(R.id.fastScroller);
        fastScroller.setRecyclerView(recyclerView);

    }

    public void generateStringList(ArrayList<String> list){
        list.add("A");
        for(int i = 1; i < 25; i++){
            list.add("Aposition " + i);
        }
        list.add("B");
        for(int i = 26; i < 50; i++){
            list.add("Bposition " + i);
        }
        list.add("C");
        for(int i = 51; i < 75; i++){
            list.add("Cposition " + i);
        }
        list.add("D");
        for(int i = 76; i < 100; i++){
            list.add("Dposition " + i);
        }

        list.add("E");
        for(int i = 101; i < 200; i++) {
            list.add("Eposition " + i);
        }
    }
    public void generateImgList(ArrayList<FoodRestModel> cards){
        cards.add(new FoodRestModel("A", null));
        for(int i = 1; i < 30; i++){
            Bitmap bmp = BitmapFactory.decodeResource(this.getResources(), R.mipmap.ic_launcher);
            cards.add(new FoodRestModel("Acard " + i, bmp));
        }
        cards.add(new FoodRestModel("B", null));
        for(int i = 31; i < 60; i++){
            Bitmap bmp = BitmapFactory.decodeResource(this.getResources(), R.mipmap.ic_launcher);
            cards.add(new FoodRestModel("Bcard " + i, bmp));
        }
        cards.add(new FoodRestModel("C", null));
        for(int i = 61; i < 90; i++){
            Bitmap bmp = BitmapFactory.decodeResource(this.getResources(), R.mipmap.ic_launcher);
            cards.add(new FoodRestModel("Ccard " + i, bmp));
        }
        cards.add(new FoodRestModel("D", null));
        for(int i = 91; i < 110; i++){
            Bitmap bmp = BitmapFactory.decodeResource(this.getResources(), R.mipmap.ic_launcher);
            cards.add(new FoodRestModel("Dcard " + i, bmp));
        }
        cards.add(new FoodRestModel("E", null));
        for(int i = 111; i < 200; i++){
            Bitmap bmp = BitmapFactory.decodeResource(this.getResources(), R.mipmap.ic_launcher);
            cards.add(new FoodRestModel("Ecard " + i, bmp));
        }
    }
}
