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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import br.com.messagebuddy.entity.Conversation;
import br.com.messagebuddy.entity.Message;
import br.com.messagebuddy.entity.User;
import br.com.messagebuddy.service.ConversationService;
import br.com.messagebuddy.service.MembershipService;
import br.com.messagebuddy.service.MessageService;
import br.com.messagebuddy.service.UserService;
import br.com.messagebuddy.util.UserSearchForm;

@Controller
public class ConversationController {
	
	@Autowired
	ConversationService conversationService;
	
	@Autowired
	MembershipService memberService;
	
	@Autowired
	MessageService messageService;
	
	@Autowired
	UserService userService;
	
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
	
	//To avoid repetition of code, this function was created, where all the data necessary to load the conversation page is retrieved
	private Model getConversationData(Long conversationId, Model model, Message message, UserSearchForm userSearch) {
		Conversation conversation = conversationService.loadConversation(conversationId);
		model.addAttribute("conversation", conversation);
		
		//Verifications
		boolean isMember = memberService.checkIfHasMembershipAccepted(this.getLoggedUser().getName(), conversation);
		model.addAttribute("isMember", isMember);
		
		//Objects used in forms
		userSearch.setConversation(conversation);
		model.addAttribute("userSearch", userSearch);
		message.setConversation(conversation);
		model.addAttribute("message", message);
		
		//Load conversation's messages
		model.addAttribute("messages", messageService.listConversationMessages(conversationId));
		
		return model;
	}
	
	@GetMapping("/user/newconversation")
	public String newConversationForm(Conversation conversation) {
		return "user/add-conversation";
	}
	
	@PostMapping("/user/addconversation")
	public String addConversation(@Valid Conversation conversation, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			return "user/add-conversation";
		}		
		conversationService.saveConversation(conversation, this.getLoggedUser().getName());
		return "redirect:/user/index";
	}
	
	@GetMapping("/user/allconversations")
	public String listPublicConversations(Model model) {
		model.addAttribute("conversations", conversationService.listPublicConversations());
		return "user/list-public-conversations";
	}
	
	@GetMapping("/user/loadconversation/{id}")
	public String loadConversation(@PathVariable("id") Long conversationId, Model model, UserSearchForm userSearch, Message message) {		
		model = this.getConversationData(conversationId, model, message, userSearch);		
		
		return "user/conversation";
	}
	
	// Member related methods
	
	@PostMapping("/user/searchuser")
	public String search(UserSearchForm userSearch, Model model, Message message) {
		model = this.getConversationData(userSearch.getConversation().getConversationId(), model, message, userSearch);		
		model.addAttribute("users", userService.searchUser(userSearch));

		return "user/conversation";
	}
	
	@GetMapping("/user/invite/person={person},conversation={conversation}")
	public String inviteUserToConversation(@PathVariable("person") Long userId, @PathVariable("conversation") Long conversationId, Model model, Message message, UserSearchForm userSearch ) {
		model = this.getConversationData(conversationId, model, message, userSearch);
		memberService.addMembershipInvitation(userId, conversationId);

		return "user/conversation";
	}
	
	@GetMapping("/user/becomemember/{id}")
	public String becomeConversationMember(@PathVariable("id") Long conversationId, Model model, UserSearchForm userSearch, Message message) {
		memberService.becomeConversationMember(this.getLoggedUser().getName(), conversationId);
		model = this.getConversationData(conversationId, model, message, userSearch);
		return "user/conversation";
	}
	
	//Message related methods
	
	@PostMapping("/user/addmessage")
	public String addMessage(@ModelAttribute("message") Message message, BindingResult bindingResult, Model model, UserSearchForm userSearch) {
		/*
		 * Somehow it's not working, no matter where I place it
		 * if(bindingResult.hasErrors()) { return "user/conversation"; }
		 */
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		messageService.addMessage(message, auth.getName());
		Message newMessage = new Message();
		newMessage.setConversation(message.getConversation());
		model = this.getConversationData(message.getConversation().getConversationId(), model, newMessage, userSearch);
						
		return "user/conversation";
	}

}
