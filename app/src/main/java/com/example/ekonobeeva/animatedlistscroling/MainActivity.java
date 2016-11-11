package com.example.ekonobeeva.animatedlistscroling;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
        for(int i = 0; i < 25; i++){
            list.add("Aposition " + i);
        }
        for(int i = 25; i < 50; i++){
            list.add("Bposition " + i);
        }
        for(int i = 50; i < 75; i++){
            list.add("Cposition " + i);
        }
        for(int i = 75; i < 100; i++){
            list.add("Dposition " + i);
        }


        recyclerView =(RecyclerView)findViewById(R.id.recyclerWiew);
        recyclerView.setAdapter(new MyAdapter(list));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.addItemDecoration(new RVItemDecorator(this, 6, 3));

        fastScroller = (FastScroller)findViewById(R.id.fastScroller);
        fastScroller.setRecyclerView(recyclerView);

    }
}
