package br.com.messagebuddy.service;

import br.com.messagebuddy.entity.Conversation;

public interface ConversationService {
	
	public void saveConversation(Conversation conversation, String username);
	public void updateConversation(Conversation conversation);
	public Iterable<Conversation> listPublicConversations();
	public Iterable<Conversation> listConversationsByUser(String username);
	public Conversation loadConversation(Long id);

}
