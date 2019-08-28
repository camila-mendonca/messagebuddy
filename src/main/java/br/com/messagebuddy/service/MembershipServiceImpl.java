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
public class MembershipServiceImpl implements MembershipService{
	
	@Autowired
	MembershipRepository memberRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	ConversationRepository conversationRepository;

	@Override
	public void addMembershipInvitation(Long userId, Long conversationId) {
		User user = userRepository.findById(userId).get();
		Conversation conversation = conversationRepository.findById(conversationId).get();
		Membership member = new Membership();
		member.setUser(user);
		member.setConversation(conversation);
		member.setInvitationStatus(InvitationStatus.waiting);
		memberRepository.save(member);
	}
	
	@Override
	public void acceptMembershipInvitation(Long memberId) {
		Membership member = memberRepository.findById(memberId).get();
		member.setInvitationStatus(InvitationStatus.accepted);
		member.setMemberSince(new Date());
		memberRepository.save(member);
	}
	
	@Override
	public void rejectMembershipInvitation(Long memberId) {
		Membership member = memberRepository.findById(memberId).get();
		memberRepository.delete(member);
		
	}
	
	@Override
	public Iterable<Membership> listMembershipsByUser(String username) {
		User user = userRepository.findUserByUsername(username);
		Iterable<Membership> members = memberRepository.findAllByUserId(user);
		
		return members;
	}
	
	@Override
	public Iterable<Membership> listWaitingMembershipsByUser(String username) {
		User user = userRepository.findUserByUsername(username);
		Iterable<Membership> waitingMemberships = memberRepository.findAllByUserAndInvitationStatus(user, InvitationStatus.waiting);
		return waitingMemberships;
	}

	@Override
	public Iterable<Membership> listAcceptedMembershipsByUser(String username) {
		User user = userRepository.findUserByUsername(username);
		Iterable<Membership> acceptedMemberships = memberRepository.findAllByUserAndInvitationStatus(user, InvitationStatus.accepted);
		return acceptedMemberships;
	}
	
	@Override
	public boolean checkIfHasMembershipAccepted(String username, Conversation conversation) {
		User user = userRepository.findUserByUsername(username);
		boolean isMember = memberRepository.existsByUserAndConversationAndInvitationStatus(user, conversation, InvitationStatus.accepted);
		return isMember;
	}

	@Override
	public void becomeConversationMember(String username, Long conversationId) {
		User user = userRepository.findUserByUsername(username);
		Conversation conversation = conversationRepository.findById(conversationId).get();
		Membership member = new Membership();
		member.setUser(user);
		member.setConversation(conversation);
		member.setMemberSince(new Date());
		member.setInvitationStatus(InvitationStatus.accepted);
		memberRepository.save(member);
	}


	

}
