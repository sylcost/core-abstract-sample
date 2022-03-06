package com.example.demo.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(initializers = {ShopControllerTest.Initializer.class})
@AutoConfigureMockMvc
@Testcontainers
@Sql(scripts = {"file:src/test/resources/create_tables.sql","file:src/test/resources/fill_tables.sql"})
public class ShopControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@ClassRule
	public static PostgreSQLContainer postgreSQLContainer = (PostgreSQLContainer) new PostgreSQLContainer("postgres:14.2")
		.withDatabaseName("postgres")
		.withUsername("postgres")
		.withPassword("postgres");
		//.withFileSystemBind("../../../../../resources",  "/docker-entrypoint-initdb.d", BindMode.READ_ONLY);

	static class Initializer
		implements ApplicationContextInitializer<ConfigurableApplicationContext>
	{
		public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
			TestPropertyValues.of(
				"spring.datasource.url=" + postgreSQLContainer.getJdbcUrl(),
				"spring.datasource.username=" + postgreSQLContainer.getUsername(),
				"spring.datasource.password=" + postgreSQLContainer.getPassword()
			).applyTo(configurableApplicationContext.getEnvironment());
		}
	}

	@Test
	public void test()
		throws Exception
	{
		ResultActions resultActions = mockMvc.perform(get("/shop/search")
			       .header("version", 1)
			.param("name", "DECATHLON NANTES"));
		resultActions.andExpect(status().is2xxSuccessful());
		resultActions.andExpect(jsonPath("$.state", equalTo("SOME")));
		resultActions.andExpect(jsonPath("$.name", equalTo("DECATHLON NANTES")));
	}
}