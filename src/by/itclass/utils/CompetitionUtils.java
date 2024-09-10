package by.itclass.utils;

import by.itclass.exeptions.CompetitionException;
import by.itclass.model.*;
import lombok.experimental.UtilityClass;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Predicate;
import java.util.regex.Pattern;

import static by.itclass.utils.AnimalFactory.EMAIL_REGEX;

@UtilityClass
public class CompetitionUtils {
    public static final String PATH_TO_FILE = "src/by/itclass/resources/animals.txt";
    public static final LocalDate AGE_DELIMITER = LocalDate.now().minusYears(4);
    public static final Predicate<Animal> YOUNG = it -> it.getBirthDate().isAfter(AGE_DELIMITER);
    public static final Predicate<Animal> OLD = it -> it.getBirthDate().isBefore(AGE_DELIMITER);

    public static void parseFile(List<Cat> cats, List<Dog> dogs, Map<String, String> errors) {
        try (var sc = new Scanner(new FileReader(PATH_TO_FILE))){
            while (sc.hasNextLine()) {
                //System.out.println(sc.nextLine());
                fillingCollections(sc.nextLine(), cats, dogs, errors);
            }
        } catch (FileNotFoundException e) {
            System.out.printf("File hasn't found by path %s%n", PATH_TO_FILE);
            System.exit(1);
        }
    }

    private static void fillingCollections(String textLine, List<Cat> cats, List<Dog> dogs,
                                           Map<String, String> errors) {
        try {
            var animal = AnimalFactory.getInstance(textLine);
            if (animal instanceof Cat) {
                cats.add((Cat) animal);
            } else {
                dogs.add((Dog) animal);
            }
        } catch (CompetitionException e) {
            processException(errors, e);
        }
    }

    private static void processException(Map<String, String> errors, CompetitionException e) {
        var pattern = Pattern.compile(EMAIL_REGEX);
        var matcher = pattern.matcher(e.getErrorLine());
        if (matcher.find()) {
            errors.put(matcher.group(), String.format("Error in string \"%s\" - %s%n", e.getErrorLine(), e.getCause().getMessage()));
        }
    }

    public static <T extends Animal> List<T> sortByBirthDate(List<T> animals) {
        return animals.stream()
                .sorted()
                .toList();
    }

    public static void printResults(List<Cat> cats, List<Dog> dogs,
                                    Map<String, String> errors) {
        System.out.println("Cats list size: " + cats.size());
        printCollection(cats);
        System.out.println("Dogs list size: " + dogs.size());
        printCollection(dogs);
        printMap(errors);
    }

    private static <T> void printCollection(List<T> collection) {
        collection.forEach(System.out::println);
    }

    private static void printMap(Map<String, String> errors) {
        if(!errors.isEmpty()) {
            System.out.println("Errors quantity: " + errors.size());
            errors.forEach((k,v) -> System.out.print(k + " : " + v));
        }
    }

    public static <T extends Animal> List<T> filterAnimals(List<T> participant, boolean isYoung) {
        return participant.stream()
                .filter(isYoung ? YOUNG : OLD)
                .toList();
    }

    public static void printResults(List<Cat> youngCats, List<Dog> youngDogs,
                                    List<Cat> oldCats, List<Dog> oldDogs) {
        System.out.println("First day participants:");
        printResults(youngCats, youngDogs, new HashMap<>());
        System.out.println("Second day participants:");
        printResults(oldCats, oldDogs, new HashMap<>());
    }
}
