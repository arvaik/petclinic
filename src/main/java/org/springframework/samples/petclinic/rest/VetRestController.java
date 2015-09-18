package org.springframework.samples.petclinic.rest;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Vet;
import org.springframework.samples.petclinic.model.Vets;
import org.springframework.samples.petclinic.service.ClinicService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/vet")
public class VetRestController {

	@Autowired
	private ClinicService clinicService;

	@RequestMapping("/xml")
	public Vets showVetList(Model model) {
		// Here we are returning an object of type 'Vets' rather than a
		// collection of Vet objects
		// so it is simpler for Object-Xml mapping
		Vets vets = new Vets();
		vets.getVetList().addAll(this.clinicService.findVets());
		return vets;
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public Collection<Vet> showJsonVetList() {
		return this.clinicService.findVets();
	}
}