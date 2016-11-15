package com.example.home.starbuzz;

/**
 * Created by Alexander on 26.09.2016.
 */

public class Drink {
    private String name;
    private String discription;
    private int imageResourseId;

    public Drink(String name, String discription, int imageResourseId) {
        this.name = name;
        this.discription = discription;
        this.imageResourseId = imageResourseId;
    }

    public static final Drink[] drinks = {
            new Drink("Latte", "Чашка восхЕтительного кофе с молочной пенкой", R.drawable.latte),
            new Drink("Cappuccino", "Эспрессо, горячее молоко и взбитая пенка с узором", R.drawable.capuchino),
            new Drink("Filtered", "Черный Американский кофе фильтрованный через бумагу", R.drawable.filtered)
    };

    public String getName() {
        return name;
    }

    public String getDiscription() {
        return discription;
    }

    public int getImageResourseId() {
        return imageResourseId;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
