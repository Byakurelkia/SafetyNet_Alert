package net.safety.dto;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PersonsInfoByAddressOrStationNumberDto {

    String lastName;
    String phoneNumber;
    int age;
    List<String> medications = new ArrayList<>();
    Set<String> allergies = new HashSet<>();

    public PersonsInfoByAddressOrStationNumberDto() {
    }

    public PersonsInfoByAddressOrStationNumberDto(String lastName, String phoneNumber, int age, List<String> medications, Set<String> allergies) {
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.age = age;
        this.medications = medications;
        this.allergies = allergies;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public void addMedications(String medications) {
        this.medications.add(medications);
    }

    public void setMedications(List<String> medications){
        this.medications = medications;
    }

    public Set<String> getAllergies() {
        return allergies;
    }

    public void setAllergies(Set<String> allergies) {
        this.allergies = allergies;
    }

    public void addAllergies(String allergies) {
        this.allergies.add(allergies);
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    @Override
    public String toString() {
        return "PersonsInfoByAddressOrStationNumberDto{" +
                "lastName='" + lastName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", age=" + age +
                ", medications=" + medications +
                ", allergies=" + allergies +
                '}';
    }
}
