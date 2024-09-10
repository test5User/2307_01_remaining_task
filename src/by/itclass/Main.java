package by.itclass;

import by.itclass.model.Cat;
import by.itclass.model.Dog;
import by.itclass.model.Genus;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static by.itclass.utils.CompetitionUtils.*;

public class Main {
    public static void main(String[] args) {
//        Genus animal = Genus.of("dog");
//        System.out.println("I an a " + animal + " and my value is " + animal.getValue());
        var cats = new ArrayList<Cat>();
//List<Cat> catsOld = new ArrayList<>();
        var dogs = new ArrayList<Dog>();
        var errors = new HashMap<String, String>();

        parseFile(cats, dogs, errors);
        //printResults(cats, dogs, errors);

        var sortedCats = sortByBirthDate(cats);
        var sortedDogs = sortByBirthDate(dogs);

        var youngCats = filterAnimals(sortedCats, true);
        var oldCats = filterAnimals(sortedCats, false);
        var youngDog = filterAnimals(sortedDogs, true);
        var oldDogs = filterAnimals(sortedDogs, false);

        printResults(youngCats, youngDog, oldCats, oldDogs);
    }
}
