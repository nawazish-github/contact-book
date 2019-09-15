package com.plivo.contactbook;

import com.plivo.contactbook.handlers.ContactBookHandler;
import org.junit.Test;
import org.junit.experimental.results.ResultMatchers;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ContactBookApplicationTests {
	@Autowired
	private MockMvc mockMvc;


	@Test
	public void getContactsByNameAndPaginate() throws Exception {
		init();
		RequestBuilder builder = MockMvcRequestBuilders.get(
				"/contactbook/name1?size=2&pageNo=1").accept(
				MediaType.APPLICATION_JSON).header("search-by", "name");

		this.mockMvc.perform(builder).andExpect(MockMvcResultMatchers.status().isOk())
									.andExpect((content().contentType(MediaType.APPLICATION_JSON_UTF8)))
									.andExpect(jsonPath("$", hasSize(2)));

	}

	private void init() throws Exception {
		RequestBuilder builder = MockMvcRequestBuilders.put("/contactbook/").content("{\n" +
				"\t\"name\":\"name1\",\n" +
				"\t\"address\":\"address1\",\n" +
				"\t\"phone\":\"phone1\",\n" +
				"\t\"email\":\"email5\"\n" +
				"}").contentType(MediaType.APPLICATION_JSON);
		this.mockMvc.perform(builder);

		builder = MockMvcRequestBuilders.put("/contactbook/").content("{\n" +
				"\t\"name\":\"name1\",\n" +
				"\t\"address\":\"address1\",\n" +
				"\t\"phone\":\"phone1\",\n" +
				"\t\"email\":\"email4\"\n" +
				"}").contentType(MediaType.APPLICATION_JSON);
		this.mockMvc.perform(builder);

		builder = MockMvcRequestBuilders.put("/contactbook/").content("{\n" +
				"\t\"name\":\"name1\",\n" +
				"\t\"address\":\"address1\",\n" +
				"\t\"phone\":\"phone1\",\n" +
				"\t\"email\":\"email3\"\n" +
				"}").contentType(MediaType.APPLICATION_JSON);
		this.mockMvc.perform(builder);

		builder = MockMvcRequestBuilders.put("/contactbook/").content("{\n" +
				"\t\"name\":\"name1\",\n" +
				"\t\"address\":\"address1\",\n" +
				"\t\"phone\":\"phone1\",\n" +
				"\t\"email\":\"email2\"\n" +
				"}").contentType(MediaType.APPLICATION_JSON);
		this.mockMvc.perform(builder);

		builder = MockMvcRequestBuilders.put("/contactbook/").content("{\n" +
				"\t\"name\":\"name1\",\n" +
				"\t\"address\":\"address1\",\n" +
				"\t\"phone\":\"phone1\",\n" +
				"\t\"email\":\"email1\"\n" +
				"}").contentType(MediaType.APPLICATION_JSON);
		this.mockMvc.perform(builder);
	}

}
