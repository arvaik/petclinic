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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Views;
import org.springframework.samples.petclinic.service.ClinicService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

@RestController
@RequestMapping("/owner")
public class OwnerRestController {

    private final ClinicService clinicService;


    @Autowired
    public OwnerRestController(ClinicService clinicService) {
        this.clinicService = clinicService;
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Collection<Owner> showOwners() {
        return this.clinicService.findOwners();
    }

    /**
     * Custom handler for displaying an owner.
     *
     * @param ownerId the ID of the owner to display
     * @return 
     * @return a ModelMap with the model attributes for the view
     */
    @RequestMapping(value = "/{ownerId}", method = RequestMethod.GET)
    public Owner showOwner(@PathVariable("ownerId") int ownerId) {
        return this.clinicService.findOwnerById(ownerId);
    }

    /**
     * Custom handler for remove an owner.
     *
     * @param ownerId the ID of the owner to remove
     */
    @RequestMapping(value = "/{ownerId}", method = RequestMethod.DELETE)
    public void removeOwner(@PathVariable("ownerId") int ownerId) {
        this.clinicService.removeOwner(ownerId);
    }
    
    /**
     * Custom handler for save an owner.
     *
     * @param Owner
     * @return 
     * @return a ModelMap with the model attributes for the view
     */
    @RequestMapping(value = "/save", method = RequestMethod.PUT)
    public Owner saveOwner(@Valid @RequestBody Owner owner) {
        return this.clinicService.saveOwner(owner);
    }

    /**
     * Custom handler for get an empty owner.
     *
     * @param Owner
     * @return 
     * @return a ModelMap with the model attributes for the view
     */
    @JsonView(Views.OwnerSummary.class)
    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public Owner getEmptyOwner() {
        return new Owner();
    }
}
