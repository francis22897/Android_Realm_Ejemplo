package com.example.realmexercise.Main;

import android.content.Context;
import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.realmexercise.Adapter.PlaceAdapter;
import com.example.realmexercise.Object.Country;
import com.example.realmexercise.Object.CountryRepository;
import com.example.realmexercise.Dialog.DialogRepository;
import com.example.realmexercise.Object.Place;
import com.example.realmexercise.R;
import com.example.realmexercise.Realm.RealmRepository;

import java.util.List;

import io.realm.RealmChangeListener;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity implements RealmChangeListener<RealmResults<Place>> {

    private FloatingActionButton fabNewPlace;
    private RealmResults<Place> listPlaces;
    private RecyclerView rv;
    private PlaceAdapter adapter;
    private RealmResults<Country> listCountries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init(){
        fabNewPlace = findViewById(R.id.fab);


        if(RealmRepository.getAllCountries().size() == 0) {
            Log.e("Relleno", "Hola");
            CountryRepository.addCountriesToBD();
        }

        listCountries = RealmRepository.getAllCountries();
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

        View view = DialogRepository.init(MainActivity.this, R.layout.dialog_place, "Agregar un nuevo lugar");

        final EditText edtName = view.findViewById(R.id.edit_namePlace);
        final EditText edtDescription = view.findViewById(R.id.edit_description);
        final Spinner spinner = view.findViewById(R.id.edit_placeCountry);
        fillSpinner(spinner);

        DialogRepository.addPositiveButton("Guardar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String name = edtName.getText().toString().trim();
                String description = edtDescription.getText().toString().trim();
                if(name.length() > 0){
                    Place newPlace = new Place(name, description, (Country) spinner.getSelectedItem());
                    RealmRepository.insertPlace(newPlace);
                }else{
                    Toast.makeText(getApplicationContext(), "Rellene todos los cambios", Toast.LENGTH_LONG).show();
                }
            }
        });

        DialogRepository.addNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        DialogRepository.showDialog();
    }

    private void update(final Place place){
        View view = DialogRepository.init(MainActivity.this, R.layout.dialog_place, "Editar");

        final EditText edtName = view.findViewById(R.id.edit_namePlace);
        final EditText edtDescription = view.findViewById(R.id.edit_description);
        final Spinner spinner = view.findViewById(R.id.edit_placeCountry);

        fillSpinner(spinner);

        edtName.setText(place.getName());
        edtDescription.setText(place.getDescription());
        spinner.setSelection(getPositionAtCountries(place.getCountry()));
        edtName.setSelection(edtName.getText().length());
        edtDescription.setSelection(edtDescription.getText().length());

        DialogRepository.addPositiveButton("Actualizar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String name = edtName.getText().toString().trim();
                String description = edtDescription.getText().toString().trim();
                Country country = (Country) spinner.getSelectedItem();
                if(name.length() > 0){
                    RealmRepository.updatePlace(place, name, description, new Country(country.getName()));
                }else{
                    Toast.makeText(getApplicationContext(), "Rellene todos los cambios", Toast.LENGTH_LONG).show();
                }
            }
        });

        DialogRepository.addNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        DialogRepository.showDialog();

    }

    private void delete(final Place place){
        View view = DialogRepository.init(MainActivity.this, R.layout.dialog_delete_confirm, "Eliminar");

        DialogRepository.addPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                RealmRepository.deletePlace(place);

            }
        });

        DialogRepository.addNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        DialogRepository.showDialog();
    }

    private void fillSpinner(Spinner spinner){
        ArrayAdapter sAdapter = new ArrayAdapter<Country>(this,
                android.R.layout.simple_spinner_dropdown_item, listCountries);
        spinner.setAdapter(sAdapter);
    }

    private int getPositionAtCountries(Country country){

        int position = 0;

        for(int i = 0; i < listCountries.size(); i++){
            if(listCountries.get(i).getId() == country.getId()){
                position = i;
                break;
            }
        }

        return position;
    }
}
