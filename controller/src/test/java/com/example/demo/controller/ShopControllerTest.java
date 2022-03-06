package com.example.demo.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
@Sql(scripts = {"file:src/test/resources/create_tables.sql","file:src/test/resources/fill_tables.sql"})
public class ShopControllerTest
{

	@Autowired
	private MockMvc mockMvc;

	@Container
	private static final PostgreSQLContainer postgresqlContainer = new PostgreSQLContainer("postgres:14.2")
		.withDatabaseName("postgres")
		.withUsername("postgres")
		.withPassword("postgres");

	@DynamicPropertySource
	static void properties(DynamicPropertyRegistry registry) throws InterruptedException {
		final String s = "jdbc:postgresql://"+ postgresqlContainer.getHost() +":"+ postgresqlContainer.getMappedPort(5432) + "/";
		registry.add("spring.datasource.url", () -> s);
	}

	@Test
	public void testGet()
		throws Exception
	{
		ResultActions resultActions = mockMvc.perform(get("/shop/search")
			       .header("version", 1)
			.param("name", "DECATHLON NANTES"));
		resultActions.andExpect(status().is2xxSuccessful());
		resultActions.andExpect(jsonPath("$.name", equalTo("DECATHLON NANTES")));
	}
}