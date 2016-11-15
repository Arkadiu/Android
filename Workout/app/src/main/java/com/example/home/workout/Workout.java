package com.example.home.workout;

/**
 * Created by Alexander on 27.09.2016.
 */

public class Workout {
    private String name;
    private String description;


    private int imageResourse;

    public Workout(String name, String description, int imageResourse) {
        this.name = name;
        this.description = description;
        this.imageResourse = imageResourse;
    }

    public static final Workout[] workouts = {
            new Workout("Релаксация конечностей", "5 отжиманий \n10 приседаний\n15 подтягиваний", R.drawable.relax),
            new Workout("Ядро Агоний", "100 подтягиваний\n100 отжиманий\n100 присиданий", R.drawable.agony),
            new Workout("Особое упражнение", "5 подтягивай\n10 отжиманий\n15 присиданий", R.drawable.special),
            new Workout("Сила и выносливость", "500м бег\n21 х 1.5 качели\n21 подтягиваний", R.drawable.s2),
            new Workout("Мастурбация одной рукой", "15 фрикций на левую руку\n23 фрикций на правую руку,\n25 поворотно отрывных фрикций с элементами страсти", R.drawable.man),
            new Workout("Женский разогрев", "20 кругов пальцем по чс\n23 круга пальцем против чс\n100-200 поступательно-проникательных движений 2 пальцами", R.drawable.women)
    };

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getImageResourse() {
        return imageResourse;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
