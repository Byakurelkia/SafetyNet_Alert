package net.safety.dto;

import java.util.Date;
import java.util.List;
import java.util.Set;

public class MedicalRecordUpdateRequest {

    private Date birthDate;
    private List<String> medications;
    private Set<String> allergies;

    public MedicalRecordUpdateRequest() {
    }

    public MedicalRecordUpdateRequest(Date birthdate, List<String> medications, Set<String> allergies) {
        this.birthDate = birthdate;
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
}
