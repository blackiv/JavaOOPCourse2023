package ru.ipanteev.oop23.lambda;

import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        List<Person> namesList = Arrays.asList(
                new Person("Евгений", 20),
                new Person("Оксана", 13),
                new Person("Андрей", 45),
                new Person("Алексей", 29),
                new Person("Роман", 66),
                new Person("Андрей", 17),
                new Person("Андрей", 77),
                new Person("Варвара", 10),
                new Person("Варвара", 37)
        );

        List<String> uniqueNamesList = namesList.stream()
                .map(Person::getName)
                .distinct()
                .toList();

        System.out.println("Список уникальных имен.");
        System.out.println(uniqueNamesList.stream().collect(Collectors.joining(", ", "Имена: ", ".")));
        System.out.println();

        List<Person> lower18AgePersons = namesList.stream()
                .filter(p -> p.getAge() < 18)
                .toList();

        if (lower18AgePersons.isEmpty()) {
            System.out.println("В списке нет людей не достигших 18 лет.");
        } else {
            System.out.println("Список людей не достигших 18 лет:");
            System.out.println(lower18AgePersons);

            OptionalDouble lower18AverageAge = lower18AgePersons.stream()
                    .mapToInt(Person::getAge)
                    .average();

            lower18AverageAge.ifPresent(age -> System.out.printf("Средний возраст людей не достигших 18 лет - %.2f%n", age));
        }

        System.out.println();

        Map<String, Double> averageAgeByNameMap = namesList.stream()
                .collect(Collectors.groupingBy(Person::getName, Collectors.averagingDouble(Person::getAge)));

        System.out.println("Map по именам со значением среднего возраста");
        System.out.println(averageAgeByNameMap);

        System.out.println("Имена людей от 20 до 45 в порядке убывания возраста");
        System.out.println(namesList.stream()
                .filter(p -> p.getAge() >= 20 && p.getAge() <= 45)
                .sorted((p1, p2) -> p2.getAge() - p1.getAge())
                .map(Person::getName)
                .collect(Collectors.joining(", ")));
    }
}
