package br.com.messagebuddy.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.messagebuddy.entity.Conversation;
import br.com.messagebuddy.entity.Message;
import br.com.messagebuddy.repository.ConversationRepository;
import br.com.messagebuddy.repository.MessageRepository;
import br.com.messagebuddy.repository.UserRepository;

@Service
public class MessageServiceImpl implements MessageService{
	
	@Autowired
	MessageRepository messageRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	ConversationRepository conversationRepository;

	@Override
	public void addMessage(Message message, String username) {
		message.setUser(userRepository.findUserByUsername(username));
		message.setDateSent(new Date());
		Message savedMessage = messageRepository.save(message);
		this.setConversationLastMessage(savedMessage);
	}
	
	@Override
	public void setConversationLastMessage(Message message) {
		Conversation conversation = message.getConversation();
		conversation.setLastMessage(message);
		conversationRepository.save(conversation);
		
	}

	@Override
	public Iterable<Message> listConversationMessages(Long conversationId) {
		Conversation conversation = conversationRepository.findById(conversationId).get();
		Iterable<Message> messages = messageRepository.findAllByConversationOrderByDateSentDesc(conversation);
		return messages;
	}

	@Override
	public void deleteMessage(Message message) {
		messageRepository.delete(message);
	}

	@Override
	public Message loadMessage(Long messageId) {
		Message message = messageRepository.findById(messageId).get();
		return message;
	}	

}
