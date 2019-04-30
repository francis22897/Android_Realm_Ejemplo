package com.example.realmexercise.Object;

import com.example.realmexercise.Realm.RealmRepository;

public class CountryRepository {

    public static void addCountriesToBD() {
        RealmRepository.insertCountry(new Country("España"));
        RealmRepository.insertCountry(new Country("Portugal"));
        RealmRepository.insertCountry(new Country("Italia"));
    }

}
