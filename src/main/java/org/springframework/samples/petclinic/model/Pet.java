/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedAttributeNode;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PropertyComparator;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonView;

/**
 * Simple business object representing a pet.
 *
 * @author Ken Krebs
 * @author Juergen Hoeller
 * @author Sam Brannen
 */
@Entity
@NamedEntityGraph(name = "Pet.detail", attributeNodes = @NamedAttributeNode("visits"))
@Table(name = "pets")
public class Pet extends NamedEntity {

    @Column(name = "birth_date")
    @JsonView(Views.PetSummary.class)
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private DateTime birthDate;

    @ManyToOne
    @JoinColumn(name = "type_id")
    @JsonView(Views.PetSummary.class)
    private PetType type;
	
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "owner_id")
    @JsonView(Views.PetSummary.class)
    private Owner owner;

    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pet", fetch = FetchType.EAGER)
    private List<Visit> visits;


    public void setBirthDate(DateTime birthDate) {
        this.birthDate = birthDate;
    }

    public DateTime getBirthDate() {
        return this.birthDate;
    }

    public void setType(PetType type) {
        this.type = type;
    }

    public PetType getType() {
        return this.type;
    }

    protected void setOwner(Owner owner) {
        this.owner = owner;
    }

    public Owner getOwner() {
        return this.owner;
    }

    protected void setVisitsInternal(List<Visit> visits) {
        this.visits = visits;
    }

    protected List<Visit> getVisitsInternal() {
        if (this.visits == null) {
            this.visits = new ArrayList<Visit>();
        }
        return this.visits;
    }

    public List<Visit> getMutableSortVisits() {
        List<Visit> sortedVisits = new ArrayList<Visit>(getVisitsInternal());
        PropertyComparator.sort(sortedVisits, new MutableSortDefinition("date", false, false));
        return Collections.unmodifiableList(sortedVisits);
    }

    public void addVisit(Visit visit) {
        getVisitsInternal().add(visit);
        visit.setPet(this);
    }

	public List<Visit> getVisits() {
		return visits;
	}

	public void setVisits(List<Visit> visits) {
		this.visits = visits;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Pet [birthDate=").append(birthDate).append(", type=").append(type).append(", owner=").append(owner).append(", visits=").append(visits)
				.append("]");
		return builder.toString();
	}
}
