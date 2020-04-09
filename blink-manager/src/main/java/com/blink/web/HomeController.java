package com.blink.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

	@GetMapping("/")
	public String home(Model model) {
		model.addAttribute("hello", "world");
		return "home";
	}
	
	@GetMapping("/user")
	public String user(Model model) {
		model.addAttribute("hello", "user");
		return "home";
	}
	
	@GetMapping("/admin")
	public String admin(Model model) {
		model.addAttribute("hello", "admin");
		return "home";
	}
	
	@GetMapping("/signup")
	public String signup(Model model) {
		model.addAttribute("hello", "admin");
		return "signup";
	}
	
	
}
