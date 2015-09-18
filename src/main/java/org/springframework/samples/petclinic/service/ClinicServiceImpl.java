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
package org.springframework.samples.petclinic.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.model.Vet;
import org.springframework.samples.petclinic.model.Visit;
import org.springframework.samples.petclinic.repository.OwnerRepository;
import org.springframework.samples.petclinic.repository.PetRepository;
import org.springframework.samples.petclinic.repository.PetTypeRepository;
import org.springframework.samples.petclinic.repository.VetRepository;
import org.springframework.samples.petclinic.repository.VisitRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Mostly used as a facade for all Petclinic controllers
 * Also a placeholder for @Transactional and @Cacheable annotations
 *
 * @author Michael Isvy
 */
@Service
public class ClinicServiceImpl implements ClinicService {

    private PetRepository petRepository;
    private PetTypeRepository petTypeRepository;
    private VetRepository vetRepository;
    private OwnerRepository ownerRepository;
    private VisitRepository visitRepository;

    @Autowired
    public ClinicServiceImpl(PetRepository petRepository, PetTypeRepository petTypeRepository, VetRepository vetRepository, OwnerRepository ownerRepository, VisitRepository visitRepository) {
        this.petRepository = petRepository;
        this.petTypeRepository = petTypeRepository;
        this.vetRepository = vetRepository;
        this.ownerRepository = ownerRepository;
        this.visitRepository = visitRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<PetType> findPetTypes() throws DataAccessException {
        return (List<PetType>) petTypeRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public PetType findPetTypeById(int id) throws DataAccessException {
        return petTypeRepository.findOne(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Owner findOwnerById(int id) throws DataAccessException {
        //return ownerRepository.findOne(id);
        return ownerRepository.findById(id);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Owner> findOwnerByLastName(String lastName) throws DataAccessException {
        return ownerRepository.findByLastName(lastName);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Owner> findOwners() throws DataAccessException {
        return (List<Owner>) ownerRepository.findAll();
    }

	@Override
    @Transactional(readOnly = true)	
	public List<Owner> findByFirstNameContaining(String firstName) throws DataAccessException {
		return ownerRepository.findByFirstNameContaining(firstName);
	}

	@Override
    @Transactional
    public Owner saveOwner(Owner owner) throws DataAccessException {
        return ownerRepository.save(owner);
    }

	@Override
    @Transactional	
	public void removeOwner(int id) throws DataAccessException {
		ownerRepository.delete(id);
	}

	@Override
	@Transactional
	public Visit findVisitById(int id) throws DataAccessException {
		return visitRepository.findOne(id);
	}

    @Override
    @Transactional
    public Visit saveVisit(Visit visit) throws DataAccessException {
        return visitRepository.save(visit);
    }

	@Override
	@Transactional
	public void removeVisit(int id) throws DataAccessException {
		visitRepository.delete(id);
	}

    @Override
    @Transactional(readOnly = true)
    public Pet findPetById(int id) throws DataAccessException {
        return petRepository.findOne(id);
    }

    @Override
    @Transactional
    public Pet savePet(Pet pet) throws DataAccessException {
        return petRepository.save(pet);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "vets")
    public List<Vet> findVets() throws DataAccessException {
        return (List<Vet>) vetRepository.findAll();
    }

	@Override
	@Transactional
	public void removePet(int id) throws DataAccessException {
		petRepository.delete(id);
	}
}
