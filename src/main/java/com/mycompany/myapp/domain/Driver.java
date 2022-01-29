package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A Driver.
 */
@Entity
@Table(name = "driver")
public class Driver implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "mileage")
    private Integer mileage;

    @ManyToMany(mappedBy = "drivers")
    @JsonIgnoreProperties(value = { "drivers" }, allowSetters = true)
    private Set<Truck> trucks = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Driver id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Driver name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return this.lastName;
    }

    public Driver lastName(String lastName) {
        this.setLastName(lastName);
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getMileage() {
        return this.mileage;
    }

    public Driver mileage(Integer mileage) {
        this.setMileage(mileage);
        return this;
    }

    public void setMileage(Integer mileage) {
        this.mileage = mileage;
    }

    public Set<Truck> getTrucks() {
        return this.trucks;
    }

    public void setTrucks(Set<Truck> trucks) {
        if (this.trucks != null) {
            this.trucks.forEach(i -> i.removeDriver(this));
        }
        if (trucks != null) {
            trucks.forEach(i -> i.addDriver(this));
        }
        this.trucks = trucks;
    }

    public Driver trucks(Set<Truck> trucks) {
        this.setTrucks(trucks);
        return this;
    }

    public Driver addTruck(Truck truck) {
        this.trucks.add(truck);
        truck.getDrivers().add(this);
        return this;
    }

    public Driver removeTruck(Truck truck) {
        this.trucks.remove(truck);
        truck.getDrivers().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Driver)) {
            return false;
        }
        return id != null && id.equals(((Driver) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Driver{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", mileage=" + getMileage() +
            "}";
    }
}
