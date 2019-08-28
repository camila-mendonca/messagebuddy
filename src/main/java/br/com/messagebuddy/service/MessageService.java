package br.com.messagebuddy.service;

import br.com.messagebuddy.entity.Message;

public interface MessageService {
	
	public void addMessage(Message message, String username);
	public void setConversationLastMessage(Message message);
	public Message loadMessage(Long messageId);
	public Iterable<Message> listConversationMessages(Long conversationId);
	public void deleteMessage(Message message);

}
