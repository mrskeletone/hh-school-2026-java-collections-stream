package tasks;

import common.Person;
import common.PersonService;
import common.PersonWithResumes;
import common.Resume;

import java.util.*;
import java.util.stream.Collectors;

/*
  Еще один вариант задачи обогащения
  На вход имеем коллекцию персон
  Сервис умеет по personId искать их резюме (у каждой персоны может быть несколько резюме)
  На выходе хотим получить объекты с персоной и ее списком резюме
 */
public class Task8 {
  private final PersonService personService;

  public Task8(PersonService personService) {
    this.personService = personService;
  }

  public Set<PersonWithResumes> enrichPersonsWithResumes(Collection<Person> persons) {
    Map<Integer, List<Resume>> resumes = personService.findResumes(persons.stream()
            .map(Person::id)
            .collect(Collectors.toSet())).stream()
        .collect(Collectors.groupingBy(Resume::personId));
    return persons.stream()
        .map(person -> {
          Set<Resume> resumeSet = new HashSet<>();
          if (resumes.containsKey(person.id())) {
            resumeSet.addAll(resumes.get(person.id()));
          }
          return new PersonWithResumes(person, resumeSet);
        })
        .collect(Collectors.toSet());
  }
}
