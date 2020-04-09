package com.blink.web;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = HomeController.class)
public class HomeControllerTest {

	@Autowired
	private MockMvc mvc;
	
	@Test
	public void testHome() throws Exception {
		mvc.perform(get("/"))
			.andExpect(status().isOk())
			.andExpect(content().string(containsString("world")));
	}
	
	@Test
	public void testUser() throws Exception {
		mvc.perform(get("/user"))
			.andExpect(status().isOk())
			.andExpect(content().string(containsString("user")));
	}
	
	@Test
	public void testAdmin() throws Exception {
		mvc.perform(get("/admin"))
			.andExpect(status().isOk())
			.andExpect(content().string(containsString("admin")));
	}
}
