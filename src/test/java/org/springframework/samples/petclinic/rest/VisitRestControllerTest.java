package org.springframework.samples.petclinic.rest;

import net.minidev.json.JSONObject;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.samples.petclinic.Application;
import org.springframework.samples.petclinic.model.Visit;
import org.springframework.samples.petclinic.repository.OwnerRepository;
import org.springframework.samples.petclinic.repository.VisitRepository;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import org.slf4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class VisitRestControllerTest {
	
	private final static Logger LOGGER = LoggerFactory.getLogger(VisitRestControllerTest.class.getName()); 

	private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	private MockMvc mockMvc;

	private HttpMessageConverter mappingJackson2HttpMessageConverter;
	
	private ObjectMapper mapper;

	@Autowired
	private VisitRepository visitRepository;
	
	@Autowired
	private OwnerRepository ownerRepository;
	
	@Autowired
	private WebApplicationContext wac;

	@Autowired
	void setConverters(HttpMessageConverter<?>[] converters) {

		this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream().filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter)
				.findAny().get();

		Assert.assertNotNull("the JSON message converter must not be null", this.mappingJackson2HttpMessageConverter);
	}

	@Before
	public void setup() throws Exception {
		this.mockMvc = webAppContextSetup(wac).build();
		mapper = new ObjectMapper();
		mapper.registerModule(new JodaModule());
	}
	
	// INSERT INTO visits VALUES (4, 7, '2013-01-04', 'spayed');
	@Test
	public void getVisitByIdTest() throws Exception {
		mockMvc.perform(get("/visit/4"))
		.andExpect(status().isOk())
		.andExpect(content().contentType(contentType))
		.andExpect(jsonPath("$.id", is(4)))
		.andExpect(jsonPath("$.description", is("spayed")))
		.andExpect(jsonPath("$.date", is("2013-01-04T00:00:00.000Z")));
	}

	@Test
	public void createVisitTest() throws Exception {
		JSONObject petJson = new JSONObject();
		petJson.put("id", 7);

		JSONObject visitJson = new JSONObject();
		visitJson.put("description", "Salvadore");
		visitJson.put("date", "2010-12-28T00:00:00.000Z");
		visitJson.put("pet", petJson);

		LOGGER.debug("testVisitJson: " + visitJson);
		this.mockMvc.perform(put("/visit/save").contentType(contentType).content(visitJson.toJSONString()))
		.andExpect(status().isOk())
				.andExpect(content().contentType(contentType))
		.andExpect(jsonPath("$.id", any(Integer.class)))
		.andExpect(jsonPath("$.description", is("Salvadore")))
		.andExpect(jsonPath("$.date", is("2010-12-28T00:00:00.000Z")));
	}
	
	@Test
	public void saveVisitTest() throws Exception {
		Visit visit = visitRepository.findOne(3);
		visit.setDescription("Test2");
		
		String json = mapper.writeValueAsString(visit);
		LOGGER.debug("visitJson mapper: " + json);
		
		JSONObject petJson = new JSONObject();
		petJson.put("id", visit.getPet().getId());
		
		JSONObject visitJson = new JSONObject();
		visitJson.put("id", visit.getId());
		visitJson.put("description", visit.getDescription());
		//petJson.put("birthDate", pet.getBirthDate());
		visitJson.put("pet", petJson);
		//visitJson.put("new", Boolean.FALSE);

		LOGGER.debug("visitJson: " + visitJson);
		this.mockMvc.perform(put("/visit/save").contentType(contentType).content(visitJson.toJSONString()))
		.andExpect(status().isOk())
		.andExpect(content().contentType(contentType))
		.andExpect(jsonPath("$.id", any(Integer.class)))
		.andExpect(jsonPath("$.description", is("Test2")))
		.andExpect(jsonPath("$.date", is("2010-11-30T00:00:00.000Z")));
	}

	@SuppressWarnings("unchecked")
	protected String json(Object o) throws IOException {
		MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
		this.mappingJackson2HttpMessageConverter.write(o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
		return mockHttpOutputMessage.getBodyAsString();
	}
}
