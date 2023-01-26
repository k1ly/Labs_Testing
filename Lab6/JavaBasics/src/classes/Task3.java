package classes;

import java.util.Arrays;

class Patient {

    int id;
    String surname;
    String name;
    String thirdName;
    String address;
    String phone;
    long medCartNum;
    String diagnosis;

    public Patient(int id, String surname, String name, String thirdName,
                   String address, String phone,
                   long medCartNum, String diagnosis) {
        this.id = id;
        this.surname = surname;
        this.name = name;
        this.thirdName = thirdName;
        this.address = address;
        this.phone = phone;
        this.medCartNum = medCartNum;
        this.diagnosis = diagnosis;
    }

    @Override
    public String toString() {
        return "{" + surname + "  " + name + "  " + thirdName + "}";
    }
}

public class Task3 {

    public static void main(String[] args) {
        Patient[] array = {new Patient(1, "SurName1", "Name1", "ThirdName1", "aaa", "111", 34, "Простуда"),
                new Patient(2, "SurName2", "Name2", "ThirdName2", "bbb", "222", 48, "Рак"),
                new Patient(3, "SurName3", "Name3", "ThirdName3", "ccc", "333", 17, "Рак")};
        String diagnosis = "Рак";
        var patientsWithDiagnosis = Arrays.stream(array)
                .filter(patient -> patient.diagnosis.equals(diagnosis)).toList();
        System.out.println("список пациентов, имеющих данный диагноз: " + diagnosis);
        patientsWithDiagnosis.forEach(System.out::println);
        int medCartNumFrom = 5;
        int medCartNumTo = 41;
        var patientsWithMedCartNumInterval = Arrays.stream(array)
                .filter(patient -> patient.medCartNum >= medCartNumFrom && patient.medCartNum <= medCartNumTo).toList();
        System.out.println("список пациентов, номер медицинской карты которых находится в заданном интервале: " +
                medCartNumFrom + " --> " + medCartNumTo);
        patientsWithMedCartNumInterval.forEach(System.out::println);
    }
}
