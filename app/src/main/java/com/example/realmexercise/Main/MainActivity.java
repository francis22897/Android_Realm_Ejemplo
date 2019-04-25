package com.example.realmexercise.Main;

import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.realmexercise.Adapter.PlaceAdapter;
import com.example.realmexercise.Object.Place;
import com.example.realmexercise.R;
import com.example.realmexercise.Realm.RealmRepository;

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
                insert();
            }
        });

        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Place place = listPlaces.get(rv.getChildAdapterPosition(v));
                update(place);
            }
        });

        adapter.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Place place = listPlaces.get(rv.getChildAdapterPosition(v));
                delete(place);
                return false;
            }
        });
    }


    @Override
    public void onChange(RealmResults<Place> places) {
        adapter.notifyDataSetChanged();
    }

    private void insert(){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        final View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.dialog_place, null);
        builder.setView(view);

        final EditText edtName = view.findViewById(R.id.edit_namePlace);
        final EditText edtDescription = view.findViewById(R.id.edit_description);
        builder.setMessage("Agregar un nuevo lugar");
        builder.setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String name = edtName.getText().toString().trim();
                String description = edtDescription.getText().toString().trim();
                if(name.length() > 0){
                    Place newPlace = new Place(name, description);
                    RealmRepository.insertPlace(newPlace);
                }else{
                    Toast.makeText(getApplicationContext(), "Rellene todos los cambios", Toast.LENGTH_LONG).show();
                }
            }
        });

        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void update(final Place place){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        final View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.dialog_place, null);
        builder.setView(view);

        final EditText edtName = view.findViewById(R.id.edit_namePlace);
        final EditText edtDescription = view.findViewById(R.id.edit_description);
        builder.setMessage("Editar");

        edtName.setText(place.getName());
        edtDescription.setText(place.getDescription());

        edtName.setSelection(edtName.getText().length());

        builder.setPositiveButton("Actualizar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String name = edtName.getText().toString().trim();
                String description = edtDescription.getText().toString().trim();
                if(name.length() > 0){
                    RealmRepository.updatePlace(place, name, description);
                }else{
                    Toast.makeText(getApplicationContext(), "Rellene todos los cambios", Toast.LENGTH_LONG).show();
                }
            }
        });

        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

    private void delete(final Place place){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        final View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.dialog_delete_confirm, null);
        builder.setView(view);

        builder.setMessage("Elimina este lugar");
        builder.setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                RealmRepository.deletePlace(place);
            }
        });

        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
