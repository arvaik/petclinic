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
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.samples.petclinic.Application;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.repository.OwnerRepository;
import org.springframework.samples.petclinic.repository.PetRepository;
import org.springframework.samples.petclinic.repository.PetTypeRepository;
import org.springframework.samples.petclinic.util.EntityUtils;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.slf4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class PetRestControllerTest {
	
	private final static Logger LOGGER = LoggerFactory.getLogger(PetRestControllerTest.class.getName()); 

	private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	private MockMvc mockMvc;

	private HttpMessageConverter mappingJackson2HttpMessageConverter;
	
	private ObjectMapper mapper;

	@Autowired
	private PetRepository petRepository;
	
	@Autowired
	private OwnerRepository ownerRepository;
	
	@Autowired
	private PetTypeRepository petTypeRepository;

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
	
	@Test
	public void showPetTypesTest() throws Exception {
		mockMvc.perform(get("/pet/types"))
		.andExpect(status().isOk())
		.andExpect(content().contentType(contentType))
		.andExpect(jsonPath("$", hasSize(6)));
	}

	// pets INSERT INTO pets VALUES (4, 'Jewel', '2010-03-07', 2, 3);
	@Test
	public void getPetByIdTest() throws Exception {
		mockMvc.perform(get("/pet/4"))
		.andExpect(status().isOk())
		.andExpect(content().contentType(contentType))
		.andExpect(jsonPath("$.id", is(4)))
		.andExpect(jsonPath("$.name", is("Jewel")))
		.andExpect(jsonPath("$.birthDate", is("2010-03-07T00:00:00.000Z")))
		.andExpect(jsonPath("$.type.name", is("dog")))
		.andExpect(jsonPath("$.visits", hasSize(0)));
	}

	@Test
	public void createPetTest() throws Exception {
		
		PetType petType = petTypeRepository.findOne(2);
		Owner owner = ownerRepository.findOne(2);
		//Pet pet = new Pet()
		
		
		
		
		JSONObject petTypeJson = new JSONObject();
		petTypeJson.put("id", petType.getId());
		petTypeJson.put("name", petType.getName());
		
		JSONObject ownerJson = new JSONObject();
		ownerJson.put("id", owner.getId());
		ownerJson.put("firstName", owner.getFirstName());
		ownerJson.put("lastName", owner.getLastName());
		ownerJson.put("address", owner.getAddress());
		ownerJson.put("city", owner.getCity());
		ownerJson.put("telephone", owner.getTelephone());
		
		JSONObject petJson = new JSONObject();
		petJson.put("name", "Salvadore");
		petJson.put("birthDate", "2010-12-28T00:00:00.000Z");
		petJson.put("type", petTypeJson);
		petJson.put("owner", ownerJson);

		LOGGER.debug("testPetJson: " + petJson);
		this.mockMvc.perform(put("/pet/save").contentType(contentType).content(petJson.toJSONString()))
		.andExpect(status().isOk())
		.andExpect(content().contentType(contentType))
		.andExpect(jsonPath("$.id", any(Integer.class)))
		.andExpect(jsonPath("$.name", is("Salvadore")))
		.andExpect(jsonPath("$.birthDate", is("2010-12-28T00:00:00.000Z")))
		.andExpect(jsonPath("$.type.name", is("dog")));
	}
	
	@Test
	public void savePetTest() throws Exception {
		Pet pet = petRepository.findOne(5);
		pet.setName("Test2");
		
		String json = mapper.writeValueAsString(pet);
		LOGGER.debug("petJson mapper: " + json);
		
		JSONObject petTypeJson = new JSONObject();
		petTypeJson.put("id", pet.getType().getId());
		petTypeJson.put("name", pet.getType().getName());
		petTypeJson.put("new", Boolean.FALSE);
		
		JSONObject ownerJson = new JSONObject();
		ownerJson.put("id", pet.getOwner().getId());
		ownerJson.put("firstName", pet.getOwner().getFirstName());
		ownerJson.put("lastName", pet.getOwner().getLastName());
		ownerJson.put("address", pet.getOwner().getAddress());
		ownerJson.put("city", pet.getOwner().getCity());
		ownerJson.put("telephone", pet.getOwner().getTelephone());
		ownerJson.put("new", Boolean.FALSE);
		
		JSONObject petJson = new JSONObject();
		petJson.put("id", pet.getId());
		petJson.put("name", pet.getName());
		//petJson.put("birthDate", pet.getBirthDate());
		petJson.put("type", petTypeJson);
		petJson.put("owner", ownerJson);
		petJson.put("new", Boolean.FALSE);

		//String 'Iggy', '2010-11-30', 3, 4); (3, 'lizard');
		LOGGER.debug("petJson: " + petJson);
		this.mockMvc.perform(put("/pet/save").contentType(contentType).content(petJson.toJSONString()))
		.andExpect(status().isOk())
		.andExpect(content().contentType(contentType))
		.andExpect(jsonPath("$.id", any(Integer.class)))
		.andExpect(jsonPath("$.name", is("Test2")))
		//.andExpect(jsonPath("$.birthDate", is("2010-11-30T00:00:00.000Z")))
		.andExpect(jsonPath("$.type.name", is("lizard")));
	}

	@SuppressWarnings("unchecked")
	protected String json(Object o) throws IOException {
		MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
		this.mappingJackson2HttpMessageConverter.write(o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
		return mockHttpOutputMessage.getBodyAsString();
	}
}
