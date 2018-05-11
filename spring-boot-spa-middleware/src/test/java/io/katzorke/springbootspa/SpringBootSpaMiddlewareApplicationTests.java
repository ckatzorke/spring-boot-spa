package io.katzorke.springbootspa;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations="classpath:application-test.properties")
@AutoConfigureMockMvc
public class SpringBootSpaMiddlewareApplicationTests {

	@Autowired
	private MockMvc mvc;

	@Test
	public void contextLoads() {
	}

	@Test
	public void redirectToIndex() throws Exception {
		mvc.perform(get("/").accept(MediaType.ALL)).andExpect(status().is(HttpStatus.FOUND.value()))
				.andExpect(redirectedUrl("/index.html"));
	}

	@Test
	public void faviconIsResolved() throws Exception {
		mvc.perform(get("/favicon.ico").accept(MediaType.ALL)).andExpect(status().isOk());
	}

	@Test
	public void routeIsResolvedToIndex() throws Exception {
		mvc.perform(get("/heroes").accept(MediaType.ALL)).andExpect(status().isOk())
				.andExpect(content().string(containsString("My SPA")));
	}
}
