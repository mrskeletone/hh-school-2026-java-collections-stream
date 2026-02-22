package tasks;

import common.ApiPersonDto;
import common.Person;
import common.PersonConverter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/*
Задача 5
Расширим предыдущую задачу.
Есть список персон, и словарь сопоставляющий id каждой персоны и id региона
Необходимо выдать список персон ApiPersonDto, с правильно проставленными areaId
Конвертер одной персоны дополнен!
 */
public class Task5 {

  private final PersonConverter personConverter;

  public Task5(PersonConverter personConverter) {
    this.personConverter = personConverter;
  }

  public List<ApiPersonDto> convert(List<Person> persons, Map<Integer, Integer> personAreaIds) {
    return persons.stream()
        .map(person -> convertPersonToApiPersonDto(person, personAreaIds.get(person.id())))
        .collect(Collectors.toList());
  }

  public ApiPersonDto convertPersonToApiPersonDto(Person person, Integer personAreaIds) {
    ApiPersonDto apiPersonDto = new ApiPersonDto();
    apiPersonDto.setCreated(person.createdAt().toEpochMilli());
    apiPersonDto.setId(person.id().toString());
    apiPersonDto.setName(person.firstName());
    apiPersonDto.setAreaId(personAreaIds);
    return apiPersonDto;
  }
}
