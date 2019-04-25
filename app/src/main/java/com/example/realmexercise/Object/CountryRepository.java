package com.example.realmexercise.Object;

import java.util.ArrayList;
import java.util.List;

public class CountryRepository {

    List<Country> countries;

    public CountryRepository() {
        countries = new ArrayList<>();
        setRepository();
    }

    private void setRepository(){
        countries.add(new Country(1, "España"));
        countries.add(new Country(2, "Portugal"));
        countries.add(new Country(3, "Italia"));
    }

    public List<Country> getRepository(){
        return countries;
    }

    public void addCountry(Country country){
        countries.add(country);
    }

    public void removeCountry(Country country){
        countries.remove(country);
    }
}
