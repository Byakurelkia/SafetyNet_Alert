package net.safety.dto;

import java.util.Date;
import java.util.List;
import java.util.Set;

public class MedicalRecordDto {
    private String firstName;
    private String lastName;
    private String birthDate;

    private List<String> medications;
    private Set<String> allergies;

    public MedicalRecordDto(String firstName, String lastName, String birthdate, List<String> medications, Set<String> allergies) {
        this.firstName = firstName;
        this.lastName = lastName;
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

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
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
}
