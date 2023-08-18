package net.safety.model;

import jakarta.persistence.*;

import java.util.Objects;
import java.util.Set;

@Entity
public class MedicalRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(updatable = false)
    private String firstName;
    @Column(updatable = false)
    private String lastName;
    private String birthDate;
    private Set<String> medicaments;
    private Set<String> allergies;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "person_id")
    private Person person;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MedicalRecord)) return false;
        MedicalRecord that = (MedicalRecord) o;
        return id == that.id && Objects.equals(firstName, that.firstName)
                && Objects.equals(lastName, that.lastName)
                && Objects.equals(birthDate, that.birthDate)
                && Objects.equals(medicaments, that.medicaments)
                && Objects.equals(allergies, that.allergies)
                && Objects.equals(person, that.person);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, birthDate);
    }
}
