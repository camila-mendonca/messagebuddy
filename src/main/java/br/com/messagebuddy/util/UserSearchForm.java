package br.com.messagebuddy.util;

import org.springframework.stereotype.Component;

import br.com.messagebuddy.entity.Conversation;

@Component
public class UserSearchForm {
	
	private String word;
	private String field;
	private Conversation conversation;
	
	public UserSearchForm() {
	}

	public UserSearchForm(String word, String field, Conversation conversation) {
		this.word = word;
		this.field = field;
		this.conversation = conversation;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public Conversation getConversation() {
		return conversation;
	}

	public void setConversation(Conversation conversation) {
		this.conversation = conversation;
	}	

}
