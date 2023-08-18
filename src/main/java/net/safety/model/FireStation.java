package net.safety.model;

import jakarta.persistence.*;

import java.util.Objects;
import java.util.Set;

@Entity
public class FireStation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String adress;

    @Column(nullable = false)
    private int stationNumber;

    @OneToMany(mappedBy = "fireStation", fetch = FetchType.LAZY)
    private Set<Person> persons;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FireStation)) return false;
        FireStation that = (FireStation) o;
        return id == that.id
                && stationNumber == that.stationNumber
                && Objects.equals(adress, that.adress)
                && Objects.equals(persons, that.persons);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, adress, stationNumber);
    }
}
