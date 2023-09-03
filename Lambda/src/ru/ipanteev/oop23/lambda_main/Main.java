package ru.ipanteev.oop23.lambda_main;

import ru.ipanteev.oop23.person.Person;

import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        List<Person> list = Arrays.asList(
                new Person("Евгений", 20),
                new Person("Оксана", 13),
                new Person("Андрей", 45),
                new Person("Алексей", 29),
                new Person("Роман", 66),
                new Person("Андрей", 17),
                new Person("Варвара", 10)
        );

        List<String> uniqueNamesList = list.stream().
                map(Person::getName)
                .distinct()
                .toList();
        System.out.println("Список уникальных имен:");
        System.out.println(String.join(", ", uniqueNamesList));
        System.out.println();

        List<Person> lower18AgePersons = list.stream()
                .filter(p -> p.getAge() < 18)
                .toList();
        System.out.println("Список людей не достигших 18 лет:");
        System.out.println(lower18AgePersons);

        Optional<Integer> averageLover18Age = lower18AgePersons.stream()
                .map(Person::getAge)
                .reduce((age, age2) -> (age + age2) / 2);

        averageLover18Age.ifPresent(age -> System.out.printf("Средний возраст людей не достигших 18 лет - %d%n", age));

        System.out.println();

        Map<String, Integer> mapByNames = list.stream()
                .collect(Collectors.toMap(Person::getName, Person::getAge, (age, age2) -> (age + age2) / 2));
        System.out.println("Map по именам со значением среднего возраста");
        System.out.println(mapByNames);

        System.out.println("Имена людей от 20 до 45 в порядке убывания возраста");
        System.out.println(list.stream()
                .filter(p -> p.getAge() > 19 && p.getAge() < 46)
                .sorted((p1, p2) -> p2.getAge() - p1.getAge())
                .map(Person::getName)
                .collect(Collectors.joining(", ")));
    }
}
