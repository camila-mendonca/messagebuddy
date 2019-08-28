package br.com.messagebuddy.service;

import br.com.messagebuddy.entity.Conversation;
import br.com.messagebuddy.entity.Membership;

public interface MembershipService {
	
	public void addMembershipInvitation(Long userId, Long conversationId);
	public void acceptMembershipInvitation(Long memberId);
	public void rejectMembershipInvitation(Long memberId);
	public Iterable<Membership> listMembershipsByUser(String username);
	public Iterable<Membership> listWaitingMembershipsByUser(String username);
	public Iterable<Membership> listAcceptedMembershipsByUser(String username);
	public boolean checkIfHasMembershipAccepted(String username, Conversation conversation);
	public void becomeConversationMember(String username, Long conversationId);

}
