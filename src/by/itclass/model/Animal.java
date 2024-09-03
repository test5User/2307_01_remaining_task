package by.itclass.model;

import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.util.StringJoiner;

@AllArgsConstructor
public abstract class Animal implements Comparable<Animal>{
    private long chipNumber;
    private Genus genus;
    private String name;
    private LocalDate birthDate;
    private String breed;
    private String email;

    @Override
    public String toString() {
        return new StringJoiner(", ", genus.getValue() + "[", "]")
                .add("chipNumber=" + chipNumber)
                .add("name='" + name + "'")
                .add("birthDate=" + birthDate)
                .add("breed='" + breed + "'")
                .add("email='" + email + "'")
                .toString();
    }

    @Override
    public int compareTo(Animal animal) {
        return birthDate.compareTo(animal.birthDate);
    }
}
