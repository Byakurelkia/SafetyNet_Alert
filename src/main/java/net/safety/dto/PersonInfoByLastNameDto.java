package net.safety.dto;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PersonInfoByLastNameDto
{
    String lastName;
    String address;
    int age;
    String mail;
    List<String> medications = new ArrayList<>();
    Set<String> allergies = new HashSet<>();

    public PersonInfoByLastNameDto() {
    }

    public PersonInfoByLastNameDto(
            String lastName,
            String address,
            int age,
            String mail,
            List<String> medications,
            Set<String> allergies) {
        this.lastName = lastName;
        this.address = address;
        this.age = age;
        this.mail = mail;
        this.medications = medications;
        this.allergies = allergies;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public List<String> getMedications() {
        return medications;
    }

    public void setMedications(List<String> medications) {
        this.medications = medications;
    }

    public Set<String> getAllergies() {
        return allergies;
    }

    public void setAllergies(Set<String> allergies) {
        this.allergies = allergies;
    }
}
