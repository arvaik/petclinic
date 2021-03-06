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
import org.springframework.samples.petclinic.repository.OwnerRepository;
import org.springframework.samples.petclinic.util.EntityUtils;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class OwnerRestControllerTest {
	
	private final static Logger LOGGER = LoggerFactory.getLogger(OwnerRestControllerTest.class.getName()); 

	private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	private MockMvc mockMvc;

	private HttpMessageConverter mappingJackson2HttpMessageConverter;

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
	}

	@Test
	public void getOwnersTest() throws Exception {
		mockMvc.perform(get("/owner/list"))
		.andExpect(status().isOk())
		.andExpect(content().contentType(contentType))
		.andExpect(jsonPath("$", hasSize(11)));
	}

	// owners VALUES (4, 'Harold', 'Davis', '563 Friendly St.', 'Windsor',
	// '6085553198');
	@Test
	public void getOwnerByIdTest() throws Exception {
		mockMvc.perform(get("/owner/4"))
		.andExpect(status().isOk())
		.andExpect(content().contentType(contentType))
		.andExpect(jsonPath("$.id", is(4)))
		.andExpect(jsonPath("$.firstName", is("Harold")))
		.andExpect(jsonPath("$.lastName", is("Davis")))
		.andExpect(jsonPath("$.city", is("Windsor")))
		.andExpect(jsonPath("$.address", is("563 Friendly St.")))
		.andExpect(jsonPath("$.telephone", is("6085553198")));
	}

	//6, 'Jean', 'Coleman', '105 N. Lake St.', 'Monona', '6085552654');
	@Test
	public void getOwnerById6Test() throws Exception {
		mockMvc.perform(get("/owner/6"))
		.andExpect(status().isOk())
		.andExpect(content().contentType(contentType))
		.andExpect(jsonPath("$.id", is(6)))
		.andExpect(jsonPath("$.firstName", is("Jean")))
		.andExpect(jsonPath("$.lastName", is("Coleman")))
		.andExpect(jsonPath("$.city", is("Monona")))
		.andExpect(jsonPath("$.address", is("105 N. Lake St.")))
		.andExpect(jsonPath("$.telephone", is("6085552654")));
		//.andExpect(jsonPath("$"));
	}
	
	@Test
	public void createOwnerTest() throws Exception {
		Owner owner = new Owner();
		owner.setFirstName("Károly");
		owner.setLastName("Árvai");
		owner.setCity("Budapest");
		owner.setAddress("1118, Budapest Csiki-hegyek utca");
		owner.setTelephone("36309306554");

		//String ownerJson = json(owner);
		
		JSONObject ownerJson = new JSONObject();
		ownerJson.put("firstName", "Károly");
		ownerJson.put("lastName", "Árvai");
		ownerJson.put("address", "1118, Budapest Csiki-hegyek utca");
		ownerJson.put("city", "Budapest");
		ownerJson.put("telephone", "3639306554");

		LOGGER.debug("testOwnerJson: " + ownerJson);
		this.mockMvc.perform(put("/owner/save").contentType(contentType).content(ownerJson.toJSONString()))
		.andExpect(status().isOk())
		.andExpect(content().contentType(contentType))
		//.andExpect(jsonPath("$.id", anyOf()))
		.andExpect(jsonPath("$.firstName", is("Károly")))
		.andExpect(jsonPath("$.lastName", is("Árvai")))
		.andExpect(jsonPath("$.city", is("Budapest")))
		.andExpect(jsonPath("$.address", is("1118, Budapest Csiki-hegyek utca")))
		.andExpect(jsonPath("$.telephone", is("3639306554")));
	}
	
	@Test
	public void saveOwnerTest() throws Exception {
		Owner owner = ownerRepository.findOne(5);
		owner.setTelephone("3639306554");
		
		JSONObject ownerJson = new JSONObject();
		ownerJson.put("id", owner.getId());
		ownerJson.put("firstName", owner.getFirstName());
		ownerJson.put("lastName", owner.getLastName());
		ownerJson.put("address", owner.getAddress());
		ownerJson.put("city", owner.getCity());
		ownerJson.put("telephone", owner.getTelephone());

		//String ownerJson = json(owner); 'Peter', 'McTavish', '2387 S. Fair Way', 'Madison', '6085552765');
		LOGGER.debug("ownerJson: " + ownerJson);
		this.mockMvc.perform(put("/owner/save").contentType(contentType).content(ownerJson.toJSONString()))
		.andExpect(status().isOk())
		.andExpect(content().contentType(contentType))
		.andExpect(jsonPath("$.id", is(5)))
		.andExpect(jsonPath("$.firstName", is("Peter")))
		.andExpect(jsonPath("$.lastName", is("McTavish")))
		.andExpect(jsonPath("$.city", is("Madison")))
		.andExpect(jsonPath("$.address", is("2387 S. Fair Way")))
		.andExpect(jsonPath("$.telephone", is("3639306554")));
	}

	@SuppressWarnings("unchecked")
	protected String json(Object o) throws IOException {
		MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
		this.mappingJackson2HttpMessageConverter.write(o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
		return mockHttpOutputMessage.getBodyAsString();
	}
}
