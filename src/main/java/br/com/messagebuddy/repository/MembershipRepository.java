package br.com.messagebuddy.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.messagebuddy.entity.Conversation;
import br.com.messagebuddy.entity.InvitationStatus;
import br.com.messagebuddy.entity.Membership;
import br.com.messagebuddy.entity.User;

@Repository
public interface MembershipRepository extends CrudRepository<Membership, Long>{
	
	@Query("SELECT m FROM Membership m WHERE m.user = ?1")
	public Iterable<Membership> findAllByUserId(User user);
	public Iterable<Membership> findAllByUserAndInvitationStatus(User user, InvitationStatus invitationStatus);
	public boolean existsByUserAndConversation(User user, Conversation conversation);
	public boolean existsByUserAndConversationAndInvitationStatus(User user, Conversation conversation, InvitationStatus invitationStatus);

}
