package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;

/**
 * A Glasses.
 */
@Entity
@Table(name = "glasses")
public class Glasses implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "model")
    private String model;

    @Column(name = "front")
    private String front;

    @Column(name = "temples")
    private String temples;

    @Column(name = "lenses")
    private String lenses;

    @JsonIgnoreProperties(value = { "glasses" }, allowSetters = true)
    @OneToOne(mappedBy = "glasses")
    private Person person;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Glasses id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getModel() {
        return this.model;
    }

    public Glasses model(String model) {
        this.setModel(model);
        return this;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getFront() {
        return this.front;
    }

    public Glasses front(String front) {
        this.setFront(front);
        return this;
    }

    public void setFront(String front) {
        this.front = front;
    }

    public String getTemples() {
        return this.temples;
    }

    public Glasses temples(String temples) {
        this.setTemples(temples);
        return this;
    }

    public void setTemples(String temples) {
        this.temples = temples;
    }

    public String getLenses() {
        return this.lenses;
    }

    public Glasses lenses(String lenses) {
        this.setLenses(lenses);
        return this;
    }

    public void setLenses(String lenses) {
        this.lenses = lenses;
    }

    public Person getPerson() {
        return this.person;
    }

    public void setPerson(Person person) {
        if (this.person != null) {
            this.person.setGlasses(null);
        }
        if (person != null) {
            person.setGlasses(this);
        }
        this.person = person;
    }

    public Glasses person(Person person) {
        this.setPerson(person);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Glasses)) {
            return false;
        }
        return id != null && id.equals(((Glasses) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Glasses{" +
            "id=" + getId() +
            ", model='" + getModel() + "'" +
            ", front='" + getFront() + "'" +
            ", temples='" + getTemples() + "'" +
            ", lenses='" + getLenses() + "'" +
            "}";
    }
}
