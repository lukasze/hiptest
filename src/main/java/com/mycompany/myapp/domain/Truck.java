package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A Truck.
 */
@Entity
@Table(name = "truck")
public class Truck implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "model")
    private String model;

    @Column(name = "engine")
    private String engine;

    @Column(name = "serial_no")
    private String serialNo;

    @ManyToMany
    @JoinTable(
        name = "rel_truck__driver",
        joinColumns = @JoinColumn(name = "truck_id"),
        inverseJoinColumns = @JoinColumn(name = "driver_id")
    )
    @JsonIgnoreProperties(value = { "trucks" }, allowSetters = true)
    private Set<Driver> drivers = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Truck id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getModel() {
        return this.model;
    }

    public Truck model(String model) {
        this.setModel(model);
        return this;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getEngine() {
        return this.engine;
    }

    public Truck engine(String engine) {
        this.setEngine(engine);
        return this;
    }

    public void setEngine(String engine) {
        this.engine = engine;
    }

    public String getSerialNo() {
        return this.serialNo;
    }

    public Truck serialNo(String serialNo) {
        this.setSerialNo(serialNo);
        return this;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public Set<Driver> getDrivers() {
        return this.drivers;
    }

    public void setDrivers(Set<Driver> drivers) {
        this.drivers = drivers;
    }

    public Truck drivers(Set<Driver> drivers) {
        this.setDrivers(drivers);
        return this;
    }

    public Truck addDriver(Driver driver) {
        this.drivers.add(driver);
        driver.getTrucks().add(this);
        return this;
    }

    public Truck removeDriver(Driver driver) {
        this.drivers.remove(driver);
        driver.getTrucks().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Truck)) {
            return false;
        }
        return id != null && id.equals(((Truck) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Truck{" +
            "id=" + getId() +
            ", model='" + getModel() + "'" +
            ", engine='" + getEngine() + "'" +
            ", serialNo='" + getSerialNo() + "'" +
            "}";
    }
}
