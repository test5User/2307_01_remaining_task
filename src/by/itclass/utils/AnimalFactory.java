package by.itclass.utils;

import by.itclass.exeptions.CompetitionException;
import by.itclass.model.Animal;
import by.itclass.model.Cat;
import by.itclass.model.Dog;
import by.itclass.model.Genus;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class AnimalFactory {
    public static final String CHIP_REGEX = "(?=\\d{15}\\b)(?:112|643)09(?:81|56)\\d{8}";
    public static final String EMAIL_REGEX = "[a-zA-Z0-9^$|-]+(?:\\.[a-zA-Z0-9]+)*@[a-zA-Z0-9]+(?:\\.[a-zA-Z0-9]+)*$";
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-y");

    public static Animal getInstance(String textString) throws CompetitionException {
        var stringArray = textString.split("[,;]");
        try {
            var chipNumber = Long.parseLong(validateByRegex(stringArray[0], CHIP_REGEX));
            var name = validateByEmpty(stringArray[2]);
            var birthDate = LocalDate.parse(stringArray[3], FORMATTER);
            var breed = validateByEmpty(stringArray[4]);
            var email = validateByRegex(stringArray[5], EMAIL_REGEX);
            return "cat".equalsIgnoreCase(stringArray[1])
                    ? new Cat(chipNumber, Genus.CAT, name, birthDate, breed, email)
                    : new Dog(chipNumber, Genus.of(stringArray[1]), name, birthDate, breed, email);
        } catch (IllegalStateException e) {
            throw new CompetitionException(e, textString);
        }
    }

    private static String validateByRegex(String value, String regex) {
        if (value.matches(regex)) {
            return value;
        }
        throw new IllegalStateException("Chip number or email is invalid");
    }

    private static String validateByEmpty(String value) {
        if (!value.isEmpty()) {
            return value;
        }
        throw new IllegalStateException("Name or Breed is empty");
    }


}
