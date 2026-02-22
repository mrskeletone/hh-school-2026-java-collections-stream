package tasks;

import common.Person;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/*
Далее вы увидите код, который специально написан максимально плохо.
Постарайтесь без ругани привести его в надлежащий вид
P.S. Код в целом рабочий (не везде), комментарии оставлены чтобы вам проще понять чего же хотел автор
P.P.S Здесь ваши правки необходимо прокомментировать (можно в коде, можно в PR на Github)
 */
public class Task9 {

  private long count;

  // Костыль, эластик всегда выдает в топе "фальшивую персону".
  // Конвертируем начиная со второй
  public List<String> getNames(List<Person> persons) {
    if (persons.isEmpty()) {//вместо проверки на 0 использовать нужно isEmpty()
      return Collections.emptyList();
    }
    //вместо удаления просто пропустим 1
    return persons.stream().skip(1).map(Person::firstName).collect(Collectors.toList());
  }

  // Зачем-то нужны различные имена этих же персон (без учета фальшивой разумеется)
  public Set<String> getDifferentNames(List<Person> persons) {
    return new HashSet<>(getNames(persons));// Убран distinct так как это set и там уже только уникальные
    // и так как там всего одна терминальная операция, stream заменяется на конструктор сета
  }

  // Тут фронтовая логика, делаем за них работу - склеиваем ФИО
  public String convertPersonToString(Person person) {
    StringBuilder result = new StringBuilder();// Конкатенацию лучше делать через StringBuilder
    // Дальше везде заменена на метод StringBuilder append
    if (person.secondName() != null) {
      result.append(person.secondName());
    }

    if (person.firstName() != null) {
      result.append(" ").append(person.firstName());
    }

    if (person.secondName() != null) {
      result.append(" ").append(person.secondName());
    }
    return result.toString();
  }

  // словарь id персоны -> ее имя
  public Map<Integer, String> getPersonNames(Collection<Person> persons) {
    // Бесполезно указывать такой низкий размер, он начнет расширяться после первого добавления
    // ведь словарь расширяется при заполнении на 3/4
    Map<Integer, String> map = new HashMap<>(persons.size());
    for (Person person : persons) {
      if (!map.containsKey(person.id())) {
        map.put(person.id(), convertPersonToString(person));
      }
    }
    return map;
  }

  // есть ли совпадающие в двух коллекциях персоны?
  public boolean hasSamePersons(Collection<Person> persons1, Collection<Person> persons2) {
    boolean has = false;
    for (Person person1 : persons1) {
      if (!has)// Добавлена проверка на то что совпадение не найдено
        for (Person person2 : persons2) {
          if (person1.equals(person2)) {
            has = true;
            break;//добавлен break, чтобы выйти из цикла после нахождения
          }
        }
      else break;// Если совпадение найдено, выйти из цикла
    }
    return has;
  }

  // Посчитать число четных чисел
  public long countEven(Stream<Integer> numbers) {
    // Заменяем foreach на вызов count в stream
    count = numbers.filter(num -> num % 2 == 0).count();
    return count;
  }

  // Загадка - объясните почему assert тут всегда верен
  // Пояснение в чем соль - мы перетасовали числа, обернули в HashSet, а toString() у него вернул их в сортированном порядке
  void listVsSet() {
    List<Integer> integers = IntStream.rangeClosed(1, 10000).boxed().collect(Collectors.toList());
    List<Integer> snapshot = new ArrayList<>(integers);
    Collections.shuffle(integers);
    Set<Integer> set = new HashSet<>(integers);
    assert snapshot.toString().equals(set.toString());
    //Изначально мы создаем лист упорядоченных чисел
    //Создаем копию
    //Переставляем изначальный массив(убираем порядок)
    //Создаем сет со значениями листа, так как в Integer
    //hash это само число, hashSet заполняется числами подряд с поправкой на размер хэш-таблицы
    //но так как она расширяется когда заполняется на 3/4
    //по итогу выходит тот же массив чисел подряд
    //который читается с 0 и до конца
    //что приводит к постоянному true в assert
  }
}
