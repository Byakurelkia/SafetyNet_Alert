package net.safety.model;

import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, updatable = false)
    private String firstName;
    @Column(nullable = false, updatable = false)
    private String lastName;
    @Column(nullable = false)
    private String birthDate;
    @Column(nullable = false)
    private String adress;
    @Column(nullable = false)
    private String city;
    @Column(nullable = false)
    private String mail;
    @Column(nullable = false)
    private String phoneNumber;
    @Column(nullable = false)
    private String zipCode;

    @OneToMany(mappedBy = "person", fetch = FetchType.LAZY)
    private Set<MedicalRecord> medicalRecords;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "fireStation_id") //ouvre une colonne fireStation_id sur la table Person
    private FireStation fireStation;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person)) return false;
        Person person = (Person) o;
        return id == person.id
                && Objects.equals(firstName, person.firstName)
                && Objects.equals(lastName, person.lastName)
                && Objects.equals(birthDate, person.birthDate)
                && Objects.equals(adress, person.adress)
                && Objects.equals(city, person.city)
                && Objects.equals(mail, person.mail)
                && Objects.equals(phoneNumber, person.phoneNumber)
                && Objects.equals(zipCode, person.zipCode)
                && Objects.equals(medicalRecords, person.medicalRecords)
                && Objects.equals(fireStation, person.fireStation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, birthDate,mail, phoneNumber);
    }
}
