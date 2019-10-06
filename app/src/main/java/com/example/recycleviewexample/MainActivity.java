package com.example.recycleviewexample;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ArrayList<Article> articleArrayList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addDummy();
        initRecyclerView();
    }

    private void addDummy(){
        articleArrayList.add(new Article("안녕1", "안녕 나는 북극곰이야"));
        articleArrayList.add(new Article("안녕2", "안녕 나는 북극곰이야"));
        articleArrayList.add(new Article("안녕3", "안녕 나는 북극곰이야"));
        articleArrayList.add(new Article("안녕4", "안녕 나는 북극곰이야"));
        articleArrayList.add(new Article("안녕5", "안녕 나는 북극곰이야"));
        articleArrayList.add(new Article("안녕6", "안녕 나는 북극곰이야"));
        articleArrayList.add(new Article("안녕7", "안녕 나는 북극곰이야"));
        articleArrayList.add(new Article("안녕8", "안녕 나는 북극곰이야"));
        articleArrayList.add(new Article("안녕9", "안녕 나는 북극곰이야"));
        articleArrayList.add(new Article("안녕10", "안녕 나는 북극곰이야"));
        articleArrayList.add(new Article("안녕11", "안녕 나는 북극곰이야"));
        articleArrayList.add(new Article("안녕12", "안녕 나는 북극곰이야"));

    }

    private void initRecyclerView(){
        recyclerView = findViewById(R.id.recyclerView);
        ItemRecyclerAdapter adapter = new ItemRecyclerAdapter(articleArrayList,getApplicationContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(adapter);
    }
}
