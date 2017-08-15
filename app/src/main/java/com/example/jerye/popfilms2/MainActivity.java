package com.example.jerye.popfilms2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import butterknife.BindView;

public class MainActivity extends AppCompatActivity{

    @BindView(R.id.rv_main)
    RecyclerView rvMain;

    MoviesAdapter moviesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private void setUpGrid(){
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2, LinearLayoutManager.VERTICAL,false);

        moviesAdapter = new MoviesAdapter(this);

        rvMain.setAdapter(moviesAdapter);
        rvMain.setLayoutManager(gridLayoutManager);



    }
}
