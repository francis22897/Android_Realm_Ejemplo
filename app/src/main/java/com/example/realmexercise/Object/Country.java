package com.example.realmexercise.Object;

import com.example.realmexercise.Realm.RealmAplication;

import io.realm.RealmObject;

public class Country extends RealmObject {
    private int id;
    private String name;

    public Country(){}

    public Country(String name) {
        this.id = RealmAplication.countryId.incrementAndGet();
        this.name = name;
    }


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
