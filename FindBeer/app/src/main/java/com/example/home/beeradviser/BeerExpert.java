package com.example.home.beeradviser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alexander on 07.09.2016.
 */
public class BeerExpert {

    public List<String> getBrands(String color) {

        List<String> brands = new ArrayList<>();
        if (color.equalsIgnoreCase("amber")) {
            brands.add("Jack Amber");
            brands.add("Red Moose");
        } else if (color.equalsIgnoreCase("special")) {
            brands.add("Балтика 7");
            brands.add("Балтика 9");
        } else {
            brands.add("Jail Pale Ale");
            brands.add("Gout Stout");
        }
        return brands;
    }
}
