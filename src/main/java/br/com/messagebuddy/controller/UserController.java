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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import br.com.messagebuddy.entity.User;
import br.com.messagebuddy.service.ConversationService;
import br.com.messagebuddy.service.UserService;
import br.com.messagebuddy.util.FileSaver;
import br.com.messagebuddy.util.UserEdit;
import br.com.messagebuddy.util.UserSearchForm;

@Controller
public class UserController{
	
	@Autowired
	private FileSaver fileSaver;

	@Autowired
	UserService userService;
	
	@Autowired
	ConversationService conversationService;
	
	@ModelAttribute("currentUser")
	public User currentUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.loadUser(auth.getName());
		return user;
	}
	 
	@GetMapping("/signup")
	public String showSignUpForm(User user) {
		return "add-user";
	}	
	
	@GetMapping("/user/index")
	public String showUserIndex() {
		return "user/index";
	}
	
	@GetMapping("/admin/index")
	public String showAdminIndex() {
		return "user/admin/index";
	}
	
	//CRUD operations
	
	@PostMapping("/adduser")
	public String addUser(@Valid User user, MultipartFile picture, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			return "add-user";
		}
		String path = fileSaver.write("user", picture);
		user.setPicturePath(path);
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
	
	@PostMapping("/user/searchuser")
	public String search(UserSearchForm userSearch, Model model) {
		model.addAttribute("users", userService.searchUser(userSearch));
		model.addAttribute("conversation", userSearch.getConversation());
		model.addAttribute("userSearch", userSearch);
		return "user/conversation";
	}
		
}
