package com.example.realmexercise.Object;

import com.example.realmexercise.Realm.RealmAplication;

import io.realm.RealmObject;

public class Place extends RealmObject {

    private int id;
    private String name;
    private String description;

    public Place(){}

    public Place(String name, String description) {
        this.id = RealmAplication.placeId.incrementAndGet();
        this.name = name;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String nombre) {
        this.name = nombre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
