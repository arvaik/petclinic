package org.springframework.samples.petclinic.rest;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Visit;
import org.springframework.samples.petclinic.service.ClinicService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/visit")
public class VisitRestController {

	@Autowired
	private ClinicService clinicService;

	/**
     * Custom handler for displaying an visit.
     *
     * @param visitId the ID of the visit to display
     * @return 
     * @return a ModelMap with the model attributes for the view
     */
    @RequestMapping(value = "/{visitId}", method = RequestMethod.GET)
    public Visit showVisit(@PathVariable("visitId") int visitId) {
        return this.clinicService.findVisitById(visitId);
    }

    /**
     * Custom handler for remove an visit.
     *
     * @param visitId the ID of the visit to remove
     */
    @RequestMapping(value = "/{visitId}", method = RequestMethod.DELETE)
    public void removeVisit(@PathVariable("visitId") int visitId) {
        this.clinicService.removeVisit(visitId);
    }
    
    /**
     * Custom handler for save an visit.
     *
     * @param Visit
     * @return 
     * @return a ModelMap with the model attributes for the view
     */
    @RequestMapping(value = "/save", method = RequestMethod.PUT)
    public Visit saveVisit(@Valid @RequestBody Visit visit) {
    	return this.clinicService.saveVisit(visit);
    }
}
