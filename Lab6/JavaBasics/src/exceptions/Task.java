package exceptions;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

class InvalidGradeException extends Exception {
    public InvalidGradeException(String message) {
        super(message);
    }
}

class NoDisciplinesException extends Exception {
    public NoDisciplinesException(String message) {
        super(message);
    }
}

class NoStudentsInGroupException extends Exception {
    public NoStudentsInGroupException(String message) {
        super(message);
    }
}

class NoGroupsInFacultyException extends Exception {
    public NoGroupsInFacultyException(String message) {
        super(message);
    }
}

public class Task {

    public static void main(String[] args) {
        try {
            class University {
                String name;
                int facultyCount;
            }
            University university = new University();
            university.name = "BSTU";
            Faculty faculty = new Faculty();
            faculty.name = "FIT";
            university.facultyCount++;
            if (university.facultyCount == 0)
                throw new NoGroupsInFacultyException("Должен быть хотябы 1 факультет в университете");
            Group group = new Group();
            group.number = 4;
            group.faculty = faculty;
            faculty.groupCount++;
            if (faculty.groupCount == 0)
                throw new NoGroupsInFacultyException("Должна быть хотябы 1 группа на факультете");

            var student1 = new Student();
            student1.id = 1;
            student1.name = "Name1";
            student1.surname = "SurName1";
            student1.group = group;
            student1.grades.put("Математика", -1);
            student1.grades.put("Программирование", 8);
            student1.grades.put("БД", 6);
            student1.grades.put("Сети", 7);
            group.studentCount++;
            if (group.studentCount == 0)
                throw new NoStudentsInGroupException("Должен быть хотябы 1 студент в группе");
            if (student1.grades.size() == 0)
                throw new NoDisciplinesException("Должен быть хотябы 1 предмет");

            student1.grades.forEach((key, value) -> {
                if (value < 0 || value > 10)
                    try {
                        throw new InvalidGradeException("Оценка должна быть в пределе от 0 до 10");
                    } catch (InvalidGradeException e) {
                        e.printStackTrace();
                    }
            });
            AtomicReference<Double> avg = new AtomicReference<>((double) 0);
            student1.grades.forEach((key, value) -> avg.updateAndGet(v -> (v + value)));
            System.out.println("Средний балл по всем предметам студента: " + (avg.get() / student1.grades.size()));
        } catch (NoDisciplinesException | NoStudentsInGroupException | NoGroupsInFacultyException e) {
            e.printStackTrace();
        }
    }
}

class Student {
    long id;
    String name;
    String surname;
    Group group;
    Map<String, Integer> grades = new LinkedHashMap<>();
}

class Group {
    int number;
    int studentCount;
    Faculty faculty;
}

class Faculty {
    String name;
    int groupCount;
}
