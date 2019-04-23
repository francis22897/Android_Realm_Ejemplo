package com.example.realmexercise;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import io.realm.RealmChangeListener;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity implements RealmChangeListener<RealmResults<Place>> {

    private FloatingActionButton fabNewPlace;
    private RealmResults<Place> listPlaces;
    private RecyclerView rv;
    private PlaceAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init(){
        fabNewPlace = findViewById(R.id.fab);
        listPlaces = RealmRepository.getAllPlaces();
        listPlaces.addChangeListener(this);
        rv = findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter = new PlaceAdapter(getApplicationContext(), listPlaces);
        rv.setAdapter(adapter);

        setListeners();
    }

    private void setListeners(){
        fabNewPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //newItem();
            }
        });

        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Place place = listPlaces.get(rv.getChildAdapterPosition(v));
                //editItem(place);
            }
        });

        adapter.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                //deleteItem();
                return false;
            }
        });
    }


    @Override
    public void onChange(RealmResults<Place> places) {
        adapter.notifyDataSetChanged();
    }
}
