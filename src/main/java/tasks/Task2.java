package tasks;

import common.Person;

import java.util.*;
import java.util.stream.Collectors;

/*
Задача 2
На вход принимаются две коллекции объектов Person и величина limit
Необходимо объеденить обе коллекции
отсортировать персоны по дате создания и выдать первые limit штук.
 */
public class Task2 {

  public static List<Person> combineAndSortWithLimit(Collection<Person> persons1,
                                                     Collection<Person> persons2,
                                                     int limit) {
   List<Person> personList=new ArrayList<>();
   personList.addAll(persons1);
   personList.addAll(persons2);
    return personList.stream()
        .sorted(Comparator.comparing(Person::createdAt))
        .limit(limit)
        .collect(Collectors.toList());
  }
}
