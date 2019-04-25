package com.example.realmexercise.Realm;

import android.util.Log;

import com.example.realmexercise.Object.Country;
import com.example.realmexercise.Object.Place;

import io.realm.Realm;
import io.realm.RealmResults;

public class RealmRepository {

    private static Realm realm = Realm.getDefaultInstance();

    public static RealmResults<Place> getAllPlaces(){
        return realm.where(Place.class).findAll();
    }

    public static void insertPlace(Place place){
        try{
            realm.beginTransaction();
            realm.copyToRealm(place);
            realm.commitTransaction();
        }catch(Exception ex){
            Log.e("Insert", ex.getMessage());
        }
    }

    public static void updatePlace(Place place, String name, String description, Country country){
        try{
            realm.beginTransaction();
            place.setName(name);
            place.setDescription(description);
            place.setCountry(realm.copyToRealm(country));
            realm.commitTransaction();
        }catch(Exception ex){
            Log.e("Update", ex.getMessage());
        }
    }

    public static void deletePlace(Place place){
        try{
            realm.beginTransaction();
            assert place != null;
            place.deleteFromRealm();
            realm.commitTransaction();
        }catch (Exception ex){
            Log.e("Delete", ex.getMessage());
        }
    }
}
