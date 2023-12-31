package net.safety.model;

import com.jsoniter.any.Any;

import java.util.*;

public class MedicalRecord {

    private String firstName;
    private String lastName;
    private Date birthDate;

    private List<String> medications;
    private Set<String> allergies;

    public MedicalRecord() {
    }

    public MedicalRecord(String firstName, String lastName, Date birthdate, List<String> medications, Set<String> allergies) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthdate;
        this.medications = medications;
        this.allergies = allergies;
    }

    public MedicalRecord(Date birthdate, List<String> medications, Set<String> allergies) {
        this.birthDate = birthdate;
        this.medications = medications;
        this.allergies = allergies;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
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
        return "{" +
                "firstName=" + firstName +
                ", lastName=" + lastName +
                ", birthDate=" + birthDate +
                ", medications=" + medications +
                ", allergies=" + allergies +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MedicalRecord)) return false;
        MedicalRecord that = (MedicalRecord) o;
        return Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName) && Objects.equals(birthDate, that.birthDate) && Objects.equals(medications, that.medications) && Objects.equals(allergies, that.allergies);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, birthDate, medications, allergies);
    }
}
