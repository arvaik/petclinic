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
package org.springframework.samples.petclinic.rest;

import java.util.Collection;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.model.Views;
import org.springframework.samples.petclinic.service.ClinicService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

@RestController
@RequestMapping("/pet")
public class PetRestController {
	
	private final static Logger LOGGER = LoggerFactory.getLogger(PetRestController.class.getName());

	private final ClinicService clinicService;


    @Autowired
    public PetRestController(ClinicService clinicService) {
        this.clinicService = clinicService;
    }

    @RequestMapping(value = "/types", method = RequestMethod.GET)
    public Collection<PetType> showPetTypes() {
        return this.clinicService.findPetTypes();
    }

    /**
     * Custom handler for displaying an pet.
     *
     * @param petId the ID of the pet to display
     * @return 
     * @return a ModelMap with the model attributes for the view
     */
    @JsonView(Views.PetSummary.class)
    @RequestMapping(value = "/{petId}", method = RequestMethod.GET)
    public Pet showPet(@PathVariable("petId") int petId) {
        return this.clinicService.findPetById(petId);
    }

    /**
     * Custom handler for remove an pet.
     *
     * @param petId the ID of the pet to remove
     */
    @RequestMapping(value = "/{petId}", method = RequestMethod.DELETE)
    public void removePet(@PathVariable("petId") int petId) {
        this.clinicService.removePet(petId);
    }
    
    /**
     * Custom handler for save an pet.
     *
     * @param Pet
     * @return 
     * @return a ModelMap with the model attributes for the view
     */
    @RequestMapping(value = "/save", method = RequestMethod.PUT)
    public Pet savePet(@Valid @RequestBody Pet pet) {
    	LOGGER.debug("pet: ", pet.toString());
        return this.clinicService.savePet(pet);
    }
}
