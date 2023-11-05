package net.safety.dto;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class MedicalRecordDto {

    private Date birthDate;

    private List<String> medications;
    private Set<String> allergies;

    public MedicalRecordDto(Date birthDate, List<String> medications, Set<String> allergies) {
        this.birthDate = birthDate;
        this.medications = medications;
        this.allergies = allergies;
    }

    public MedicalRecordDto(){}

    public MedicalRecordDto(List<String> medications, Set<String> allergies) {
        this.medications = medications;
        this.allergies = allergies;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MedicalRecordDto)) return false;
        MedicalRecordDto that = (MedicalRecordDto) o;
        return Objects.equals(birthDate, that.birthDate) && Objects.equals(medications, that.medications) && Objects.equals(allergies, that.allergies);
    }

    @Override
    public int hashCode() {
        return Objects.hash(birthDate, medications, allergies);
    }
}
