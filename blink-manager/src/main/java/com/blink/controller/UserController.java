package com.blink.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.blink.entity.User;
import com.blink.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private UserService userService;
	
	@GetMapping("/signup")
	public String signupForm() {
		return "signup";
	}

	@PostMapping("/signup")
	public String signup(User admin) {
		System.out.println(admin);
		userService.signupUser(admin);
		return "redirect:/user/signup";
	}

	@GetMapping("/test")
	public ModelAndView test(Model model) {

		ModelAndView mv = new ModelAndView("/test");
//		userList = userService.selectAllUser();
		Iterable<User> userList = userService.selectSomeUsers();
		mv.addObject("userList", userList);
		System.out.println(userList);
		
		return mv;
	}
}
