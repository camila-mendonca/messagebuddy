package br.com.messagebuddy.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.messagebuddy.entity.Conversation;
import br.com.messagebuddy.entity.User;

@Repository
public interface ConversationRepository extends CrudRepository<Conversation, Long>{
	
	@Query("SELECT c FROM Conversation c WHERE c.user = ?1")
	public Iterable<Conversation> findAllByUserId(User user);
	
	@Query("SELECT c FROM Conversation c WHERE c.subject = ?1")
	public Optional<Conversation> findBySubject(String subject);
	
	public Iterable<Conversation> findAllByOpenTrue();

}
