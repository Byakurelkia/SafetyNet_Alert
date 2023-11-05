package net.safety.dto;

import java.util.ArrayList;
import java.util.List;

public class PersonsByStationNumberDto {

    String firstName;
    String phoneNumber;
    int age;
    List<String> medications= new ArrayList<>();
    List<String> allergies = new ArrayList<>();

    public PersonsByStationNumberDto() {
    }

    public PersonsByStationNumberDto(String firstName,
                                     String phoneNumber,
                                     int age,
                                     List<String> medications,
                                     List<String> allergies) {
        this.firstName = firstName;
        this.phoneNumber = phoneNumber;
        this.age = age;
        this.medications = medications;
        this.allergies = allergies;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<String> getMedications() {
        return medications;
    }

    public void setMedications(List<String> medications) {
        this.medications = medications;
    }

    public List<String> getAllergies() {
        return allergies;
    }

    public void setAllergies(List<String> allergies) {
        this.allergies = allergies;
    }
}
