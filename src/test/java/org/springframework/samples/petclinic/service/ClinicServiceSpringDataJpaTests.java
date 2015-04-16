
package org.springframework.samples.petclinic.service;

import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.samples.petclinic.Application;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * <p> Integration test using the 'Spring Data' profile. 
 * @see AbstractClinicServiceTests AbstractClinicServiceTests for more details. </p>
 * @author Michael Isvy
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class ClinicServiceSpringDataJpaTests extends AbstractClinicServiceTests {

}