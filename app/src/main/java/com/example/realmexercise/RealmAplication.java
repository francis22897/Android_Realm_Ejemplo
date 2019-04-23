package com.example.realmexercise;

import android.app.Application;

import java.util.concurrent.atomic.AtomicInteger;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmObject;
import io.realm.RealmResults;

public class RealmAplication extends Application {
    public static AtomicInteger placeId = new AtomicInteger();

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(getApplicationContext());
        setUpRealConfig();
        Realm realm = Realm.getDefaultInstance();
        placeId = getByTabla(realm, Place.class);
        realm.close();
    }

    private void setUpRealConfig(){
        RealmConfiguration config = new RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().build();
        Realm.setDefaultConfiguration(config);
    }

    private <T extends RealmObject> AtomicInteger getByTabla(Realm realm, Class<T> anyClass){
        RealmResults<T> results = realm.where(anyClass).findAll();
        return (results.size()>0)? new AtomicInteger(results.max("id").intValue()): new AtomicInteger();
    }
}
