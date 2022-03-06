package com.example.demo.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import com.example.demo.dto.exceptions.TooMuchShoesException;
import com.example.demo.dto.in.StocksUpdate;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(initializers = { StockControllerTest.Initializer.class})
@AutoConfigureMockMvc
@Testcontainers
@Sql(scripts = {"file:src/test/resources/create_tables.sql","file:src/test/resources/fill_tables.sql"})
public class StockControllerTest
{

	@Autowired
	private MockMvc mockMvc;

	@ClassRule
	public static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer("postgres:14.2")
		.withDatabaseName("postgres")
		.withUsername("postgres")
		.withPassword("postgres");

	static class Initializer
		implements ApplicationContextInitializer<ConfigurableApplicationContext>
	{
		public void initialize(final ConfigurableApplicationContext configurableApplicationContext) {
			TestPropertyValues.of(
				"spring.datasource.url=" + postgreSQLContainer.getJdbcUrl(),
				"spring.datasource.username=" + postgreSQLContainer.getUsername(),
				"spring.datasource.password=" + postgreSQLContainer.getPassword()
			).applyTo(configurableApplicationContext.getEnvironment());
		}
	}

	@Test
	public void testPatch()
		throws Exception
	{
		ResultActions resultActions1 = mockMvc.perform(get("/stock/shop/1")
			.header("version", 1));
		resultActions1.andExpect(status().is2xxSuccessful());
		resultActions1.andExpect(jsonPath("$.shoes.length()", equalTo(3)));
		StocksUpdate stocksUpdate1 = new StocksUpdate();
		StocksUpdate.StockUpdate stockUpdate11 = new StocksUpdate.StockUpdate(1L, 10L);
		StocksUpdate.StockUpdate stockUpdate12 = new StocksUpdate.StockUpdate(2L, 3L);
		stocksUpdate1.getStocks().addAll(Arrays.asList(stockUpdate11, stockUpdate12));

		ResultActions resultActions2 = mockMvc.perform(patch("/stock/shop/1/full")
			.header("version", 1)
			.content(asJsonString(stocksUpdate1))
			.contentType(MediaType.APPLICATION_JSON));
		resultActions2.andExpect(status().is2xxSuccessful());
		ResultActions resultActions3 = mockMvc.perform(get("/stock/shop/1")
			.header("version", 1));
		resultActions3.andExpect(status().is2xxSuccessful());
		resultActions3.andExpect(jsonPath("$.shoes.length()", equalTo(2)));

		StocksUpdate stocksUpdate2 = new StocksUpdate();
		StocksUpdate.StockUpdate stockUpdate21 = new StocksUpdate.StockUpdate(1L, 10L);
		StocksUpdate.StockUpdate stockUpdate22 = new StocksUpdate.StockUpdate(2L, 15L);
		StocksUpdate.StockUpdate stockUpdate23 = new StocksUpdate.StockUpdate(2L, 20L);
		stocksUpdate2.getStocks().addAll(Arrays.asList(stockUpdate21, stockUpdate22, stockUpdate23));

		ResultActions resultActions4 = mockMvc.perform(patch("/stock/shop/1/full")
			.header("version", 1)
			.content(asJsonString(stocksUpdate2))
			.contentType(MediaType.APPLICATION_JSON));
		resultActions4.andExpect(status().is4xxClientError());
		resultActions4.andExpect(result -> assertTrue(result.getResolvedException() instanceof TooMuchShoesException));
	}


	public static String asJsonString(final Object obj)
	{
		try {
			final ObjectMapper mapper = new ObjectMapper();
			return mapper.writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}