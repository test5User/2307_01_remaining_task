package by.itclass;

import by.itclass.model.Cat;
import by.itclass.model.Dog;
import by.itclass.model.Genus;
import by.itclass.utils.CompetitionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Main {
    public static void main(String[] args) {
//        Genus animal = Genus.of("dog");
//        System.out.println("I an a " + animal + " and my value is " + animal.getValue());
        var cats = new ArrayList<Cat>();
        //List<Cat> catsOld = new ArrayList<>();
        var dogs = new ArrayList<Dog>();
        var errors = new HashMap<String, String>();
        CompetitionUtils.parseFile(cats, dogs, errors);

        cats.forEach(System.out::println);
        dogs.forEach(System.out::println);
    }
}
