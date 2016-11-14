package com.example.ekonobeeva.animatedlistscroling;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
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


        recyclerView =(RecyclerView)findViewById(R.id.recyclerWiew);
        recyclerView.setAdapter(new MyAdapter(list));
        recyclerView.setLayoutManager(new GridLayoutManager(this, 5));
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new RVItemDecorator(this, 6, 3));

        fastScroller = (FastScroller)findViewById(R.id.fastScroller);
        fastScroller.setRecyclerView(recyclerView);

    }
}
