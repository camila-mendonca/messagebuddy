package br.com.messagebuddy.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.messagebuddy.entity.Conversation;
import br.com.messagebuddy.entity.Message;

@Repository
public interface MessageRepository extends CrudRepository<Message, Long>{
	
	public Iterable<Message> findAllByConversationOrderByDateSentDesc(Conversation conversation);

}
