package com.example.realmexercise;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

public class RealmRepository {

    protected static Realm realm = Realm.getDefaultInstance();

    public static RealmResults<Place> getAllPlaces(){
        return realm.where(Place.class).findAll();
    }


}
