package br.com.messagebuddy.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.messagebuddy.entity.Conversation;
import br.com.messagebuddy.entity.InvitationStatus;
import br.com.messagebuddy.entity.Membership;
import br.com.messagebuddy.entity.User;
import br.com.messagebuddy.repository.ConversationRepository;
import br.com.messagebuddy.repository.MembershipRepository;
import br.com.messagebuddy.repository.UserRepository;

@Service
public class ConversationServiceImpl implements ConversationService {
	
	@Autowired
	ConversationRepository conversationRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	MembershipRepository memberRepository;
	
	private User getUser(String username) {
		User user = userRepository.findUserByUsername(username);
		return user;
	}

	@Override
	public void saveConversation(Conversation conversation, String username) {					
		conversation.setUser(this.getUser(username));
		conversation.setCreationDate(new Date());
		
		Conversation savedConversation = conversationRepository.save(conversation);
		this.addMember(savedConversation);
	}
	
	@Override
	public void updateConversation(Conversation conversation) {
		conversationRepository.save(conversation);
		
	}

	@Override
	public Iterable<Conversation> listPublicConversations() {
		Iterable<Conversation> conversations = conversationRepository.findAllByOpenTrue();
		return conversations;
		
	}
	
	@Override
	public Iterable<Conversation> listConversationsByUser(String username) {
		Iterable<Conversation> conversations = conversationRepository.findAllByUserId(this.getUser(username));
		return conversations;
	}

	@Override
	public Conversation loadConversation(Long id) {
		Conversation conversation = conversationRepository.findById(id).get();
		return conversation;
	}
	
	public void addMember(Conversation savedConversation) {		
		Membership member = new Membership();
		member.setConversation(savedConversation);
		member.setUser(savedConversation.getUser());
		member.setMemberSince(new Date());
		member.setInvitationStatus(InvitationStatus.accepted);
		memberRepository.save(member);
	}


}
