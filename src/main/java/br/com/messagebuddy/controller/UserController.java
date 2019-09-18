package br.com.messagebuddy.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.messagebuddy.entity.User;
import br.com.messagebuddy.service.ConversationService;
import br.com.messagebuddy.service.MembershipService;
import br.com.messagebuddy.service.UserService;
import br.com.messagebuddy.util.UserEdit;

@Controller
public class UserController{

	@Autowired
	UserService userService;
	
	@Autowired
	ConversationService conversationService;
	
	@Autowired
	MembershipService memberService;
	
	@ModelAttribute("currentUser")
	public User currentUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.loadUser(auth.getName());
		return user;
	}
	
	private Authentication getLoggedUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return auth;
	}
	 
	@GetMapping("/signup")
	public String showSignUpForm(User user) {
		return "add-user";
	}	
	
	@GetMapping("/user/index")
	public ModelAndView listMyConversations() {
		ModelAndView modelAndView = new ModelAndView("user/list-user-conversations");
		modelAndView.addObject("conversations", conversationService.listConversationsByUser(this.getLoggedUser().getName()));
		modelAndView.addObject("memberConversations", memberService.listAcceptedMembershipsByUser(this.getLoggedUser().getName()));
		return modelAndView;
	}
	
	@GetMapping("/admin/index")
	public String showAdminIndex() {
		return "user/admin/index";
	}
	
	@GetMapping("/user/nextreleases")
	public String nextReleases() {
		return "user/next-releases";
	}
	
	//CRUD operations
	
	@PostMapping("/adduser")
	public String addUser(@Valid User user, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			return "add-user";
		}
		userService.saveUser(user);
		return "login";
	}
	
	@GetMapping("admin/listusers")
	public ModelAndView listUsers() {
		ModelAndView modelAndView = new ModelAndView("user/admin/list-users");
		modelAndView.addObject("users", userService.listUsers());
		return modelAndView;
	}
	
	@GetMapping("/user/editcurrentuser")
	public String showEditForm(UserEdit userEdit, Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		// Since I just want some information to be edited, I load a userEdit object and load these information into it
		model.addAttribute("user", userService.loadUserEdit(auth.getName()));
		return "user/edit-user";
	}

	@PostMapping("/user/edituser")
	public String editUser(UserEdit userEdit, Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		userService.saveEditedUser(userEdit, auth.getName());
		model.addAttribute("user", userService.loadUserEdit(auth.getName()));
		return "user/edit-user";
	}
	
	
		
}
