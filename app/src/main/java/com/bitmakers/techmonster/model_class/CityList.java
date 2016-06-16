package com.bitmakers.techmonster.model_class;

import java.util.ArrayList;

/**
 * Created by MAHBUB_MUKUL on 6/9/2016.
 */
public class CityList {
    private String id;
    private String name;
    private String country_id;
    private ArrayList<DistList> districts;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry_id() {
        return country_id;
    }

    public void setCountry_id(String country_id) {
        this.country_id = country_id;
    }

    public ArrayList<DistList> getDistricts() {
        return districts;
    }

    public void setDistricts(ArrayList<DistList> districts) {
        this.districts = districts;
    }
}
