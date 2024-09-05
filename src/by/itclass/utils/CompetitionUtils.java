package by.itclass.utils;

import by.itclass.exeptions.CompetitionException;
import by.itclass.model.*;
import lombok.experimental.UtilityClass;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Pattern;

import static by.itclass.utils.AnimalFactory.EMAIL_REGEX;

@UtilityClass
public class CompetitionUtils {
    public static final String PATH_TO_FILE = "src/by/itclass/resources/animals.txt";

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
            errors.put(matcher.group(), String.format("Error in string \"%s\" - %s", e.getErrorLine(), e.getCause().getMessage()));
        }
    }
}
