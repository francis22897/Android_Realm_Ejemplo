package com.example.realmexercise.Realm;

import android.util.Log;

import com.example.realmexercise.Main.MainActivity;
import com.example.realmexercise.Object.Country;
import com.example.realmexercise.Object.Place;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;

public class RealmRepository {

    private static Realm realm = Realm.getDefaultInstance();
    private static MainActivity main = new MainActivity();

    public static RealmResults<Place> getAllPlaces(){
        return realm.where(Place.class).findAll();
    }

    public static RealmResults<Country> getAllCountries(){
        return realm.where(Country.class).findAll();
    }

    public static void insertPlace(Place place){
        try{
            realm.beginTransaction();
            realm.copyToRealm(place);
            realm.commitTransaction();
        }catch(Exception ex){
            main.showToast("No se pudo realizar la inserción del lugar");
        }
    }

    public static void insertCountry(Country country){
        try{
            realm.beginTransaction();
            realm.copyToRealm(country);
            realm.commitTransaction();
        }catch(Exception ex){
            main.showToast("No se pudo realizar la inserción del país");
        }
    }

    public static void updatePlace(Place place, String name, String description, Country country){
        try{
            realm.beginTransaction();
            place.setName(name);
            place.setDescription(description);
            place.setCountry(realm.where(Country.class).equalTo("name", country.getName()).findFirst());
            realm.commitTransaction();
        }catch(Exception ex){
            main.showToast("No se pudo realizar la actualización del lugar");
        }
    }

    public static void deletePlace(Place place){
        try{
            realm.beginTransaction();
            assert place != null;
            place.deleteFromRealm();
            realm.commitTransaction();
        }catch (Exception ex){
            main.showToast("No se pudo eliminar el lugar");
        }
    }
}
