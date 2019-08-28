package br.com.messagebuddy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import br.com.messagebuddy.service.ConversationService;
import br.com.messagebuddy.service.MembershipService;

@Controller
public class NotificationController {
	
	@Autowired
	MembershipService membershipService;
	
	@Autowired
	ConversationService conversationService;	
	
	@GetMapping("/user/notifications")
	public ModelAndView listNotifications() {
		ModelAndView modelAndView = new ModelAndView("user/list-invitations");
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		modelAndView.addObject("invitations", membershipService.listWaitingMembershipsByUser(auth.getName()));
		return modelAndView;
	}
	
	@GetMapping("/user/acceptInvitation/{invitation}")
	public String acceptInvitation(@PathVariable("invitation") Long membershipId) {
		membershipService.acceptMembershipInvitation(membershipId);		
		return "redirect:/user/myinvitations";
	}
	
	@GetMapping("/user/refuseInvitation/{invitation}")
	public String refuseInvitation(@PathVariable("invitation") Long membershipId) {
		membershipService.rejectMembershipInvitation(membershipId);
		return "redirect:/user/myinvitations";
	}

}
