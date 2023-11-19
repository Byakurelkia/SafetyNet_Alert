package net.safety.dto;

import java.util.*;

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

    @Override
    public String toString() {
        return "PersonInfoByLastNameDto{" +
                "lastName='" + lastName + '\'' +
                ", address='" + address + '\'' +
                ", age=" + age +
                ", mail='" + mail + '\'' +
                ", medications=" + medications +
                ", allergies=" + allergies +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PersonInfoByLastNameDto)) return false;
        PersonInfoByLastNameDto that = (PersonInfoByLastNameDto) o;
        return age == that.age && Objects.equals(lastName, that.lastName) && Objects.equals(address, that.address) && Objects.equals(mail, that.mail) && Objects.equals(medications, that.medications) && Objects.equals(allergies, that.allergies);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lastName, address, age, mail, medications, allergies);
    }
}
