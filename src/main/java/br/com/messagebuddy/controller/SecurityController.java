package br.com.messagebuddy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SecurityController {
	
	//@GetMapping("/")
	@RequestMapping(value= {"/","/login"})
	public String login(Model model) {
		return "login";
	}

}
