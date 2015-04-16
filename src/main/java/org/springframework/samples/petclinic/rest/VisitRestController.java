package org.springframework.samples.petclinic.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.model.Visit;
import org.springframework.samples.petclinic.service.ClinicService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest")
public class VisitRestController {

	@Autowired
	private ClinicService clinicService;

	@RequestMapping(value = "/owners/*/pets/{petId}/visits")
	public List<Visit> showVisits(@PathVariable int petId) {
		return this.clinicService.findPetById(petId).getVisits();
	}

	@RequestMapping("/greeting")
	public PetType greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
		PetType petType = new PetType();
		petType.setName(name);
		return petType;
	}
}
